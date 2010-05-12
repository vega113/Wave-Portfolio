/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.aggfi.portfolio.wave.client.finance;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aggfi.portfolio.wave.client.portfolio.IOverviewRow;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortRow;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.gdata.client.finance.CostBasis;
import com.google.gwt.gdata.client.finance.DaysGain;
import com.google.gwt.gdata.client.finance.FinanceService;
import com.google.gwt.gdata.client.finance.MarketValue;
import com.google.gwt.gdata.client.finance.PortfolioData;
import com.google.gwt.gdata.client.finance.PortfolioEntry;
import com.google.gwt.gdata.client.finance.PortfolioEntryCallback;
import com.google.gwt.gdata.client.finance.PortfolioFeed;
import com.google.gwt.gdata.client.finance.PortfolioFeedCallback;
import com.google.gwt.gdata.client.finance.PositionData;
import com.google.gwt.gdata.client.finance.PositionEntry;
import com.google.gwt.gdata.client.finance.PositionFeed;
import com.google.gwt.gdata.client.finance.PositionFeedCallback;
import com.google.gwt.gdata.client.impl.CallErrorException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The following example demonstrates how to retrieve a list of a
 * user's portfolios.
 */
public class FinanceRetrievePortfolios extends AbstractWavePortfolio {

  static String logStr = new String();

  private static final String URI_FINANCE_PORTFOLIOS = "http://finance.google.com/finance/feeds/default/portfolios?returns=true&positions=true";
  private FinanceService service = null;
  private final static String scope = "http://finance.google.com/finance/feeds/";


  /**
   * Retrieve the portfolios feed using the Finance service and
   * the portfolios feed uri. In GData all get, insert, update
   * and delete methods always receive a callback defining
   * success and failure handlers.
   * Here, the failure handler displays an error message while the
   * success handler calls showData to display the portfolio entries.
 * @param portfoliosFeedUri The uri of the portfolios feed
   * 
   * @throws CallErrorException 
   */
  
