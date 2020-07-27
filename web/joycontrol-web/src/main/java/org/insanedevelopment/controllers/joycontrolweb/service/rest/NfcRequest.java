package org.insanedevelopment.controllers.joycontrolweb.service.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NfcRequest {

	@JsonProperty("nfc_data")
	private String nfcData;

	public NfcRequest(String nfcData) {
		this.nfcData = nfcData;
	}

	public String getNfcData() {
		return nfcData;
	}

}
