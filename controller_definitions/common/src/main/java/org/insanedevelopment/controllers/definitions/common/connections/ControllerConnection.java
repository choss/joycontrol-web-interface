package org.insanedevelopment.controllers.definitions.common.connections;

public interface ControllerConnection<Stick extends Enum<?>, Axis extends Enum<?>, Button extends Enum<?>> {

	/**
	 * @param axis
	 * @param axisValue
	 *            between 0 and 1024
	 */
	void setStickAxis(Stick stick, Axis axis, int axisValue);

	/**
	 *
	 * @param button
	 * @param buttonState
	 *            true = pressed, false = not pressed
	 */
	void setButtonState(Button button, boolean buttonState);

}
