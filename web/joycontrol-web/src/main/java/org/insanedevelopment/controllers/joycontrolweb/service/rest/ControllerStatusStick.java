package org.insanedevelopment.controllers.joycontrolweb.service.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControllerStatusStick {

	@JsonProperty("x_axis")
	private Integer xAxis;
	@JsonProperty("y_axis")
	private Integer yAxis;
	@JsonProperty("is_center")
	private Boolean isCenter;

	public Integer getxAxis() {
		return xAxis;
	}

	public Integer getyAxis() {
		return yAxis;
	}

	public Boolean getIsCenter() {
		return isCenter;
	}

}