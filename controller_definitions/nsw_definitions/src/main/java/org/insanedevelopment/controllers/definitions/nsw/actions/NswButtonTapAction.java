package org.insanedevelopment.controllers.definitions.nsw.actions;

import org.insanedevelopment.controllers.definitions.common.action.ButtonTapAction;
import org.insanedevelopment.controllers.definitions.nsw.connections.SwitchControllerConnection;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticks;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchSticksAxis;

public class NswButtonTapAction extends ButtonTapAction<SwitchControllerConnection, SwitchSticks, SwitchSticksAxis, SwitchButtons> {

	public NswButtonTapAction(SwitchButtons button, long pressDurationInMs, long delayAfterInMs) {
		super(button, pressDurationInMs, delayAfterInMs);
	}

	@Override
	public void visit(SwitchControllerConnection consumer) {
		if (pressDurationInMs == 300) {
			log.debug("Tapping button with API {} {}", button, delayAfterInMs);
			consumer.tapButton(button);
			sleep(delayAfterInMs);
		} else {
			super.visit(consumer);
		}
	}

}
