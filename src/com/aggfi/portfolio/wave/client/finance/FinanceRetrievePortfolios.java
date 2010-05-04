///*
// * Copyright 2008 Google Inc.
// * 
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License. You may obtain a copy of
// * the License at
// * 
// * http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations under
// * the License.
// */
//
//package com.aggfi.portfolio.wave.client.finance;
//
//
//import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
//import com.google.gwt.accounts.client.AuthSubStatus;
//import com.google.gwt.accounts.client.User;
//import com.google.gwt.core.client.JsArrayString;
//import com.google.gwt.gdata.client.GData;
//import com.google.gwt.gdata.client.GDataSystemPackage;
//import com.google.gwt.gdata.client.finance.CostBasis;
//import com.google.gwt.gdata.client.finance.DaysGain;
//import com.google.gwt.gdata.client.finance.FinanceService;
//import com.google.gwt.gdata.client.finance.MarketValue;
//import com.google.gwt.gdata.client.finance.PortfolioData;
//import com.google.gwt.gdata.client.finance.PortfolioEntry;
//import com.google.gwt.gdata.client.finance.PortfolioFeed;
//import com.google.gwt.gdata.client.finance.PortfolioFeedCallback;
//import com.google.gwt.gdata.client.finance.PositionEntry;
//import com.google.gwt.gdata.client.finance.PositionFeed;
//import com.google.gwt.gdata.client.finance.PositionFeedCallback;
//import com.google.gwt.gdata.client.impl.CallErrorException;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//
///**
// * The following example demonstrates how to retrieve a list of a
// * user's portfolios.
// */
//public class FinanceRetrievePortfolios extends AbstractWavePortfolio {
//
//  static String logStr = new String();
//
//  private static final String URI_FINANCE_PORTFOLIOS = "http://finance.google.com/finance/feeds/default/portfolios?returns=true&positions=true";
//  private FinanceService service = null;
//  private final static String scope = "http://finance.google.com/finance/feeds/";
//
//
//  /**
//   * Retrieve the portfolios feed using the Finance service and
//   * the portfolios feed uri. In GData all get, insert, update
//   * and delete methods always receive a callback defining
//   * success and failure handlers.
//   * Here, the failure handler displays an error message while the
//   * success handler calls showData to display the portfolio entries.
//   * 
//   * @param portfoliosFeedUri The uri of the portfolios feed
// * @throws CallErrorException 
//   */
//  
//  public void retrievePortfolioNames(String userId, final AsyncCallback<OverviewPortHeader[]> callback) {
//	  
//	  if (!GData.isLoaded(GDataSystemPackage.FINANCE)) {
//	    	logStr += "Loading the GData Finance package...";
//	      GData.loadGDataApi(GDATA_API_KEY, new Runnable() {
//	        public void run() {
//	        	  if (User.getStatus(scope) == AuthSubStatus.LOGGED_IN){
//	        		  execRetrieve(callback, URI_FINANCE_PORTFOLIOS);
//	        	  }
//	        }
//	      }, GDataSystemPackage.FINANCE);
//	    }
//	  
////	  if (User.getStatus(scope) == AuthSubStatus.LOGGED_IN) {
////		 
////	  }
//  }
//
//
//
//
//private void execRetrieve(
//		final AsyncCallback<OverviewPortHeader[]> callback,
//		String portfoliosFeedUri) {
//	service = FinanceService.newInstance("Wave Portfolio 0.01");
////	service.setDeveloperKey("ABQIAAAAbvr6gQH1qmQkeIRFd4m2eRT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRY7y9tpXoGhmuQObT-SLOXXKGXlA");
////	service.setUserCredentials("vega113@gmail.com", "soetyr63937");
//	service.getPortfolioFeed(portfoliosFeedUri, new PortfolioFeedCallback() {
//		public void onSuccess(PortfolioFeed result) {
//			OverviewPortHeader[] headers = null;
//	        PortfolioEntry[] entries = result.getEntries();
//	        if (entries.length > 0) {
//	        	 headers = new OverviewPortHeader[entries.length];
//	     	    for (int i = 0; i < entries.length; i++) {
//	     	      PortfolioEntry portfolioEntry = entries[i];
//	     	      headers[i] = buildOverviewPortHeader(portfolioEntry);
//	     	    }
//	        } 
//	        callback.onSuccess(headers);
//		}
//		
//		@Override
//		public void onFailure(CallErrorException caught) {
//			// TODO Auto-generated method stub
//			
//		}
//	});
//}
//  
//	
//	private OverviewPortHeader buildOverviewPortHeader(PortfolioEntry portfolioEntry) {
//		OverviewPortHeader header = new OverviewPortHeader();
//		
//		String portfolioId = portfolioEntry.getId().getValue();
//        JsArrayString match = regExpMatch("\\/(\\d+)$", portfolioId);
////        if (match.length() > 1) {
////          portfolioId = match.get(1);
////        }
////        getPositions(portfolioId,new PositionFeedCallback() {
////	      public void onFailure(CallErrorException caught) {
////	    	  caught.getMessage();
////	      }
////	      public void onSuccess(PositionFeed result) {
////	        PositionEntry[] entries = result.getEntries();
////	        if (entries.length > 0) {
////	        	PositionEntry portfolioEntry = entries[0];
////	        } 
////	      }
////	    });
//	    PortfolioData portfolioData = portfolioEntry.getPortfolioData();
//	    CostBasis cb = null;
//	    MarketValue mv = null;
//	    cb = portfolioData.getCostBasis();
//	    double portTodayCostBasis = cb != null ? portfolioData.getCostBasis().getMoney()[0].getAmount() : 0;
//	    mv = portfolioData.getMarketValue();
//	    double cash = mv != null ? mv.getMoney()[0].getAmount() : 0;
//	    cb = portfolioData.getCostBasis();
//	    DaysGain dg = portfolioData.getDaysGain();
//	    double portTodayGain  =  dg != null ? dg.getMoney()[0].getAmount() : 0;
//	    double changePercent = cash - portTodayGain != 0 ? portTodayGain/( cash - portTodayGain) : 0;
//	    String portName = portfolioEntry.getTitle().getText();
//	    header.init(portName, portTodayGain,  changePercent, portTodayGain);
//	    return header;
//	  }
//	
//	
//	/**
//	   * Retrieve the positions feed for a given portfolio using the
//	   * Finance service and the positions feed uri.
//	   * The failure handler displays an error message while the
//	   * success handler calls showData to display the position entries.
//	   * 
//	   * @param portfolioId The id of the portfolio for which to
//	   * retrieve position data
//	   */
//	  private void getPositions(String portfolioId, PositionFeedCallback callback) {
//	    String positionsFeedUri = "http://finance.google.com/finance/feeds/default/portfolios/" + portfolioId + "/positions";
//	    service.getPositionFeed(positionsFeedUri, callback);
//	  }
//  
//	  
//}