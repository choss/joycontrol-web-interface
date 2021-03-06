package org.insanedevelopment.controllers.definitions.nsw.connections;

import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public interface SwitchControllerConnection extends ControllerConnection<SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	void sendNfcData(byte[] nfcData);

	void removeNfcData();

	String connect(SwitchControllerType type, String reconnectAddress, byte[] firmware);

	void disconnect();

	void tapButton(SwitchButtons button);

}
