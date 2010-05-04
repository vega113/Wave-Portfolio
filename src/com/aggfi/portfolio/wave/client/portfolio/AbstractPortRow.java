package com.aggfi.portfolio.wave.client.portfolio;

import java.io.Serializable;

public class AbstractPortRow implements Serializable {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	protected String name;
	protected String symbol;

}
