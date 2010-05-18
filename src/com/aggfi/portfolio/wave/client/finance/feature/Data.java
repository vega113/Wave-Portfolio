package com.aggfi.portfolio.wave.client.finance.feature;

import com.google.gwt.core.client.JsArrayString;

public class Data extends JsArrayString{
	// Overlay types always have protected, zero argument constructors.
	  protected Data() {}                                              

	  // JSNI methods to get stock data.
	  public final native String getSymbol() /*-{ return this[google.finance.SYMBOL]; }-*/;
	  public final native String getExchange() /*-{ return this[google.finance.EXCHANGE]; }-*/;
	  public final native double getLast() /*-{ return this[google.finance.LAST]; }-*/;
	  public final native double getOpen() /*-{ return this[google.finance.OPEN]; }-*/;
	  public final native double getHigh() /*-{ return this[google.finance.HIGH]; }-*/;
	  public final native double getLow() /*-{ return this[google.finance.LOW]; }-*/;
	  public final native String getVolume() /*-{ return this[google.finance.VOLUME]; }-*/;
	  public final native String getLastTradeTime() /*-{ return this[google.finance.LAST_TRADE_TIME]; }-*/;
	  public final native double getChange() /*-{ return this[google.finance.CHANGE]; }-*/;
	  public final native double getChangePercentage() /*-{ return this[google.finance.CHANGE_PCT]; }-*/;
	  public final native double getExtVolume() /*-{ return this[google.finance.EXT_VOLUME]; }-*/;
//	  public final native double getExtLastPrice() /*-{ return this.el; }-*/;
//	  public final native String getExtLastTime() /*-{ return this.elt; }-*/;
//	  public final native double getExtChange() /*-{ return this.ec; }-*/;
//	  public final native double getExtChangePercentage() /*-{ return this.ecp; }-*/; 
}
