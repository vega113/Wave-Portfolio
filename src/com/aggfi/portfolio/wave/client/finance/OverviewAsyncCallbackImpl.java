package com.aggfi.portfolio.wave.client.finance;

import java.util.ArrayList;
import java.util.List;

import com.aggfi.portfolio.wave.client.finance.feature.Data;
import com.aggfi.portfolio.wave.client.finance.feature.FinanceFeature;
import com.aggfi.portfolio.wave.client.finance.feature.PoolQuoteHandler;
import com.aggfi.portfolio.wave.client.portfolio.DisclosureWidget;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * It's onSuccess method invoked after  retrieval of portfolio positions; Then real time market data is also retrieved
 * via google.finance.quote.getQuotes()
 * ahould be initialized before used - set the dsWidget - i.e. the widget that contains portfolio data
 * @author vega
 *
 */
public class OverviewAsyncCallbackImpl implements AsyncCallback<OverviewPortRow>{
	
	private DisclosureWidget dsWidget;
	private FinanceFeature financeFearure;

	public void setFinanceFearure(FinanceFeature financeFearure) {
		this.financeFearure = financeFearure;
	}

	public void setDsWidget(DisclosureWidget dsWidget) {
		this.dsWidget = dsWidget;
	}

	@Override
	public void onSuccess(final OverviewPortRow row) {
		List<String> symbolsList = new ArrayList<String>();
		// now create list of symbols for this portfolio and also collect callbacks for each portfolio row which will be invkoed
		//when marked data will arrive
		Log.debug("Inside OverviewAsyncCallbackImpl.onSuccess - creating list of stock symbols and callbacks from row: " + row.getSymbol());
		if(row == null){ // no entries in portfolio
			dsWidget.portPopulate(null);
		}
		if(!row.isCashRow()){
			symbolsList.add(row.getSymbol());
			QuoteUpdateEventHandlerImpl quoteUpdateEventHandler = 
				PoolQuoteHandler.instance().getQuoteUpdateEventHandler(dsWidget.getPortId(),financeFearure);
			quoteUpdateEventHandler.setDsWidget(dsWidget);
			quoteUpdateEventHandler.setResult(row);
			financeFearure.getQuoteInstance().getQuotes(symbolsList.toArray(new String[1]));
		}else{
			OverviewPortRow[] rows = new OverviewPortRow[1];
			rows[0] = row;
			dsWidget.portPopulate(rows );
		}
	}

	@Override
	public void onFailure(Throwable caught) {
		Log.error("Error in DsWidget callback! ", caught);
	}
};
