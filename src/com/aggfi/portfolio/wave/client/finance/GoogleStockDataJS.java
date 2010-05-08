package com.aggfi.portfolio.wave.client.finance;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleStockDataJS extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected GoogleStockDataJS() {}                                              

	  // JSNI methods to get stock data.
	  public final native double getId() /*-{ return this.id; }-*/; 
	  public final native String getTicker() /*-{ return this.t; }-*/;
	  public final native String getExchange() /*-{ return this.e; }-*/;
	  public final native double getLastPrice() /*-{ return this.l; }-*/;
	  public final native double getCurLastPrice() /*-{ return this.l_cur; }-*/;
	  public final native String getLastTradeTime() /*-{ return this.ltt; }-*/;
	  public final native String getLastTime() /*-{ return this.lt; }-*/;
	  public final native double getChange() /*-{ return this.c; }-*/;
	  public final native double getChangePercentage() /*-{ return this.cp; }-*/;
	  public final native String getCCol() /*-{ return this.ccol; }-*/;
	  public final native double getExtLastPrice() /*-{ return this.el; }-*/;
	  public final native double getExtCurLastPrice() /*-{ return this.el_cur; }-*/;
	  public final native String getExtLastTime() /*-{ return this.elt; }-*/;
	  public final native double getExtChange() /*-{ return this.ec; }-*/;
	  public final native double getExtChangePercentage() /*-{ return this.ecp; }-*/;
	  public final native String getExtCCol() /*-{ return this.eccol; }-*/;
	  public final native String getDiv() /*-{ return this.div; }-*/;
	  public final native double getYield() /*-{ return this.yld; }-*/;
}
