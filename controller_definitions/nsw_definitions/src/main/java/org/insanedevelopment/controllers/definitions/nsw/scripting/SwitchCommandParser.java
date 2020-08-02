package org.insanedevelopment.controllers.definitions.nsw.scripting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

import org.insanedevelopment.controllers.definitions.common.action.AbstractAction;
import org.insanedevelopment.controllers.definitions.common.scripting.ResourceFinder;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswButtonChangeStateAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswButtonTapAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswConnectAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswDisconnectAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswLoopAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswNfcStartAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswNfcStopAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSequenceAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSetStickAxisAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSleepAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerType;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public enum SwitchCommandParser {
	BUTTON_STATE((sc, fd) -> new NswButtonChangeStateAction(SwitchButtons.valueOf(sc.next().toUpperCase()), convertUpDown(sc.next()))),
	SLEEP((sc, fd) -> new NswSleepAction(sc.nextLong())),
	BUTTON_TAP((sc, fd) -> new NswButtonTapAction(SwitchButtons.valueOf(sc.next().toUpperCase()), sc.nextLong(), sc.nextLong())),
	LOOP((sc, fd) -> new NswLoopAction(sc.nextLong(), createSequence(sc, fd, "END"))),
	STICK((sc, fd) -> new NswSetStickAxisAction(SwitchSticks.valueOf(sc.next()), SwitchSticksAxis.valueOf(sc.next()), sc.nextInt())),
	NFC_SEND((sc, fd) -> new NswNfcStartAction(fd.getResourceBytes("NFC", nullable(sc.next())))),
	NFC_REMOVE((sc, fd) -> new NswNfcStopAction()),
	CONNECT((sc, fd) -> new NswConnectAction(SwitchControllerType.valueOf(sc.next()), nullable(sc.next()),
			fd.getResourceBytes("CONNECT", nullable(sc.next())))),
	DISCONNECT((sc, fd) -> new NswDisconnectAction());

	private BiFunction<Scanner, ResourceFinder, AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> creationFunction;

	private SwitchCommandParser(
			BiFunction<Scanner, ResourceFinder, AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> creationFunction) {
		this.creationFunction = creationFunction;
	}

	public AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> parse(Scanner sc, ResourceFinder finder) {
		return creationFunction.apply(sc, finder);
	}

	public String getCommandName() {
		return name();
	}

	private static boolean convertUpDown(String next) {
		if ("hold".equalsIgnoreCase(next) || "down".equalsIgnoreCase(next)) {
			return true;
		}
		return false;
	}

	/*
	 * Static Methods for creating / scanning a script
	 */

	public static NswSequenceAction createSequence(Scanner sc, ResourceFinder finder, String endToken) {
		List<AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> scriptActions = new ArrayList<>();
		while (sc.hasNext()) {
			String commandName = sc.next();
			if (commandName.equalsIgnoreCase(endToken)) {
				break;
			}
			if (commandName.equals("#")) {
				sc.nextLine();
				continue;
			}
			var cp = getCommandParser(commandName);
			scriptActions.add(cp.parse(sc, finder));
		}
		return new NswSequenceAction(scriptActions);
	}

	private static SwitchCommandParser getCommandParser(String commandName) {
		for (var pp : SwitchCommandParser.values()) {
			if (pp.getCommandName().equalsIgnoreCase(commandName)) {
				return pp;
			}
		}
		throw new RuntimeException("Cannot find command " + commandName);
	}

	private static String nullable(String next) {
		return "null".equalsIgnoreCase(next) ? null : next;
	}

}
