package org.insanedevelopment.controllers.definitions.nsw.scripting;

import java.util.Scanner;

import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.insanedevelopment.controllers.definitions.common.scripting.ResourceFinder;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSequenceAction;

public class NswScriptScanner {

	public static NswSequenceAction parseScript(String script, ResourceFinder rf) {
		try (Scanner sc = new Scanner(script)) {
			var startToken = sc.next();
			while ("#".equals(startToken)) {
				sc.nextLine();
				startToken = sc.next();
			}
			if (!"NSW-START".equalsIgnoreCase(startToken)) {
				throw new RuntimeException("Script needs to start with NSW-START ");
			}

			return SwitchCommandParser.createSequence(sc, rf, null);
		}
	}

	public static void main(String[] args) {
		var r = parseScript(ACNH_SCRIPT, new ResourceFinder() {

			@Override
			public byte[] getResourceBytes(String commandName, String resourceName) {
				System.out.println(commandName + " - " + resourceName);
				return null;
			}
		});
		System.out.println(ReflectionToStringBuilder.reflectionToString(r, new MultilineRecursiveToStringStyle()));
		System.out.println(ACNH_SCRIPT);
	}

	private static String ACNH_SCRIPT = "# Test\nNSW-START\r\n" +
			"CONNECT PRO_CONTROLLER ${switch_mac} spi.firm\r\n" +
			"# setup - go back to game\r\n" +
			"BUTTON_TAP DPAD_DOWN 300 300\r\n" +
			"BUTTON_TAP DPAD_DOWN 300 300\r\n" +
			"BUTTON_TAP DPAD_RIGHT 300 300\r\n" +
			"BUTTON_TAP DPAD_RIGHT 300 300\r\n" +
			"BUTTON_TAP DPAD_RIGHT 300 300\r\n" +
			"BUTTON_TAP A 300 2000\r\n" +
			"BUTTON_TAP A 300 1500\r\n" +
			"\r\n" +
			"CONNECT PRO_CONTROLLER ${switch_mac} spi.firm\r\n" +
			"\r\n" +
			"BUTTON_TAP A 300 1000\r\n" +
			"BUTTON_TAP A 300 1000\r\n" +
			"BUTTON_TAP B 300 1000\r\n" +
			"BUTTON_TAP DPAD_LEFT 300 300\r\n" +
			"BUTTON_TAP DPAD_LEFT 300 300\r\n" +
			"BUTTON_TAP DPAD_LEFT 300 300\r\n" +
			"BUTTON_TAP DPAD_UP 300 300\r\n" +
			"BUTTON_TAP A 300 300\r\n" +
			"\r\n" +
			"# actual loop\r\n" +
			"LOOP 500\r\n" +
			"BUTTON_TAP A 300 1000\r\n" +
			"END\r\n" +
			"\r\n" +
			"# go back to controller config\r\n" +
			"BUTTON_TAP HOME 300 300\r\n" +
			"BUTTON_TAP DPAD_DOWN 300 300\r\n" +
			"BUTTON_TAP DPAD_RIGHT 300 300\r\n" +
			"BUTTON_TAP DPAD_RIGHT 300 300\r\n" +
			"BUTTON_TAP DPAD_RIGHT 300 300\r\n" +
			"BUTTON_TAP A 300 1500\r\n" +
			"BUTTON_TAP A 300 4000\r\n" +
			"";

}
