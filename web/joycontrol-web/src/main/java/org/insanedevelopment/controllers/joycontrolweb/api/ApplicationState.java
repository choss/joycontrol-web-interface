package org.insanedevelopment.controllers.joycontrolweb.api;

import org.insanedevelopment.controllers.joycontrolweb.service.rest.ControllerStatus;

public class ApplicationState {

	private ControllerStatus controllerStatus;

	private String reconnectAddress;
	private boolean scriptActive;
	private String nfcFileName;
	private String firmwareFileName;

	public ApplicationState(ControllerStatus controllerStatus, String reconnectAddress, boolean isScriptRunning, String nfcFileName, String firmwareFileName) {
		this.controllerStatus = controllerStatus;
		this.reconnectAddress = reconnectAddress;
		scriptActive = isScriptRunning;
		this.nfcFileName = nfcFileName;
		this.firmwareFileName = firmwareFileName;
	}

	public ControllerStatus getControllerStatus() {
		return controllerStatus;
	}

	public String getReconnectAddress() {
		return reconnectAddress;
	}

	public boolean isScriptActive() {
		return scriptActive;
	}

	public String getNfcFileName() {
		return nfcFileName;
	}

	public String getFirmwareFileName() {
		return firmwareFileName;
	}

	public String getPeer() {
		return controllerStatus.getPeer();
	}

	public Boolean getConnected() {
		return controllerStatus.getConnected();
	}

	public Boolean getNfcActive() {
		return controllerStatus.getNfcActive();
	}

}
