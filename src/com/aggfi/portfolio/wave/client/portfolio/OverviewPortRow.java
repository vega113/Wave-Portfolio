package com.aggfi.portfolio.wave.client.portfolio;

public class OverviewPortRow extends AbstractPortRow implements IOverviewRow {
	
	



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
	private double shares;
	
	private String currencyCode;
	
	public void initOverviewPortRow(double lastPrice, double shares, long mktCap,
			long volume, double open, double high, double low, double daysGain,
			String name, String symbol, int rowNum, String stockId) {
		super.initOverviewPortRow(name, symbol, rowNum, stockId);
		
		this.lastPrice = lastPrice;
		this.changeAbsVal = 0;
		this.changePercent = 0;
		this.mktCap = mktCap;
		this.volume = volume;
		this.open = open;
		this.high = high;
		this.low = low;
		this.daysGain = daysGain;
		this.shares = shares;
		
		if(lastPrice != 0 && lastPrice > -1){
			this.changePercent = (daysGain / lastPrice) / 100;
		}
		if(this.shares != 0  && this.shares > -1){
			this.changeAbsVal = (daysGain)/this.shares;
		}
	}
	
	public void initCashOverviewPortRow(double lastPrice, String cashStr, String currencyCode){
		isCashRow = true;
		this.lastPrice = lastPrice;
		this.currencyCode = currencyCode;
		this.name = cashStr;
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getLastPrice()
	 */
	public double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getChangeAbsVal()
	 */
	public double getChangeAbsVal() {
		return changeAbsVal;
	}
	public void setChangeAbsVal(double changeAbsVal) {
		this.changeAbsVal = changeAbsVal;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getChangePercent()
	 */
	public double getChangePercent() {
		return changePercent;
	}
	public void setChangePercent(double changePercent) {
		this.changePercent = changePercent;
	}



	



	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getMktCap()
	 */
	public long getMktCap() {
		return mktCap;
	}
	public void setMktCap(long mktCap) {
		this.mktCap = mktCap;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getVolume()
	 */
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getOpen()
	 */
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getHigh()
	 */
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getLow()
	 */
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getDaysGain()
	 */
	public double getDaysGain() {
		return daysGain;
	}
	public void setDaysGain(double daysGain) {
		this.daysGain = daysGain;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getCurrencyCode()
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#isCashRow()
	 */
	public boolean isCashRow() {
		return isCashRow;
	}
}
