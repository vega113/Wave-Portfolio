package com.aggfi.portfolio.wave.client.finance.feature;

import java.util.HashMap;

import com.aggfi.portfolio.wave.client.finance.QuoteUpdateEventHandlerImpl;

public class PoolQuoteHandler {
	private static PoolQuoteHandler instance = null;
	
	private HashMap<String,QuoteUpdateEventHandlerImpl> qoutePool = new HashMap<String,QuoteUpdateEventHandlerImpl> ();
	
	private PoolQuoteHandler(){
	}
	
	public static PoolQuoteHandler instance(){
		if(instance == null){
			instance = new PoolQuoteHandler();
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
	public  QuoteUpdateEventHandlerImpl getQuoteUpdateEventHandler(String portfolioId, FinanceFeature financeFearure){
		QuoteUpdateEventHandlerImpl handler = qoutePool.get(portfolioId);
		if(handler == null){
			handler = new QuoteUpdateEventHandlerImpl();
			financeFearure.addQuoteUpdateEventHandler(handler);
			qoutePool.put(portfolioId, handler);
		}
		return handler;
	}
}
