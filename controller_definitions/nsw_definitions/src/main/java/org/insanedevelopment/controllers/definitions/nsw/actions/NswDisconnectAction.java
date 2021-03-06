package org.insanedevelopment.controllers.definitions.nsw.actions;

import java.util.Collections;
import java.util.List;

import org.insanedevelopment.controllers.definitions.common.action.AbstractAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswDisconnectAction extends AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswDisconnectAction() {
	}

	@Override
	public void visit(SwitchControllerConnection consumer) {
		log.debug("Disconnecting from switch");
		consumer.disconnect();
	}

	@Override
	protected List<AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> getChildren() {
		return Collections.emptyList();
	}

}
