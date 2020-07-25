package org.insanedevelopment.controllers.joycontrolweb.service.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectRequest {

	@JsonProperty("controller_type")
	private String controllerType;
	@JsonProperty("spi_firm")
	private String spiFirm;
	@JsonProperty("reconnect_address")
	private String reconnectAddress;

	public ConnectRequest(String controllerType, String reconnectAddress, String spiFirm) {
		super();
		this.controllerType = controllerType;
		this.spiFirm = spiFirm;
		this.reconnectAddress = reconnectAddress;
	}

	public String getControllerType() {
		return controllerType;
	}

	public String getSpiFirm() {
		return spiFirm;
	}

	public String getReconnectAddress() {
		return reconnectAddress;
	}

}