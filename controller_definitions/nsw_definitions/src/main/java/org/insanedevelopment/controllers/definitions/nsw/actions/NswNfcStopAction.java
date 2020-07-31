package org.insanedevelopment.controllers.definitions.nsw.actions;

import java.util.Collections;
import java.util.List;

import org.insanedevelopment.controllers.definitions.common.action.AbstractAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswNfcStopAction extends AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswNfcStopAction() {
	}

	@Override
	public void visit(SwitchControllerConnection consumer) {
		log.debug("Removing nfc data");
		consumer.removeNfcData();
	}

	@Override
	protected List<AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> getChildren() {
		return Collections.emptyList();
	}

}
