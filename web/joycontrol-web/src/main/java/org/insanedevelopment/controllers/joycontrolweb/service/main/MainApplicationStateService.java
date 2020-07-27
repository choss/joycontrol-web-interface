package org.insanedevelopment.controllers.joycontrolweb.service.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSequenceAction;
import org.insanedevelopment.controllers.joycontrolweb.api.ApplicationState;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.ControllerStatus;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.JoyControlRestClient;
import org.insanedevelopment.controllers.joycontrolweb.service.script.ScriptManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MainApplicationStateService {

	private static final ExecutorService BACKGROUND_EXECUTOR = Executors.newSingleThreadExecutor();
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplicationStateService.class);

	@Autowired
	private JoyControlRestClient restClient;

	@Autowired
	private ScriptManagementService scriptService;

	@Value("${ui.data.dir}")
	private String dataDir;

	private List<Pair<String, String>> allKnownMacs = Collections.emptyList();

	private String reconnectAddress;
	private String nfcFileName;
	private String spiFileName;

	private Pair<String, NswSequenceAction> runningScript;

	public Mono<ApplicationState> getApplicationState() {
		Mono<ControllerStatus> controllerState = restClient.getStatusReactive();
		return controllerState.map(cs -> new ApplicationState(cs, reconnectAddress, runningScript != null, nfcFileName, spiFileName,
				runningScript != null ? runningScript.getKey() : null));
	}

	public Flux<Pair<String, String>> getAllKnownMacs() {
		return Flux.fromIterable(allKnownMacs);
	}

	@PostConstruct
	public void init() throws IOException {
		loadLastData();
		refreshCaches();
	}

	public void refreshCaches() throws IOException {
		reloadMacs();
	}

	private void loadLastData() throws IOException {
		Properties p = new Properties();
		var settingsFile = new File(FilenameUtils.concat(dataDir, "settings.properties"));
		if (settingsFile.exists()) {
			try (var is = FileUtils.openInputStream(settingsFile)) {
				p.load(is);
				reconnectAddress = StringUtils.trimToNull(p.getProperty("reconnectAddress", ""));
				nfcFileName = StringUtils.trimToNull(p.getProperty("nfcFileName", ""));
				spiFileName = StringUtils.trimToNull(p.getProperty("spiFileName", ""));
			}
		}
	}

	private void saveData() {
		Properties p = new Properties();
		p.setProperty("reconnectAddress", Objects.toString(reconnectAddress, ""));
		p.setProperty("nfcFileName", Objects.toString(nfcFileName, ""));
		p.setProperty("spiFileName", Objects.toString(spiFileName, ""));

		var settingsFile = new File(FilenameUtils.concat(dataDir, "settings.properties"));
		try (var os = FileUtils.openOutputStream(settingsFile)) {
			p.store(os, "Settings file");
		} catch (IOException iox) {
			LOGGER.error("Error saving settings file", iox);
		}
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
		saveData();
	}

	public void runScriptInBackground(String scriptId) {
		if (runningScript != null) {
			return;
		}
		NswSequenceAction script = scriptService.createExecutableScript(scriptId, reconnectAddress, spiFileName, nfcFileName);
		BACKGROUND_EXECUTOR.submit(() -> runScript(scriptId, script));
	}

	private void runScript(String scriptId, NswSequenceAction script) {
		try {
			runningScript = Pair.of(scriptId, script);
			script.visit(restClient);
		} catch (Exception e) {
			LOGGER.error("Error running script", e);
		} finally {
			runningScript = null;
		}
	}

	public void stopRunningScript() {
		if (runningScript != null) {
			runningScript.getRight().requestStop();
		}
	}

	public void setSpiFile(String spiFile) {
		spiFileName = StringUtils.trimToNull(spiFile);
		saveData();
	}

	public void setNfcFile(String nfcFile) {
		nfcFileName = StringUtils.trimToNull(nfcFile);
		saveData();
	}

}
