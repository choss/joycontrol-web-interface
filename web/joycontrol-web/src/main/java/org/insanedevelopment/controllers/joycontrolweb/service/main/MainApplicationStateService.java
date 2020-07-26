package org.insanedevelopment.controllers.joycontrolweb.service.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.controllers.joycontrolweb.api.ApplicationState;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.ControllerStatus;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.JoyControlRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MainApplicationStateService {

	@Autowired
	private JoyControlRestClient restClient;

	@Value("${ui.data.dir}")
	private String dataDir;

	private List<Pair<String, String>> allKnownMacs = Collections.emptyList();

	private String reconnectAddress;
	private String nfcFileName;
	private String spiFileName;

	private boolean isScriptRunning = false;

	public Mono<ApplicationState> getApplicationState() {
		Mono<ControllerStatus> controllerState = restClient.getStatusReactive();
		return controllerState.map(cs -> new ApplicationState(cs, reconnectAddress, isScriptRunning, nfcFileName, spiFileName));
	}

	public Flux<Pair<String, String>> getAllKnownMacs() {
		return Flux.fromIterable(allKnownMacs);
	}

	@PostConstruct
	public void refreshCaches() throws IOException {
		reloadMacs();
	}

	private void reloadMacs() throws IOException {
		var path = new File(dataDir).getCanonicalPath();
		var configLines = IOUtils.readLines(new FileSystemResourceLoader().getResource(path + "/switch_macs.csv").getInputStream(),
				StandardCharsets.UTF_8);
		List<Pair<String, String>> allKnownMacs = new ArrayList<>(configLines.size() + 2);
		for (String line : configLines) {
			var splitLine = line.split(",");
			var mac = splitLine[0].trim();
			var description = mac;
			if (splitLine.length > 1) {
				description = description + " " + splitLine[1].trim();
			}
			allKnownMacs.add(Pair.of(mac, description));
		}
		this.allKnownMacs = allKnownMacs;
	}

	public void setReconnectAddress(String mac) {
		reconnectAddress = StringUtils.trimToNull(mac);
	}

}
