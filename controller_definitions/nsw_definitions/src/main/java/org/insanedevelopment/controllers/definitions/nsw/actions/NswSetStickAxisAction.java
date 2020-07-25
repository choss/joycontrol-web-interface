package org.insanedevelopment.controllers.definitions.nsw.actions;

import org.insanedevelopment.controllers.definitions.common.action.SetStickAxisAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswSetStickAxisAction extends SetStickAxisAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswSetStickAxisAction() {
		super();
	}

	public NswSetStickAxisAction(SwitchSticks stick, SwitchSticksAxis axis, int value) {
		super(stick, axis, value);
	}

}
