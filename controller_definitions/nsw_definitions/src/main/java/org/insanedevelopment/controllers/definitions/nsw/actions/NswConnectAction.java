package org.insanedevelopment.controllers.definitions.nsw.actions;

import java.util.Collections;
import java.util.List;

import org.insanedevelopment.controllers.definitions.common.action.AbstractAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerType;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswConnectAction extends AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	private SwitchControllerType type;
	private String reconnectAddress;
	private byte[] firmware;

	public NswConnectAction(SwitchControllerType type, String reconnectAddress, byte[] firmware) {
		this.type = type;
		this.reconnectAddress = reconnectAddress;
		this.firmware = firmware;
	}

	@Override
	public void visit(SwitchControllerConnection consumer) {
		consumer.connect(type, reconnectAddress, firmware);
	}

	@Override
	protected List<AbstractAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons>> getChildren() {
		return Collections.emptyList();
	}

}
