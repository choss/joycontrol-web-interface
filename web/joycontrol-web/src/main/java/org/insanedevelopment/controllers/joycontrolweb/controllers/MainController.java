package org.insanedevelopment.controllers.joycontrolweb.controllers;

import java.io.IOException;

import org.insanedevelopment.controllers.joycontrolweb.api.script.ScriptType;
import org.insanedevelopment.controllers.joycontrolweb.service.main.MainApplicationStateService;
import org.insanedevelopment.controllers.joycontrolweb.service.main.ResourceManagementService;
import org.insanedevelopment.controllers.joycontrolweb.service.script.ScriptManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private MainApplicationStateService mainService;
	@Autowired
	private ScriptManagementService scriptService;
	@Autowired
	private ResourceManagementService resourceManagementService;

	@RequestMapping("/")
	public Mono<String> index(final Model model) {
		var scripts = new ReactiveDataDriverContextVariable(scriptService.getScriptsOfType(ScriptType.QUICK_ACCESS));
		model.addAttribute("state", mainService.getApplicationState());
		model.addAttribute("quick_scripts", scripts);
		model.addAttribute("normal_scripts", scriptService.getScriptsOfType(ScriptType.NORMAL));
		model.addAttribute("all_macs", mainService.getAllKnownMacs());
		model.addAttribute("all_spis", resourceManagementService.getAllSpi());
		model.addAttribute("all_nfcs", resourceManagementService.getAllAmiibo());
		return Mono.just("index");
	}

	@RequestMapping("/settings/update_mac")
	public Mono<String> updateMac(@RequestParam("return_of_the_mac") String mac) {
		mainService.setReconnectAddress(mac);
		return Mono.just("redirect:/");
	}

	@RequestMapping("/settings/update_spi")
	public Mono<String> updateSPI(@RequestParam("spi_file") String mac) {
		mainService.setSpiFile(mac);
		return Mono.just("redirect:/");
	}

	@RequestMapping("/settings/update_nfc")
	public Mono<String> updateNfc(@RequestParam("nfc_file") String mac) {
		mainService.setNfcFile(mac);
		return Mono.just("redirect:/");
	}

	@RequestMapping("/script/start")
	public Mono<String> startScript(@RequestParam("id") String scriptId) {
		mainService.runScriptInBackground(scriptId);
		return Mono.just("redirect:/");
	}

	@RequestMapping("/script/stop")
	public Mono<String> stopScript() {
		mainService.stopRunningScript();
		return Mono.just("redirect:/");
	}

	@RequestMapping("/app/refresh")
	public Mono<String> refreshCaches() {
		try {
			mainService.refreshCaches();
			scriptService.refreshCaches();
			resourceManagementService.refreshCaches();
		} catch (IOException iox) {
			LOGGER.error("Error refreshing caches", iox);
		}
		return Mono.just("redirect:/");
	}

}
