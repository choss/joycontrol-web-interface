package org.insanedevelopment.controllers.definitions.common.action;

import java.util.Collections;
import java.util.List;

import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;

public class LoopAction<CC extends ControllerConnection<Stick, Axis, Button>, Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>>
		extends AbstractAction<CC, Stick, Axis, Button> {

	private long loopAmount;
	private SequenceAction<CC, Stick, Axis, Button> child;

	public LoopAction() {
		super();
	}

	public LoopAction(long loopAmount, SequenceAction<CC, Stick, Axis, Button> child) {
		this.loopAmount = loopAmount;
		this.child = child;
	}

	@Override
	public String toString() {
		return "LoopDirective [loopAmount=" + loopAmount + ", directive=" + child + "]";
	}

	@Override
	public void visit(CC consumer) {
		for (int i = 0; i < loopAmount; i++) {
			if (stop) {
				break;
			}
			child.visit(consumer);
		}
	}

	@Override
	protected List<AbstractAction<CC, Stick, Axis, Button>> getChildren() {
		return Collections.singletonList(child);
	}

}
