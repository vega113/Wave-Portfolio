package com.aggfi.portfolio.wave.client.finance;


import java.util.HashMap;

import com.aggfi.portfolio.wave.client.finance.feature.FinanceFeature;
import com.aggfi.portfolio.wave.client.portfolio.DisclosureWidget;


public class PoolFinanceCallbacks {
	private static PoolFinanceCallbacks instance = null;
	
	private HashMap<String,PositionFeedCallbackImpl> positionsPool = new HashMap<String,PositionFeedCallbackImpl> ();
	private HashMap<String,OverviewAsyncCallbackImpl> overviewsPool = new HashMap<String,OverviewAsyncCallbackImpl> ();
	
	private PoolFinanceCallbacks(){
	}
	
	public static PoolFinanceCallbacks instance(){
		if(instance == null){
			instance = new PoolFinanceCallbacks();
		}
		return instance;
	}
	
	/**
	 * Creates or returns QuoteUpdateEventHandler - per porfolio. Wherever market data arrives frin google finance - it's onUpdate
	 * method will be invoked. If there's no cached handler for portfolio - it will be created and registered to listen on quoteUpdateEvents
	 * @param portfolioId
	 * @param financeFearure
	 * @return
	 */
	public  PositionFeedCallbackImpl getPositionFeedCallbackImpl(String positionId){
		PositionFeedCallbackImpl handler = positionsPool.get(positionId);
		if(handler == null){
			handler = new PositionFeedCallbackImpl();
			positionsPool.put(positionId, handler);
		}
		return handler;
	}
	
	public OverviewAsyncCallbackImpl getOverviewAsyncCallbackImpll(
			DisclosureWidget dsWidget, FinanceFeature financeFearure) {
		String id = dsWidget.getPortId();
		OverviewAsyncCallbackImpl handler = overviewsPool.get(id);
		if(handler == null){
			handler = new OverviewAsyncCallbackImpl();
			overviewsPool.put(id, handler);
			handler.setDsWidget(dsWidget);
			handler.setFinanceFearure(financeFearure);
		}
		return handler;
	}
}
