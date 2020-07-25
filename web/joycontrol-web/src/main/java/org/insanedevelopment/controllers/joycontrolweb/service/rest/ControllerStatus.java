package org.insanedevelopment.controllers.joycontrolweb.service.rest;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControllerStatus {

	@JsonProperty("connected")
	private Boolean connected;
	@JsonProperty("peer")
	private String peer;
	@JsonProperty("controller_type")
	private String controllerType;
	@JsonProperty("buttons")
	private Map<String, Boolean> buttons;
	@JsonProperty("nfc_active")
	private Boolean nfcActive;
	@JsonProperty("left_stick")
	private ControllerStatusStick leftStick;
	@JsonProperty("right_stick")
	private ControllerStatusStick rightStick;

	public String getPeer() {
		return peer;
	}

	public String getControllerType() {
		return controllerType;
	}

	public Map<String, Boolean> getButtons() {
		return buttons;
	}

	public Boolean getNfcActive() {
		return nfcActive;
	}

	public ControllerStatusStick getLeftStick() {
		return leftStick;
	}

	public ControllerStatusStick getRightStick() {
		return rightStick;
	}

	public Boolean getConnected() {
		return connected;
	}

}
