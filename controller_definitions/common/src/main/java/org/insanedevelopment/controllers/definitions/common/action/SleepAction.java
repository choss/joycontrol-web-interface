package org.insanedevelopment.controllers.definitions.common.action;

import java.util.Collections;
import java.util.List;

import org.insanedevelopment.controllers.definitions.common.connections.ControllerConnection;

public class SleepAction<CC extends ControllerConnection<Stick, Axis, Button>, Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>>
		extends AbstractAction<CC, Stick, Axis, Button> {

	private long duration;

	public SleepAction() {
		super();
	}

	public SleepAction(long duration) {
		this.duration = duration;
	}

	@Override
	public void visit(CC consumer) {
		log.debug("Sleeping {}", duration);
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	protected List<AbstractAction<CC, Stick, Axis, Button>> getChildren() {
		return Collections.emptyList();
	}

}
