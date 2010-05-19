package com.aggfi.portfolio.wave.client.portfolio.data;

import java.io.Serializable;


public class OverviewPortHeader implements Serializable {
	public double getCash() {
		return cash;
	}
	public double getMktValue() {
		return mktValue;
	}

	private static final int MAX_PORT_NAME_LENGTH = 20;
	private double cash;
	private double mktValue;
	public String getPortName() {
		return portName;
	}
	public double getChangeAbsVal() {
		return changeAbsVal;
	}
	public double getChangePercent() {
		return changePercent;
	}
	public void init(String portName, String portId, double changeAbsVal,
			double changePercent, double cash, double mktValue) {
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
		
		this.portName = name.toString();
		this.changeAbsVal = changeAbsVal;
		this.changePercent = changePercent;
		this.portId = portId;
		this.cash = cash;
		this.mktValue = mktValue;
	}
	
	public String getPortId() {
		return portId;
	}

	private String portName;
	private String portId;
	private double changeAbsVal;
	private double changePercent;

}
