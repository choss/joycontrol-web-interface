package org.insanedevelopment.controllers.definitions.common.action;

import java.util.List;

import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;

public abstract class AbstractAction<CC extends ControllerConnection<Stick, Axis, Button>, Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>> {

	protected volatile boolean stop = false;

	public abstract void visit(CC consumer);

	public void requestStop() {
		this.stop = true;
		for (var child : getChildren()) {
			child.requestStop();
		}
	}

	protected abstract List<AbstractAction<CC, Stick, Axis, Button>> getChildren();

}
