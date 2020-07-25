package org.insanedevelopment.controllers.definitions.nsw.scripting;

import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.insanedevelopment.controllers.definitions.nsw.actions.NswSequenceAction;
import org.insanedevelopment.controllers.definitions.nsw.gamepad.SwitchButtons;

public class ScriptDemo {

	public static NswSequenceAction generateDemo1() {
		return DslStatics.INIT(
				DslStatics.LOOP(20,
						DslStatics.BUTTON(SwitchButtons.B, true),
						DslStatics.DELAY(1000),
						DslStatics.BUTTON(SwitchButtons.B, false),
						DslStatics.DELAY(1000)));
	}

	public static void main(String[] args) {
		var d = generateDemo1();
		System.out.println(ReflectionToStringBuilder.reflectionToString(d, new MultilineRecursiveToStringStyle()));
	}

}
