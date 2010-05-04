package com.aggfi.portfolio.wave.client.portfolio;

public class OverviewPortRow extends AbstractPortRow {
	
	

	private boolean isCashRow = false;
	
	private double lastPrice;
	private double changeAbsVal;
	private double changePercent;
	private long mktCap;
	private long volume;
	private double open;
	private double high;
	private double low;
	private double daysGain;
	
	private String currencyCode;
	
	public void initOverviewPortRow(double lastPrice, double change, double changePercent, long mktCap,
			long volume, double open, double high, double low, double daysGain,
			String name, String symbol) {
		
		this.lastPrice = lastPrice;
		this.changeAbsVal = change;
		this.changePercent = changePercent;
		this.mktCap = mktCap;
		this.volume = volume;
		this.open = open;
		this.high = high;
		this.low = low;
		this.daysGain = daysGain;
		this.name = name;
		this.symbol = symbol;
	}
	
	public void initCashOverviewPortRow(double lastPrice, String cashStr, String currencyCode){
		isCashRow = true;
		this.lastPrice = lastPrice;
		this.currencyCode = currencyCode;
		this.name = cashStr;
		
	}
	
	
	
	public double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public double getChangeAbsVal() {
		return changeAbsVal;
	}
	public void setChangeAbsVal(double changeAbsVal) {
		this.changeAbsVal = changeAbsVal;
	}
	public double getChangePercent() {
		return changePercent;
	}
	public void setChangePercent(double changePercent) {
		this.changePercent = changePercent;
	}



	



	public long getMktCap() {
		return mktCap;
	}
	public void setMktCap(long mktCap) {
		this.mktCap = mktCap;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getDaysGain() {
		return daysGain;
	}
	public void setDaysGain(double daysGain) {
		this.daysGain = daysGain;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}

	public boolean isCashRow() {
		return isCashRow;
	}
}
