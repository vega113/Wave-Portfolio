package com.aggfi.portfolio.wave.client.finance;

import java.util.HashMap;
import java.util.Map;

import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortHeader;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.gdata.client.finance.PortfolioEntry;
import com.google.gwt.gdata.client.finance.PortfolioFeed;
import com.google.gwt.gdata.client.finance.PortfolioFeedCallback;
import com.google.gwt.gdata.client.impl.CallErrorException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PortfolioFeedCallbackImpl implements PortfolioFeedCallback{

	public void setCallback(AsyncCallback<OverviewPortHeader[]> callback) {
		this.callback = callback;
	}

	private AsyncCallback<OverviewPortHeader[]> callback;
	Map<String,OverviewPortHeader> headerPortMap = new HashMap<String,OverviewPortHeader>();
	
	public void onSuccess(PortfolioFeed result) {
		OverviewPortHeader[] headers = null;
		PortfolioEntry[] entries = result.getEntries();
		Log.trace("entries length: " + entries.length);
		if (entries.length > 0) {
			try {
				headers = new OverviewPortHeader[entries.length];
				for (int i = 0; i < entries.length; i++) {
					PortfolioEntry portfolioEntry = entries[i];
					OverviewPortHeader ph = headerPortMap.get(portfolioEntry.getId().getValue());
					if(ph == null){
						ph = PortfolioTools.buildOverviewPortHeader(portfolioEntry);
					}else{
						ph = PortfolioTools.updateOverviewPortHeader(portfolioEntry,ph);
					}
					headers[i] = ph;
				}

			} catch (Exception e) {
				Log.error("Failure in execretRievePortfolioNames: ", e);
			}
		}
		callback.onSuccess(headers);
	}

	@Override
	public void onFailure(CallErrorException caught) {
		Log.error("Failure in execretRievePortfolioNames: ", caught);

	}

}
