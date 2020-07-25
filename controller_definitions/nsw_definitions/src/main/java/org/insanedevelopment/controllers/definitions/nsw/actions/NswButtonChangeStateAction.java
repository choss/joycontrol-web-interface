package org.insanedevelopment.controllers.definitions.nsw.actions;

import org.insanedevelopment.controllers.definitions.common.action.ButtonChangeStateAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswButtonChangeStateAction extends ButtonChangeStateAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswButtonChangeStateAction() {
		super();
	}

	public NswButtonChangeStateAction(SwitchButtons button, boolean state) {
		super(button, state);
	}

}
