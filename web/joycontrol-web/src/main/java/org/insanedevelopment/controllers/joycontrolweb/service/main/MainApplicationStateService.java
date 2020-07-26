package org.insanedevelopment.controllers.joycontrolweb.service.main;

import org.insanedevelopment.controllers.joycontrolweb.api.ApplicationState;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.ControllerStatus;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.JoyControlRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MainApplicationStateService {

	@Autowired
	private JoyControlRestClient restClient;

	private String reconnectAddress;
	private String nfcFileName;
	private String spiFileName;

	private boolean isScriptRunning = false;

	public Mono<ApplicationState> getApplicationState() {
		Mono<ControllerStatus> controllerState = restClient.getStatusReactive();
		return controllerState.map(cs -> new ApplicationState(cs, reconnectAddress, isScriptRunning, nfcFileName, spiFileName));
	}

}
