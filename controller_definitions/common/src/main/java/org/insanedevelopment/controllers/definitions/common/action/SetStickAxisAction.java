package org.insanedevelopment.controllers.definitions.common.action;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;

public class SetStickAxisAction<CC extends ControllerConnection<Stick, Axis, Button>, Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>>
		extends AbstractAction<CC, Stick, Axis, Button> {

	private Stick stick;
	private Axis axis;
	private int value;

	public SetStickAxisAction() {
		super();
	}

	public SetStickAxisAction(Stick stick, Axis axis, int value) {
		this.stick = stick;
		this.axis = axis;
		this.value = value;
		Validate.notNull(axis);
		Validate.inclusiveBetween(0, 1023, value);
	}

	@Override
	public void visit(CC consumer) {
		consumer.setStickAxis(stick, axis, value);
	}

	@Override
	protected List<AbstractAction<CC, Stick, Axis, Button>> getChildren() {
		return Collections.emptyList();
	}

}
