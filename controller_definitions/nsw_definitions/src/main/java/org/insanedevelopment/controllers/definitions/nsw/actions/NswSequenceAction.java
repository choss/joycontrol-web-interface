package org.insanedevelopment.controllers.definitions.nsw.actions;

import java.util.List;

import org.insanedevelopment.controllers.definitions.common.action.AbstractAction;
import org.insanedevelopment.controllers.definitions.common.action.SequenceAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswSequenceAction extends SequenceAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswSequenceAction() {
		super();
	}

	public NswSequenceAction(List<AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> children) {
		super(children);
	}

}
