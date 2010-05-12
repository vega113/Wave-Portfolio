package com.aggfi.portfolio.wave.client.finance;

import com.google.gwt.core.client.JavaScriptObject;

public class Quote extends JavaScriptObject {
	
	protected Quote(){}

	  public final native void addListener(String callback) /*-{
	    //$wnd.quote.addListener(@com.aggfi.portfolio.wave.client.finance.FinanceFeature::quoteUpdateEvent(Lcom/google/gwt/core/client/JavaScriptObject;));
	    $wnd.quote.addListener(callback);
	  }-*/;
	  
	  
	  public final  native void getQuote(String symbol) /*-{
		  $wnd.quote.getQuote(symbol);
	  }-*/;
	  
	  public final  native void getQuotes(String[] symbols) /*-{
	  		$wnd.quote.getQuotes(symbols);
   	  }-*/;
}
