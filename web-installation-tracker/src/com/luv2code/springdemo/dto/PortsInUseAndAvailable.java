package com.luv2code.springdemo.dto;

public class PortsInUseAndAvailable {
	
	private String portInUse;
	
	private String portAvailable;	

	public PortsInUseAndAvailable(String portInUse, String portAvailable) {
		super();
		this.portInUse = portInUse;
		this.portAvailable = portAvailable;
	}

	public String getPortInUse() {
		return portInUse;
	}

	public void setPortInUse(String portInUse) {
		this.portInUse = portInUse;
	}

	public String getPortAvailable() {
		return portAvailable;
	}

	public void setPortAvailable(String portAvailable) {
		this.portAvailable = portAvailable;
	}

}