  public void retrievePortfolioNames(final AsyncCallback<OverviewPortHeader[]> callback) {
	  
	  if (!GData.isLoaded(GDataSystemPackage.FINANCE)) {
	    	logStr += "Loading the GData Finance package...";
	      GData.loadGDataApi(GDATA_API_KEY, new Runnable() {
	        public void run() {
	        	  if (User.getStatus(scope) == AuthSubStatus.LOGGED_IN){
	        		  execretRievePortfolioNames(callback, URI_FINANCE_PORTFOLIOS);
	        	  }
	        }
	      }, GDataSystemPackage.FINANCE);
	    }
	  
//	  if (User.getStatus(scope) == AuthSubStatus.LOGGED_IN) {
//		 
//	  }
  }




private void execretRievePortfolioNames(
		final AsyncCallback<OverviewPortHeader[]> callback,
		String portfoliosFeedUri) {
	service = FinanceService.newInstance("Wave Portfolio 0.01");
//	service.setDeveloperKey("ABQIAAAAbvr6gQH1qmQkeIRFd4m2eRT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRY7y9tpXoGhmuQObT-SLOXXKGXlA");
//	service.setUserCredentials("vega113@gmail.com", "soetyr63937");
	service.getPortfolioFeed(portfoliosFeedUri, new PortfolioFeedCallback() {
		public void onSuccess(PortfolioFeed result) {
			OverviewPortHeader[] headers = null;
	        PortfolioEntry[] entries = result.getEntries();
	        if (entries.length > 0) {
	        	 headers = new OverviewPortHeader[entries.length];
	     	    for (int i = 0; i < entries.length; i++) {
	     	      PortfolioEntry portfolioEntry = entries[i];
	     	      headers[i] = buildOverviewPortHeader(portfolioEntry);
	     	    }
	        } 
	        callback.onSuccess(headers);
		}
		
		@Override
		public void onFailure(CallErrorException caught) {
			Log.error("Failure in execretRievePortfolioNames: ", caught);
			
		}
	});
}
  
	
	private OverviewPortHeader buildOverviewPortHeader(PortfolioEntry portfolioEntry) {
		OverviewPortHeader header = new OverviewPortHeader();
		
		String portfolioId = portfolioEntry.getId().getValue();
        JsArrayString match = regExpMatch("\\/(\\d+)$", portfolioId);
//        if (match.length() > 1) {
//          portfolioId = match.get(1);
//        }
//        getPositions(portfolioId,new PositionFeedCallback() {
//	      public void onFailure(CallErrorException caught) {
//	    	  caught.getMessage();
//	      }
//	      public void onSuccess(PositionFeed result) {
//	        PositionEntry[] entries = result.getEntries();
//	        if (entries.length > 0) {
//	        	PositionEntry portfolioEntry = entries[0];
//	        } 
//	      }
//	    });
	    PortfolioData portfolioData = portfolioEntry.getPortfolioData();
	    CostBasis cb = null;
	    MarketValue mv = null;
	    cb = portfolioData.getCostBasis();
	    double portTodayCostBasis = cb != null ? portfolioData.getCostBasis().getMoney()[0].getAmount() : 0;
	    mv = portfolioData.getMarketValue();
	    double cash = mv != null ? mv.getMoney()[0].getAmount() : 0;
	    cb = portfolioData.getCostBasis();
	    DaysGain dg = portfolioData.getDaysGain();
	    double portTodayGain  =  dg != null ? dg.getMoney()[0].getAmount() : 0;
	    double changePercent = cash - portTodayGain != 0 ? portTodayGain/( cash - portTodayGain) : 0;
	    String portName = portfolioEntry.getTitle().getText();
	    header.init(portName,portfolioId, portTodayGain,  changePercent, portTodayGain);
	    return header;
	  }
	
	
	/**
	   * Retrieve the positions feed for a given portfolio using the
	   * Finance service and the positions feed uri.
	   * The failure handler displays an error message while the
	   * success handler calls showData to display the position entries.
	   * 
	   * @param portfolioId The id of the portfolio for which to
	   * retrieve position data
	   */
	  private void getPositions(String portfolioId, final AsyncCallback<OverviewPortRow[]> callback) {
//	    
		  String positionsFeedUri = portfolioId+ "/positions?returns=true&transactions=true";
//	    String positionsFeedUri = portfolioId + "/positions";
	    service.getPositionFeed(positionsFeedUri, new PositionFeedCallback() {
			
			@Override
			public void onSuccess(PositionFeed result) {
				IOverviewRow[] rows = null;
				 PositionEntry[] entries = result.getEntries();
			        if (entries.length == 0) {
			        } else {
			        	
			        	rows = new IOverviewRow[entries.length];
			        	int counter = 0;
			        	for(PositionEntry posEntry : entries){
			        		 int stockNum = counter + 1;
//					  	       JsArrayString match = regExpMatch("\\/(\\d+)$", posEntry.getId().getValue());
//					  	        if (match.length() > 1) {
//					  	        	stockNum = match.get(1);
//					  	        }
				        	OverviewPortRow row = new OverviewPortRow();
				        	PositionData posData =  posEntry.getPositionData();
				        	
				        	double lastPrice = 0;
				        	//------- where from do i take volume and mktCap ? //TODO
				        	long mktCap = 0;
				        	long volume = 0;
				        	
				        	double open = 0;
				        	double high = 0;
				        	double low = 0;
				        	
				        	String name = posEntry.getTitle().getText();
				        	String symbol = posEntry.getSymbol().getSymbol();
				        	
				        	double changeAbsVal = 0;
				        	double changePercent =  0;
				        	double daysGain = 0;
				        	try {
				        		daysGain = posData.getDaysGain().getMoney()[0].getAmount();
				        	}catch (com.google.gwt.core.client.JavaScriptException e) {
				        		Log.debug ("Failure in getPositions: probably no money data for position. " +  e.getDescription());
							}
				        	
				        	double shares = 0;
				        	try {
				        		shares = posData.getShares();
				        	}catch (com.google.gwt.core.client.JavaScriptException e) {
				        		Log.debug ("Failure in getPositions: probably no shares. " +  e.getDescription());
							}
				        	
//				        	double costBasis = posData.getCostBasis().getMoney()[0].getAmount();
//				        	try {
//				        		shares = posData.getShares();
//				        	}catch (com.google.gwt.core.client.JavaScriptException e) {
//				        		Log.debug ("Failure in getPositions: probably no money data for position. " +  e.getDescription());
//							}
				        	
//				        	double marketValue = posData.getMarketValue().getMoney()[0].getAmount();
				        	if(shares != 0){
				        		changeAbsVal = (daysGain)/shares;
				        	}
				        	if (lastPrice != 0) {
								changePercent = (daysGain / lastPrice) / 100;
							}
				        	String stockId = posEntry.getId().getValue();
							row.initOverviewPortRow(lastPrice,shares,mktCap,volume,open,high,low,daysGain,name,symbol,stockNum,stockId);
				    		rows[counter] = row;
				    		counter++;
				    		OverviewPortRow[] rows2Update = new OverviewPortRow[1];
				    		rows2Update[0] = row;
				    		callback.onSuccess(rows2Update);
				    		
				    		//now make a request to retrieve market data

							retrieveMarketDataPerRow(callback, row);
			        	}
			        }
			}

			private void retrieveMarketDataPerRow(
					final AsyncCallback<OverviewPortRow[]> callback,
					final OverviewPortRow row) {
				String url;
				String urlFmt = "http://www.google.com/finance/info?client=ig&q=";
				 url = URL.encode(urlFmt + row.getSymbol());
				    // Send request to server and catch any errors.
				    RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

				    try {
				      Request request = builder.sendRequest(null, new RequestCallback() {
				        public void onError(Request request, Throwable exception) {
				        	Log.error("Couldn't retrieve JSON, request Failed");
				        	callback.onFailure(exception);
				        }

				        public void onResponseReceived(Request request, Response response) {
				        	if (200 == response.getStatusCode()) {
				        		//construct row and call the callback.onSuccess
					        	OverviewPortRow[] rows2Update = new OverviewPortRow[1];
					        	JsArray<GoogleStockDataJS> gStockDataJS =  asArrayOfStockData(com.google.gwt.core.client.JsonUtils.unsafeEval(response.getText()));
					        	
					        	GoogleStockDataJS stockData = gStockDataJS.get(0);

					        	double lastPrice = stockData.getLastPrice();
					        	
					        	row.initOverviewPortRow(lastPrice, -1, -1, -1, -1, -1, -1, -1, null, null, -1 , null);
					    		 rows2Update[1] = row;
					    		callback.onSuccess(rows2Update);
					          } else {
					        	  Log.error("Couldn't retrieve JSON (" + response.getStatusText()+ ")" + " Status is: " + response.getStatusCode());
					          }
				        }

				      });
				    } catch (RequestException e) {
				    	Log.error("Couldn't retrieve JSON");
				    }
			}
			
			@Override
			public void onFailure(CallErrorException caught) {
				Log.error("Failure in getPositions: ", caught);
				
			}
		});
	  }




	public void retrievePortfolioOverview(String portfolioId,
			AsyncCallback<OverviewPortRow[]> asyncCallback) {
		getPositions(portfolioId, asyncCallback);
	}
	
	
	public void retrStocksPrice(IOverviewRow row, final AsyncCallback<OverviewPortRow[]> callback){
		//		String urlFmt = "http://www.google.com/finance?q={0}&output=csv";
		String urlFmt = "http://www.google.com/finance/info?client=ig&q=";
		final List<HashMap<String,Object>> quotesList = new ArrayList<HashMap<String,Object>> ();
//		for(OverviewPortRow row : rowsList){}
	}
	
	private String extrctInfo(String responseStr, String strInfo) {
		int ind = responseStr.indexOf(strInfo) + strInfo.length();
		String tmp1 = responseStr.substring(ind,responseStr.length());
		String info = tmp1.split("\"")[0];
		return info;
	}
	
	private final native JsArray<GoogleStockDataJS> asArrayOfStockData(JavaScriptObject jso) /*-{
		return jso;
	}-*/;
  
	  
}