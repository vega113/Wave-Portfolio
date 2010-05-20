package com.aggfi.portfolio.wave.client.finance;

import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.gdata.client.finance.PositionData;
import com.google.gwt.gdata.client.finance.PositionEntry;
import com.google.gwt.gdata.client.finance.PositionFeed;
import com.google.gwt.gdata.client.finance.PositionFeedCallback;
import com.google.gwt.gdata.client.impl.CallErrorException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PositionFeedCallbackImpl implements PositionFeedCallback {
	
	/**
	 * {@link:OverviewAsyncCallbackImpl}}
	 */
	private AsyncCallback<OverviewPortRow> callback;
	
	/**
	 * 
	 * @param callback - {@link:OverviewAsyncCallbackImpl}}
	 */
	public void setCallback(AsyncCallback<OverviewPortRow> callback) {
		this.callback = callback;
	}

	public void setCashTitle(String cashTitle) {
		this.cashTitle = cashTitle;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	private String cashTitle;
	private String cash;

	@Override
	public void onSuccess(PositionFeed result) {
		try{
			OverviewPortRow[] rows = null;
			
			if (result == null || result.getEntries() == null || result.getEntries().length == 0) {
				callback.onSuccess(null);
			} else {
				PositionEntry[] entries = result.getEntries();
				rows = new OverviewPortRow[entries.length];
				int counter = 0;
				for(PositionEntry posEntry : entries){
					Log.trace("Looping positions entries: " +posEntry.getId().getValue());
					int stockNum = counter + 1; // +1 it beacause of table header - it's also a row
					OverviewPortRow row = new OverviewPortRow();
					PositionData posData =  posEntry.getPositionData();

					double lastPrice = 0;
					//------- where from do i take volume and mktCap ? //TODO
					long mktCap = 0;
					long volume = 0;

					double open = 0;
					double high = 0;
					double low = 0;

					String name = posEntry.getTitle().getText();
					String symbol = posEntry.getSymbol().getSymbol();

					double daysGain = 0;
					try {
						daysGain = posData.getDaysGain().getMoney()[0].getAmount();
					}catch (com.google.gwt.core.client.JavaScriptException e) {
						Log.warn ("Failure in getPositions: probably no money data for position. ", e);
					}

					double shares = 0;
					try {
						shares = posData.getShares();
					}catch (com.google.gwt.core.client.JavaScriptException e) {
						Log.warn ("Failure in getPositions: probably no shares. ",e);
					}
					String stockId = posEntry.getId().getValue();
					row.initOverviewPortRow(lastPrice,shares,mktCap,volume,open,high,low,daysGain,name,symbol,stockNum,stockId);
					rows[counter] = row;
					counter++;
					Log.debug("Before calling callback.onSuccess : " + row.getSymbol());
					callback.onSuccess(row);
				}
				//add row with cash
				int stockNum = counter + 1;
				Log.trace("Adding cash row num: " +stockNum);
				OverviewPortRow row = new OverviewPortRow();
				row.initCashOverviewPortRow(cash,cashTitle,stockNum);
				callback.onSuccess(row);
			}
		}catch(Exception e){
			Log.error("positionFeedCallbackImpl.onSuccess Failed! ", e);
		}
	}

	@Override
	public void onFailure(CallErrorException caught) {
		Log.error("Failure in getPositions: ", caught);
		
	}
}