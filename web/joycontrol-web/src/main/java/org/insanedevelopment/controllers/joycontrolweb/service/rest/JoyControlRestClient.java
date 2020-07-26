package org.insanedevelopment.controllers.joycontrolweb.service.rest;

import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerType;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class JoyControlRestClient implements SwitchControllerConnection {

	private WebClient webClient;

	public JoyControlRestClient() {
		webClient = WebClient.create("http://switch-controller-emulator:8000/");
	}

	// @Override
	// public void sendAxisState(StickAxis axis, int axisValue) {
	// int value = axisValue * 4;
	// webClient.patchForObject("/controller/stick/{stick}/{axis}/{value}",
	// null, ControllerStatus.class, "l_stick", axis, value);
	// }
	//
	// @Override
	// public void sendButtonState(GamepadButton button, boolean buttonState) {
	// String buttonName = button.getSwitchButtonName();
	// String action = buttonState ? "press" : "release";
	// webClient.patchForObject("/controller/button/{action}/{button_name}",
	// null, ControllerStatus.class, action, buttonName);
	// }

	@Override
	public void sendNfcData(byte[] nfcData) {
		// TODO Auto-generated method stub

	}

	@Override
	public String connect(SwitchControllerType type, String reconnectAddress, byte[] firmware) {
		String controllerType = type.name();
		String spiFirmware = null;
		ConnectRequest cr = new ConnectRequest(controllerType, reconnectAddress, spiFirmware);
		var result = webClient.post()
				.uri("/controller/connect")
				.bodyValue(cr)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(ControllerStatus.class);
		result.subscribe(a -> System.out.println(a.getBody()));
		return "a";
	}

	public Mono<String> connectReactive(SwitchControllerType type, String reconnectAddress, byte[] firmware) {
		String controllerType = type.name();
		String spiFirmware = null;
		ConnectRequest cr = new ConnectRequest(controllerType, reconnectAddress, spiFirmware);
		var result = webClient.post()
				.uri("/controller/connect")
				.bodyValue(cr)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class);
		return result.map(cs -> cs.getPeer());
	}

	@Override
	public void disconnect() {
		webClient.get().uri("/controller/disconnect")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ControllerStatus.class)
				.subscribe(a -> System.out.println(a));
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setButtonState(SwitchButtons button, boolean buttonState) {
		// TODO Auto-generated method stub

	}

}