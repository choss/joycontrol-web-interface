package org.insanedevelopment.controllers.definitions.nsw.actions;

import org.insanedevelopment.controllers.definitions.common.action.LoopAction;
import org.insanedevelopment.controllers.definitions.common.action.SequenceAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswLoopAction extends LoopAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswLoopAction() {
		super();
	}

	public NswLoopAction(long loopAmount, SequenceAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> child) {
		super(loopAmount, child);
	}

}
