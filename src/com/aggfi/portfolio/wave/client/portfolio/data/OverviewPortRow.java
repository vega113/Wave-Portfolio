package com.aggfi.portfolio.wave.client.portfolio.data;


import com.aggfi.portfolio.wave.client.finance.feature.Data;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OverviewPortRow extends AbstractPortRow{
	
	



	@Override
	public String toString() {
		return "OverviewPortRow [cash=" + cash + ", changeAbsVal="
				+ changeAbsVal + ", changePercent=" + changePercent
				+ ", currencyCode=" + currencyCode + ", daysGain=" + daysGain
				+ ", high=" + high + ", isCashRow=" + isCashRow
				+ ", lastPrice=" + lastPrice + ", low=" + low + ", mktCap="
				+ mktCap + ", open=" + open + ", shares=" + shares
				+ ", volume=" + volume + "]";
	}

	private boolean isCashRow = false;
	
	private double lastPrice;
	private double changeAbsVal;
	private double changePercent;
	private long mktCap;
	private double volume;
	private double open;
	private double high;
	private double low;
	private double daysGain;
	private double shares;
	
	private String currencyCode;

	private String cash;
	
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
		
		calcInfo();
	}

	private void calcInfo() {
		if(lastPrice != 0 && lastPrice > -1){
			this.changePercent = (daysGain / lastPrice) / 100;
		}
		if(this.shares != 0  && this.shares > -1){
			this.changeAbsVal = (daysGain)/this.shares;
		}
	}
	
	public void initCashOverviewPortRow(String cashStrVal, String cashTitle, int rowNum ){
		isCashRow = true;
		this.cash = cashStrVal;
		this.symbol = cashTitle;
		this.rowNum = rowNum;
		
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
		return this.mktCap;
	}
	public void setMktCap(long mktCap) {
		this.mktCap = mktCap;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getVolume()
	 */
	public double getVolume() {
		return this.volume;
	}
	public void setVolume(double volume) {
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
		return this.high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getLow()
	 */
	public double getLow() {
		return this.low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getDaysGain()
	 */
	public double getDaysGain() {
		return this.daysGain;
	}
	public void setDaysGain(double daysGain) {
		this.daysGain = daysGain;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#getCurrencyCode()
	 */
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IOverviewRow#isCashRow()
	 */
	public boolean isCashRow() {
		return this.isCashRow;
	}

	public String getCash() {
		return cash;
	}

	@Override
	public AsyncCallback<Data> getCallback() {
		return new AsyncCallback<Data>() {

			@Override
			public void onSuccess(Data result) {
				if(result.getSymbol().equals(getSymbol())){
					setLastPrice(result.getLast());
					setHigh(result.getHigh());
					setLow(result.getLow());
					setOpen(result.getOpen());
					NumberFormat fmt = NumberFormat.getDecimalFormat();
					double vol = fmt.parse(result.getVolume());
					setVolume( vol);
					Log.trace("symbol: " + result.getSymbol() + ", last: " + result.getLast() + ", " + ", open: " + result.getOpen()+  ", high: " + result.getHigh() + ", " +  "low: " + result.getLow() + ", volume: " + vol + ", ext volume: " + result.getExtVolume());
					calcInfo();
				}
			}

			@Override
			public void onFailure(Throwable caught) {

			}
		};
	}
}
