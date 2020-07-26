package org.insanedevelopment.controllers.joycontrolweb.controllers;

import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerType;
import org.insanedevelopment.controllers.joycontrolweb.api.script.ScriptType;
import org.insanedevelopment.controllers.joycontrolweb.service.main.MainApplicationStateService;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.JoyControlRestClient;
import org.insanedevelopment.controllers.joycontrolweb.service.script.ScriptManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	@Autowired
	private JoyControlRestClient client;
	@Autowired
	private MainApplicationStateService mainService;
	@Autowired
	private ScriptManagementService scriptService;

	@RequestMapping("/")
	public Mono<String> index(final Model model) {
		var scripts = new ReactiveDataDriverContextVariable(scriptService.getScriptsOfType(ScriptType.QUICK_ACCESS));
		model.addAttribute("state", mainService.getApplicationState());
		model.addAttribute("quick_scripts", scripts);
		return Mono.just("index");
	}

	@RequestMapping("/test")
	public Mono<String> connect(final Model model) {
		var result = client.connectReactive(SwitchControllerType.PRO_CONTROLLER, "EC:C4:0D:4B:B4:B2", null);
		model.addAttribute("greeting", result);
		return Mono.just("test");
	}

	@RequestMapping("/test2")
	public String disconnect(final Model model) {
		client.disconnect();
		return "test";
	}
}
