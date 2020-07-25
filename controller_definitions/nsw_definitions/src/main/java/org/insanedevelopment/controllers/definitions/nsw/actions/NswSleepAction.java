package org.insanedevelopment.controllers.definitions.nsw.actions;

import org.insanedevelopment.controllers.definitions.common.action.SleepAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswSleepAction extends SleepAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswSleepAction() {
		super();
	}

	public NswSleepAction(long duration) {
		super(duration);
	}

}
