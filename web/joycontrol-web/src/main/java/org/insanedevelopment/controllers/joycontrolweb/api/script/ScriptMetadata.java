package org.insanedevelopment.controllers.joycontrolweb.api.script;

import java.util.EnumSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScriptMetadata {

	@JsonProperty("type")
	private ScriptType scriptType = ScriptType.NORMAL;
	@JsonProperty("title")
	private String title = null;
	@JsonProperty("order")
	private int order = 1000;
	@JsonProperty("requirements")
	private EnumSet<ScriptVariableRequirements> requirements = EnumSet.noneOf(ScriptVariableRequirements.class);

	public ScriptMetadata() {
		super();
	}

	public ScriptMetadata(ScriptType scriptType, String title, int order, EnumSet<ScriptVariableRequirements> requirements) {
		this.scriptType = scriptType;
		this.title = title;
		this.order = order;
		this.requirements = requirements;
	}

	public ScriptType getScriptType() {
		return scriptType;
	}

	public String getTitle() {
		return title;
	}

	public int getOrder() {
		return order;
	}

	public EnumSet<ScriptVariableRequirements> getRequirements() {
		return requirements;
	}

	@Override
	public String toString() {
		return "ScriptMetadata [scriptType=" + scriptType + ", title=" + title + ", order=" + order + ", requirements=" + requirements + "]";
	}

}
