package com.aggfi.portfolio.wave.client.finance;

import com.google.gwt.core.client.JsArrayString;

public class AbstractWavePortfolio {
	protected static String GDATA_API_KEY = "ABQIAAAAbvr6gQH1qmQkeIRFd4m2eRQGgUfFXBTwJSrdXmAUVcxdpVrjSxSCpKc56TMohMx09fePLmfMFkr2VA";
	/**
	   * Expose the JavaScript regular expression parsing to GWT.
	   * 
	   * @param regEx The regular expression to use
	   * @param target The text string to parse
	   * @return A JavaScript string array containing any matches
	   */
	  protected native static JsArrayString regExpMatch(String regEx, String target) /*-{
	    var re = new RegExp();
	    return re.compile(regEx).exec(target);
	  }-*/;
}
