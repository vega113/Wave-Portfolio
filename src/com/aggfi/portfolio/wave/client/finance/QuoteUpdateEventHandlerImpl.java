package com.aggfi.portfolio.wave.client.finance;

import java.util.HashMap;
import java.util.Map;

import com.aggfi.portfolio.wave.client.finance.feature.Data;
import com.aggfi.portfolio.wave.client.finance.feature.QuoteUpdateEvent;
import com.aggfi.portfolio.wave.client.finance.feature.QuoteUpdateEventHandler;
import com.aggfi.portfolio.wave.client.portfolio.DisclosureWidget;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This implementation of QuoteUpdateEventHandler will invoke all registered callback and pass them the market data for some quote
 * @author vega
 *
 */
public class QuoteUpdateEventHandlerImpl implements QuoteUpdateEventHandler
{
	
	private Map<String,OverviewPortRow> resultMap = new HashMap<String,OverviewPortRow>();
	/**
	 * These callbacks - are related to rows in the dsWidget.
	 * Each calback is implemented as method inside OverviewPortRow inner class, so it has access to the 
	 * OverviewPortRow members. Each callback will check if the quote market data is relevant - by symbol comparison,
	 * if so - it will update the row with market data
	 */
	
	/**
	 * {@link OverviewPortRow.callback}
	 * The callback updates it's outer row class field members with Quote data
	 */
	private AsyncCallback<Data> callback;
	
	public void setResult(OverviewPortRow result) {
		resultMap.put(result.getSymbol(), result);
		Log.debug("Put stock info to resultMap: " + result.getSymbol());
	}


	public void setDsWidget(DisclosureWidget dsWidget) {
		this.dsWidget = dsWidget;
	}

	private DisclosureWidget dsWidget;

	@Override
	public void onUpdate(QuoteUpdateEvent event) {
		try {
			
			Log.debug("Inside QuoteUpdateEventHandlerImpl.onUpdate: " + extrctSymbol(event.getData().getSymbol()));
			OverviewPortRow[] rows = new OverviewPortRow[1];
			rows[0] = resultMap.get(extrctSymbol(event.getData().getSymbol()));
			if(rows[0] != null){
				callback = rows[0].getCallback();
				callback.onSuccess(event.getData());
				dsWidget.portPopulate(rows );
			}else{
				Log.warn("problem inside QuoteUpdateEventHandlerImpl : cannot find row for: " + extrctSymbol(event.getData().getSymbol()) + "; " + event.getData().toStringMy());
			}
		} catch (Exception e) {
			Log.error("in callback", e);
		}
	}
	private String extrctSymbol(String str){
		String symbol = str.split(":")[2];
		return symbol;
	}
};
