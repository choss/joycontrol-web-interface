package org.insanedevelopment.controllers.definitions.nsw.actions;

import java.util.Collections;
import java.util.List;

import org.insanedevelopment.controllers.definitions.common.action.AbstractAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswNfcStartAction extends AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	private byte[] nfcData;

	public NswNfcStartAction(byte[] nfcData) {
		this.nfcData = nfcData;
	}

	@Override
	public void visit(SwitchControllerConnection consumer) {
		log.debug("Setting nfc data");
		consumer.sendNfcData(nfcData);
	}

	@Override
	protected List<AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> getChildren() {
		return Collections.emptyList();
	}

}
