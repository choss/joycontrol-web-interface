package org.insanedevelopment.controllers.joycontrolweb.service.script;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.text.StringSubstitutor;
import org.insanedevelopment.controllers.definitions.common.scripting.ResourceFinder;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSequenceAction;
import org.insanedevelopment.controllers.definitions.nsw.scripting.NswScriptScanner;
import org.insanedevelopment.controllers.joycontrolweb.api.script.ScriptFile;
import org.insanedevelopment.controllers.joycontrolweb.api.script.ScriptMetadata;
import org.insanedevelopment.controllers.joycontrolweb.api.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@Service
public class ScriptManagementService {

	@Value("${ui.script.dir}")
	private String scriptDir;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ResourceFinder resourceFinder;

	private List<ScriptFile> scripts = new ArrayList<>();
	private Map<String, ScriptFile> idToScript = new HashMap<>();

	public Flux<ScriptFile> getScriptsOfType(ScriptType type) {
		return Flux.fromIterable(scripts).filter(s -> type == null || s.getScriptType() == type);
	}

	@PostConstruct
	public void refreshCaches() throws IOException {
		objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
		reloadScripts();
	}

	private void reloadScripts() throws IOException {
		scripts.clear();
		idToScript.clear();
		var path = new File(scriptDir).getCanonicalPath();

		var resolver = new PathMatchingResourcePatternResolver();
		var resources = resolver.getResources("file://" + path + "/*.script");

		for (Resource resource : resources) {
			var fileContents = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
			if (fileContents.size() == 0) {
				continue;
			}
			var fileName = resource.getFilename();
			var metaData = getMetataData(fileContents);
			var sf = new ScriptFile(metaData, String.join("\n", fileContents.toArray(new String[fileContents.size()])), fileName);
			scripts.add(sf);
			idToScript.put(sf.getId(), sf);
		}
		Collections.sort(scripts, this::compareScriptFiles);
	}

	private ScriptMetadata getMetataData(List<String> fileContents) throws IOException {
		var firstLine = fileContents.get(0);
		if (firstLine.startsWith("# Meta:")) {
			return objectMapper.readValue(firstLine.substring(7), ScriptMetadata.class);
		} else {
			return new ScriptMetadata();
		}
	}

	private int compareScriptFiles(ScriptFile a, ScriptFile b) {
		return new CompareToBuilder()
				.append(a.getScriptType(), b.getScriptType())
				.append(a.getOrder(), b.getOrder())
				.append(a.getTitle(), b.getTitle())
				.append(a.getFileName(), b.getFileName())
				.build().intValue();
	}

	public NswSequenceAction createExecutableScript(String scriptId, String reconnectAddress, String spiFileName, String nfcFileName) {
		String scriptContent = idToScript.get(scriptId).getScriptContents();
		Map<String, String> variableMap = Map.of("switch_mac", Objects.toString(reconnectAddress),
				"spi_firm", Objects.toString(spiFileName),
				"nfc_file", Objects.toString(nfcFileName));

		String templatedScript = StringSubstitutor.replace(scriptContent, variableMap);
		NswSequenceAction result = NswScriptScanner.parseScript(templatedScript, resourceFinder);
		return result;
	}

}
