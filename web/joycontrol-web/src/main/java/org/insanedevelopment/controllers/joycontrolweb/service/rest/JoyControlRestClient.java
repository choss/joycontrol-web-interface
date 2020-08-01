package org.insanedevelopment.controllers.joycontrolweb.service.rest;

import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerType;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class JoyControlRestClient implements SwitchControllerConnection {

	private Map<SwitchButtons, String> buttonsToApi = new EnumMap<>(SwitchButtons.class);
	private Map<SwitchSticks, String> sticksToApi = new EnumMap<>(SwitchSticks.class);
	private Map<SwitchSticksAxis, String> stickAxisToApi = new EnumMap<>(SwitchSticksAxis.class);

	private WebClient webClient;

	public JoyControlRestClient(@Value("${joycontrol.rest.url}") String baseUrl) {
		webClient = WebClient.create(baseUrl);

		buttonsToApi.put(SwitchButtons.Y, "y");
		buttonsToApi.put(SwitchButtons.X, "x");
		buttonsToApi.put(SwitchButtons.B, "b");
		buttonsToApi.put(SwitchButtons.A, "a");
		buttonsToApi.put(SwitchButtons.R, "r");
		buttonsToApi.put(SwitchButtons.ZR, "zr");
		buttonsToApi.put(SwitchButtons.MINUS, "minus");
		buttonsToApi.put(SwitchButtons.PLUS, "plus");
		buttonsToApi.put(SwitchButtons.RIGHT_STICK, "r_stick");
		buttonsToApi.put(SwitchButtons.LEFT_STICK, "l_stick");
		buttonsToApi.put(SwitchButtons.HOME, "home");
		buttonsToApi.put(SwitchButtons.CAPTURE, "capture");
		buttonsToApi.put(SwitchButtons.DPAD_DOWN, "down");
		buttonsToApi.put(SwitchButtons.DPAD_UP, "up");
		buttonsToApi.put(SwitchButtons.DPAD_RIGHT, "right");
		buttonsToApi.put(SwitchButtons.DPAD_LEFT, "left");
		buttonsToApi.put(SwitchButtons.L, "l");
		buttonsToApi.put(SwitchButtons.ZL, "zl");
		buttonsToApi.put(SwitchButtons.SR, "sr");
		buttonsToApi.put(SwitchButtons.SL, "sl");

		sticksToApi.put(SwitchSticks.LEFT_STICK, "l_stick");
		sticksToApi.put(SwitchSticks.RIGHT_STICK, "r_stick");

		stickAxisToApi.put(SwitchSticksAxis.X_AXIS, "x_axis");
		stickAxisToApi.put(SwitchSticksAxis.Y_AXIS, "y_axis");

	}

	@Override
	public void sendNfcData(byte[] nfcData) {
		var nfcString = convertBase64(nfcData);
		var request = new NfcRequest(nfcString);
		webClient.post()
				.uri("/controller/nfc")
				.bodyValue(request)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class)
				.block();
	}

	@Override
	public void removeNfcData() {
		webClient.delete()
				.uri("/controller/nfc")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class)
				.block();
	}

	@Override
	public String connect(SwitchControllerType type, String reconnectAddress, byte[] firmware) {
		var result = connectReactive(type, reconnectAddress, firmware);
		return result.block();
	}

	public Mono<String> connectReactive(SwitchControllerType type, String reconnectAddress, byte[] firmware) {
		String controllerType = type.name();
		String spiFirmware = convertBase64(firmware);
		ConnectRequest cr = new ConnectRequest(controllerType, reconnectAddress, spiFirmware);
		var result = webClient.post()
				.uri("/controller/connect")
				.bodyValue(cr)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class);
		return result.map(cs -> cs.getPeer());
	}

	// That is a ridiculous method, too lazy to change it now
	private String convertBase64(byte[] source) {
		return Base64.encodeBase64String(source);
	}

	@Override
	public void disconnect() {
		webClient.get()
				.uri("/controller/disconnect")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class)
				.block();
	}

	public Mono<ControllerStatus> getStatusReactive() {
		var r = webClient.get()
				.uri("/controller/status")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class);
		return r;
	}

	@Override
	public void setStickAxis(SwitchSticks stick, SwitchSticksAxis axis, int axisValue) {
		String stickApiString = sticksToApi.get(stick);
		String axisApiString = stickAxisToApi.get(axis);
		int value = axisValue * 4;
		webClient.patch()
				.uri("/controller/stick/{stick}/{axis}/{value}", stickApiString, axisApiString, value)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class)
				.block();
	}

	@Override
	public void setButtonState(SwitchButtons button, boolean buttonState) {
		String buttonName = buttonsToApi.get(button);
		String action = buttonState ? "press" : "release";
		webClient.patch()
				.uri("/controller/button/{action}/{button_name}", action, buttonName)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class)
				.block();
	}

	@Override
	public void tapButton(SwitchButtons button) {
		String buttonName = buttonsToApi.get(button);
		webClient.patch()
				.uri("/controller/button/tap/{button_name}", buttonName)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class)
				.block();
	}

}
