package org.insanedevelopment.controllers.joycontrolweb.controllers;

import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerType;
import org.insanedevelopment.controllers.joycontrolweb.service.rest.JoyControlRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	@Autowired
	private JoyControlRestClient client;

	@RequestMapping("/")
	public String index(final Model model) {
		model.addAttribute("greeting", "Hello");

		return "index";
	}

	@RequestMapping("/test")
	public Mono<String> connect(final Model model) {
		var result = client.connectReactive(SwitchControllerType.PRO_CONTROLLER, "", null);
		model.addAttribute("greeting", result);
		return Mono.just("index");
	}

	@RequestMapping("/test2")
	public String disconnect(final Model model) {
		client.disconnect();
		return "index";
	}
}
