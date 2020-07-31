package org.insanedevelopment.controllers.definitions.common.action;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;

public class ButtonChangeStateAction<CC extends ControllerConnection<Stick, Axis, Button>, Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>>
		extends AbstractAction<CC, Stick, Axis, Button> {

	private Button button;
	private boolean state;

	public ButtonChangeStateAction() {
		super();
	}

	public ButtonChangeStateAction(Button button, boolean state) {
		Validate.notNull(button);
		this.button = button;
		this.state = state;
	}

	@Override
	public void visit(CC consumer) {
		log.debug("Setting button state {} {}", button, state);
		consumer.setButtonState(button, state);
	}

	@Override
	protected List<AbstractAction<CC, Stick, Axis, Button>> getChildren() {
		return Collections.emptyList();
	}

}
