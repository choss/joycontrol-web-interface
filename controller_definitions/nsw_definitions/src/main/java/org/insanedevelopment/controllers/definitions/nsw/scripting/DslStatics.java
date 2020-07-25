package org.insanedevelopment.controllers.definitions.nsw.scripting;

import java.util.Arrays;

import org.insanedevelopment.controllers.definitions.common.action.AbstractAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswButtonChangeStateAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswLoopAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSequenceAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSetStickAxisAction;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSleepAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class DslStatics {

	public static NswSequenceAction SEQ(AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>... subactions) {
		return new NswSequenceAction(Arrays.asList(subactions));
	}

	public static NswSetStickAxisAction JOYSTICK(SwitchSticks stick, SwitchSticksAxis axis, int value) {
		return new NswSetStickAxisAction(stick, axis, value);
	}

	public static NswButtonChangeStateAction BUTTON(SwitchButtons button, boolean state) {
		return new NswButtonChangeStateAction(button, state);
	}

	public static NswLoopAction LOOP(long times, AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>... subactions) {
		return new NswLoopAction(times, SEQ(subactions));
	}

	public static NswSleepAction DELAY(long milliseconds) {
		return new NswSleepAction(milliseconds);
	}

	public static NswSequenceAction INIT(AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>... subactions) {
		return new NswSequenceAction(Arrays.asList(subactions));
	}

}
