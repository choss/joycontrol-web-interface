package org.insanedevelopment.controllers.joycontrolweb.service.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.insanedevelopment.controllers.definitions.common.scripting.ResourceFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class ResourceManagementService implements ResourceFinder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceManagementService.class);

	@Value("${ui.firm.dir}")
	private String firmDir;
	@Value("${ui.nfc.dir}")
	private String nfcDir;

	private Map<String, Resource> allFirmwares;
	private Map<String, Resource> allAmiibos;

	@Override
	public byte[] getResourceBytes(String commandName, String resourceName) {
		if ("CONNECT".equals(commandName)) {
			var resource = allFirmwares.get(resourceName);
			if (resource == null) {
				return null;
			}
			try (var is = resource.getInputStream()) {
				return IOUtils.toByteArray(is);
			} catch (IOException iox) {
				LOGGER.error("Error reading file {} {}", commandName, resourceName, iox);
			}
		}

		if ("NFC".equals(commandName)) {
			var resource = allAmiibos.get(resourceName);
			if (resource == null) {
				return null;
			}
			try (var is = resource.getInputStream()) {
				return IOUtils.toByteArray(is);
			} catch (IOException iox) {
				LOGGER.error("Error reading file {} {}", commandName, resourceName, iox);
			}
		}

		return null;
	}

	public Flux<String> getAllSpi() {
		return Flux.fromIterable(allFirmwares.keySet()).sort();
	}

	public Flux<String> getAllAmiibo() {
		return Flux.fromIterable(allAmiibos.keySet()).sort();
	}

	@PostConstruct
	public void refreshCaches() throws IOException {
		allFirmwares = readDirContents(firmDir);
		allAmiibos = readDirContents(nfcDir);
	}

	private Map<String, Resource> readDirContents(String dir) throws IOException {
		var path = new File(dir).getCanonicalPath();

		var resolver = new PathMatchingResourcePatternResolver();
		var resources = resolver.getResources("file://" + path + "/**/*.bin");
		Map<String, Resource> result = new HashMap<>(resources.length * 2);

		for (Resource resource : resources) {
			result.put(resource.getFilename().replace(' ', '_'), resource);
		}

		return result;
	}

}
