package com.aggfi.portfolio.wave.client.portfolio;

import java.io.Serializable;


public class OverviewPortHeader implements Serializable {
	private static final int MAX_PORT_NAME_LENGTH = 20;
	public String getPortName() {
		return portName;
	}
	public double getChangeAbsVal() {
		return changeAbsVal;
	}
	public double getChangePercent() {
		return changePercent;
	}
	public void init(String portName, double changeAbsVal,
			double changePercent, double gain) {
		StringBuffer name = null;
		if(portName.length() > MAX_PORT_NAME_LENGTH){
			name = new StringBuffer(portName.substring(0, MAX_PORT_NAME_LENGTH -1));
		}else{
			name = new StringBuffer(portName);
			int nameLength = name.length();
			for(int i = 0; i< MAX_PORT_NAME_LENGTH - nameLength; i++){
				name.append(" ");
			}
		}
		
		this.portName = "| " + name.toString() + " |";
		this.changeAbsVal = changeAbsVal;
		this.changePercent = changePercent;
		this.gain = gain;
	}
	
	public double getGain() {
		return gain;
	}

	String portName;
	double changeAbsVal;
	double changePercent;
	double gain;
	

}
