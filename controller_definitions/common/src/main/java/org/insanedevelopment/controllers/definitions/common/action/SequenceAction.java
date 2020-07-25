package org.insanedevelopment.controllers.definitions.common.action;

import java.util.List;

import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;

public class SequenceAction<CC extends ControllerConnection<Stick, Axis, Button>, Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>>
		extends AbstractAction<CC, Stick, Axis, Button> {

	private List<AbstractAction<CC, Stick, Axis, Button>> children;

	public SequenceAction() {
		super();
	}

	public SequenceAction(List<AbstractAction<CC, Stick, Axis, Button>> children) {
		this.children = children;
	}

	@Override
	public void visit(CC consumer) {
		for (var child : children) {
			child.visit(consumer);
		}

	}

	@Override
	protected List<AbstractAction<CC, Stick, Axis, Button>> getChildren() {
		return children;
	}

}
