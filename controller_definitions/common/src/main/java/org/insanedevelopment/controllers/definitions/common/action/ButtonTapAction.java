package org.insanedevelopment.controllers.definitions.common.action;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;

public class ButtonTapAction<CC extends ControllerConnection<Stick, Axis, Button>, Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>>
		extends AbstractAction<CC, Stick, Axis, Button> {

	private Button button;
	private long pressDurationInMs;
	private long delayAfterInMs;

	public ButtonTapAction(Button button, long pressDurationInMs, long delayAfterInMs) {
		Validate.notNull(button);
		this.button = button;
		this.pressDurationInMs = pressDurationInMs;
		this.delayAfterInMs = delayAfterInMs;
	}

	@Override
	public void visit(CC consumer) {
		consumer.setButtonState(button, true);
		sleep(pressDurationInMs);
		consumer.setButtonState(button, false);
		sleep(delayAfterInMs);
	}

	private void sleep(long durationInMs) {
		try {
			Thread.sleep(durationInMs);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	protected List<AbstractAction<CC, Stick, Axis, Button>> getChildren() {
		return Collections.emptyList();
	}

}
