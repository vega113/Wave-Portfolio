package com.aggfi.portfolio.wave.client.finance;

import com.google.gwt.core.client.JsArrayString;

public class Data extends JsArrayString{
	// Overlay types always have protected, zero argument constructors.
	  protected Data() {}                                              

	  // JSNI methods to get stock data.
	  public final native String getExchange() /*-{ return this[$wnd.EXCHANGE]; }-*/;
	  public final native double getLast() /*-{ return this[$wnd.LAST]; }-*/;
	  public final native double getOpen() /*-{ return this[$wnd.OPEN]; }-*/;
	  public final native double getHigh() /*-{ return this[$wnd.HIGH]; }-*/;
	  public final native double getLow() /*-{ return this[$wnd.LOW]; }-*/;
	  public final native double getVolume() /*-{ return this[$wnd.VOLUME]; }-*/;
	  public final native String getLastTradeTime() /*-{ return this[$wnd.LAST_TRADE_TIME]; }-*/;
	  public final native double getChange() /*-{ return this[$wnd.CHANGE]; }-*/;
	  public final native double getChangePercentage() /*-{ return this[$wnd.CHANGE_PCT]; }-*/;
//	  public final native double getExtLastPrice() /*-{ return this.el; }-*/;
//	  public final native String getExtLastTime() /*-{ return this.elt; }-*/;
//	  public final native double getExtChange() /*-{ return this.ec; }-*/;
//	  public final native double getExtChangePercentage() /*-{ return this.ecp; }-*/; 
	  
	  
}
