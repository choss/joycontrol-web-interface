package org.insanedevelopment.controllers.joycontrolweb.api.script;

import java.util.EnumSet;

import org.apache.commons.lang3.StringUtils;

public class ScriptFile {

	private ScriptMetadata metadata;
	private String scriptContents;
	private String fileName;

	public ScriptFile(ScriptMetadata metadata, String scriptContents, String fileName) {
		this.metadata = metadata;
		this.scriptContents = scriptContents;
		this.fileName = fileName;
	}

	public ScriptMetadata getMetadata() {
		return metadata;
	}

	public String getScriptContents() {
		return scriptContents;
	}

	public String getFileName() {
		return fileName;
	}

	public ScriptType getScriptType() {
		return metadata.getScriptType();
	}

	public String getTitle() {
		var mtitle = metadata.getTitle();
		return StringUtils.isBlank(mtitle) ? getFileName() : mtitle;
	}

	public int getOrder() {
		return metadata.getOrder();
	}

	public EnumSet<ScriptVariableRequirements> getRequirements() {
		return metadata.getRequirements();
	}

	public String getId() {
		return fileName;
	}

	@Override
	public String toString() {
		return "ScriptFile [metadata=" + metadata + ", fileName=" + fileName + "]";
	}

}
