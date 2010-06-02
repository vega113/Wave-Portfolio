package com.aggfi.portfolio.wave.client.portfolio.data;


import com.aggfi.portfolio.wave.client.finance.feature.Data;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class OverviewPortRow extends AbstractPortRow{
	
	




	@Override
	public String toString() {
		return "OverviewPortRow [cash=" + cash + ", changeAbsVal="
				+ changeAbsVal + ", changePercent=" + changePercent
				+ ", currencyCode=" + currencyCode + ", daysGain=" + daysGain
				+ ", exchange=" + exchange + ", high=" + high + ", isCashRow="
				+ isCashRow + ", lastPrice=" + lastPrice + ", lastTradeTime="
				+ lastTradeTime + ", low=" + low + ", mktCap=" + mktCap
				+ ", open=" + open + ", preLastPrice=" + preLastPrice
				+ ", shares=" + shares + ", timer=" + timer + ", volume="
				+ volume + ", name=" + name + ", rowNum=" + rowNum
				+ ", symbol=" + symbol + "]";
	}

	private boolean isCashRow = false;
	
	private double lastPrice;
	/**
	 * change since close
	 */
	private double changeAbsVal;
	private double changePercent;
	private long mktCap;
	private double volume;
	private double open;
	private double high;
	private double low;
	private double daysGain;
	private double shares;
	private double preLastPrice;
	private String exchange;
	
	public String getExchange() {
		return exchange;
	}

	private String currencyCode;

	private String cash;

	private String lastTradeTime = "";

	public void initOverviewPortRow(double lastPrice, double shares, long mktCap,
			long volume, double open, double high, double low, double daysGain,
			String name, String symbol, String exchange, int rowNum, String stockId) {
		super.initOverviewPortRow(name, symbol, rowNum, stockId);
		
		if(lastPrice > 0){
			this.lastPrice = lastPrice;
		}
		this.changeAbsVal = 0;
		this.changePercent = 0;
		this.mktCap = mktCap;
		this.volume = volume;
		this.open = open;
		this.high = high;
		this.low = low;
		this.daysGain = daysGain;
		this.shares = shares;
		this.preLastPrice = 0;
		this.exchange = exchange;
		
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
	
	private void updateDaysGain(){
		daysGain = changeAbsVal * (shares != 0 ? shares : 1);
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
//		if(lastPrice > 0){
//			this.lastPrice = lastPrice;
//		}
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
	
	public double getPreLastPrice() {
		return preLastPrice;
	}

	public void setPreLastPrice(double preLastPrice) {
		if(preLastPrice > 0){
			this.preLastPrice = preLastPrice;
		}
	}
	
	public String getLastTradeTime() {
		return lastTradeTime;
	}

	private void setLastTradeTime(String lastTradeTime) {
		this.lastTradeTime = lastTradeTime == null ? "" : lastTradeTime;
		
	}

	AsyncCallback<Data> callback = new AsyncCallback<Data>() {

		@Override
		public void onSuccess(Data result) {
			if(extrctSymbol(result.getSymbol()).equals(getSymbol())){
				setPreLastPrice(getLastPrice());
				try {
					setLastPrice(result.getLast());
					Log.debug("Last : " + result.getLast() + ", " + extrctSymbol(result.getSymbol()).equals(getSymbol()));
				} catch (Exception e) {
					Log.error("",e);
				}
				setLastTradeTime(result.getLastTradeTime());
				setHigh(result.getHigh());
				setLow(result.getLow());
				setOpen(result.getOpen());
				setChangeAbsVal(result.getChange());
				setChangePercent(result.getChangePercentage() / 100);
				setOpen(result.getOpen());
				NumberFormat fmt = NumberFormat.getDecimalFormat();
				double vol = fmt.parse(result.getVolume());
				setVolume( vol);
				updateDaysGain();
				Log.debug(toString());
			}
		}

		@Override
		public void onFailure(Throwable caught) {

		}
		
		private String extrctSymbol(String str){
			String symbol = str.split(":")[2];
			return symbol;
		}
	};
	
	@Override
	public AsyncCallback<Data> getCallback() {
		return callback;
	}


	OverviewTimer timer = new OverviewTimer();
	public OverviewTimer getTimer() {
		return timer;
	}
	public class OverviewTimer extends Timer {
		FlexTable f = null;
		public void setF(FlexTable f) {
			this.f = f;
		}
		Widget widget = null;
		public void setLast(Widget widget) {
			this.widget = widget;
		}
		@Override
		public void run() {
			f.setWidget(getRowNum(), 1, widget);

		}
	}

}
