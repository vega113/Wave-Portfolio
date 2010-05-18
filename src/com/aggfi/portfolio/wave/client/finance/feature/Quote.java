package com.aggfi.portfolio.wave.client.finance.feature;

import com.google.gwt.core.client.JavaScriptObject;

public class Quote extends JavaScriptObject {
	
	protected Quote(){}

	  public final native void addListener(String callback) /*-{
	    this.addListener(callback);
	  }-*/;
	  
	  public final native void addListener() /*-{
	    this.addListener(@com.aggfi.portfolio.wave.client.finance.feature.FinanceFeature::quoteUpdateEvent(Lcom/google/gwt/core/client/JavaScriptObject;));
	  }-*/;
	  
	  
	  public final  native void getQuotes(String symbol) /*-{
		  this.getQuotes(symbol);
	  }-*/;
	  
	  public final  native void getQuotes(String[] symbols) /*-{
	  		this.getQuotes(symbols);
   	  }-*/;
}
