package com.aggfi.portfolio.wave.server;


import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortRow;
import com.google.gdata.client.finance.FinanceService;
import com.google.gdata.client.finance.FinanceUtilities;
import com.google.gdata.data.BaseEntry;
import com.google.gdata.data.BaseFeed;
import com.google.gdata.data.Link;
import com.google.gdata.data.extensions.Money;
import com.google.gdata.data.finance.PortfolioData;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PortfolioFeed;
import com.google.gdata.data.finance.PositionData;
import com.google.gdata.data.finance.PositionEntry;
import com.google.gdata.data.finance.PositionFeed;
import com.google.gdata.data.finance.TransactionData;
import com.google.gdata.data.finance.TransactionEntry;
import com.google.gdata.data.finance.TransactionFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demonstrates interactions with Google Finance Data API's portfolio feeds
 * using the Java client library:
 *
 * <ul>
 * <li>Retrieve all of the user's portfolios</li>
 * <li>Retrieve a single user portfolio</li>
 * <li>Create a new portfolio</li>
 * <li>Rename an existing portfolio</li>
 * <li>Delete an existing portfolio</li>
 * <li>Retrieve all of the positions belonging to a user portfolio</li>
 * <li>Retrieve a single position belonging to a user portfolio</li>
 * <li>Create a new position</li>
 * <li>Delete an existing position</li>
 * <li>Retrieve all of the transactions belonging to a user position</li>
 * <li>Retrieve a single transaction belonging to a user position</li>
 * <li>Create a new transaction</li>
 * <li>Update an existing transaction</li>
 * <li>Delete an existing transaction</li>
 * </ul>
 */ 
public class FinancePortfolioClient {

  

  // User's email and password:
  private static String userEmail = "vega113@gmail.com";


  // Base URL for GData requests
  // GData Server is supplied as command-line argument and
  // appended with /finance/feeds/default
  private static String server = "http://finance.google.com/finance/feeds/default/portfolios?returns=true&positions=true";
  private static String basePath = "/finance/feeds/";
  private static String baseUrl = server + basePath + "default";

  // Feed and Entry URI suffixes:
  private static final String PORTFOLIO_FEED_URL_SUFFIX = "/portfolios";
  private static final String POSITION_FEED_URL_SUFFIX = "/positions";
  private static String userPassword = "soetyr63937";
  private static final String TRANSACTION_FEED_URL_SUFFIX = "/transactions";

  /**
   * Portfolio ID, Ticker, and Transaction ID are components of the
   * heirarchical feed URL:
   * e.g. The Portfolio Feed
   * http://finance.google.com/finance/feeds/default/portfolios
   * e.g. A Portfolio Entry
   * http://finance.google.com/finance/feeds/default/portfolios/<pid>
   * e.g. A Position Feed
   * http://finance.google.com/finance/feeds/default/portfolios/<pid>
   *     /positions
   * e.g. A Position Entry
   * http://finance.google.com/finance/feeds/default/portfolios/<pid>
   *     /positions/<ticker>
   * e.g. A Transaction Feed
   * http://finance.google.com/finance/feeds/default/portfolios/<pid>
   *     /positions/<ticker>/transactions
   * e.g. A Transaction Entry
   * http://finance.google.com/finance/feeds/default/portfolios/<pid>
   *     /positions/<ticker>/transactions/<tid>
   * (where pid (portfolioIdProperty) and tid (transactionIdProperty) are
   * 1, 2, 3, ... and a ticker is of the form NASDAQ:GOOG)
   */
  private static String portfolioIdProperty;
  private static String tickerProperty;
  private static String transactionIdProperty;
  private static FinanceService service = new FinanceService("Aggfi");
  
  private static Logger logger = Logger.getLogger(FinancePortfolioClient.class.getName());


private static boolean isLogged = false;
  
  /**
   * Authenticates the user with the Google server after reading in the user
   * email and password.
   *
   * @param service authenticated client connection to a Finance GData service
   * @param userID Finance portfolio user ID (e.g. "bob@gmail.com")
   * @param userPassword Finance portfolio user password (e.g. "Bobs$tocks")
   * @return login success or failure
   */
  private static Boolean loginUser(String userID, String userPassword) {
    try {
      service.setUserCredentials(userID, userPassword);
      
    } catch (AuthenticationException e) {
      System.err.println("Invalid Credentials!");
      logger.log(Level.SEVERE, e.getDebugInfo());
      isLogged  = false;
      return false;
    }
    isLogged  = true;
    return true;
  }
  
  public static void main(String[] args) {
	  if (loginUser(userEmail, userPassword)) {
		logger.info("Logged in!");
	}
	  try {
		  OverviewPortHeader[] headers = retrievePortHeaders(service,"http://finance.google.com/finance/feeds/default/portfolios?returns=true&positions=true");
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ServiceException e) {
		e.printStackTrace();
	}
  }
  
  public static  OverviewPortHeader[] retrievePortHeaders()
	throws IOException, MalformedURLException, ServiceException {
	  if(!isLogged){
		  loginUser(userEmail, userPassword);
	  }
	  return retrievePortHeaders(service,server);
  }
  
  public static OverviewPortHeader[] retrievePortHeaders(FinanceService service, String feedUrl)
  	throws IOException, MalformedURLException, ServiceException {
	  
	  
	    PortfolioFeed portfolioFeed = service.getFeed(new URL(feedUrl), PortfolioFeed.class);
	    OverviewPortHeader[] headers = new OverviewPortHeader[portfolioFeed.getEntries().size()];
	    for (int i = 0; i < portfolioFeed.getEntries().size(); i++) {
	      PortfolioEntry portfolioEntry = portfolioFeed.getEntries().get(i);
	      headers[i] = buildOverviewPortHeader(portfolioEntry);
	    }
	  return headers;
  }
  
  /**
   * Prints detailed contents for a portfolio (i.e. a Portfolio Feed entry)
   *
   * @param portfolioEntry The portfolio entry of interest
   */
  private static OverviewPortHeader buildOverviewPortHeader(PortfolioEntry portfolioEntry) {
	OverviewPortHeader header = new OverviewPortHeader();
    PortfolioData portfolioData = portfolioEntry.getPortfolioData();
    double portTodayCostBasis = portfolioData.getCostBasis() != null ? portfolioData.getCostBasis().getMoney().get(0).getAmount() : 0;
    double cash = portfolioData.getMarketValue() != null ? portfolioData.getMarketValue().getMoney().get(0).getAmount() : 0;
    double portTodayGain  =  portfolioData.getCostBasis() != null ? portfolioData.getDaysGain().getMoney().get(0).getAmount() : 0;
    double changePercent = cash - portTodayGain != 0 ? portTodayGain/( cash - portTodayGain) : 0;
    header.init(portfolioEntry.getTitle().getPlainText(), portTodayGain,  changePercent, portTodayGain);
    return header;
  }
  
  
  
  
  public static  OverviewPortRow[] retrievePortOverviewEntries(String portName)
	throws IOException, MalformedURLException, ServiceException {
	  if(!isLogged){
		  loginUser(userEmail, userPassword);
	  }
	  return retrievePortOverviewEntries(service,server,portName);
}

public static OverviewPortRow[] retrievePortOverviewEntries(FinanceService service, String feedUrl, String portName)
	throws IOException, MalformedURLException, ServiceException {
	  
	  
	    PortfolioFeed portfolioFeed = service.getFeed(new URL(feedUrl), PortfolioFeed.class);
	    OverviewPortRow[] rows = new OverviewPortRow[portfolioFeed.getEntries().size()];
	    for (int i = 0; i < portfolioFeed.getEntries().size(); i++) {
	      PortfolioEntry portfolioEntry = portfolioFeed.getEntries().get(i);
	      rows[i] = buildPortOverviewEntries(portfolioEntry);
	    }
	  return rows;
}

private static OverviewPortRow buildPortOverviewEntries(PortfolioEntry portfolioEntry) {
	OverviewPortRow row = new OverviewPortRow();
  PortfolioData portfolioData = portfolioEntry.getPortfolioData();
  double portTodayCostBasis = portfolioData.getCostBasis() != null ? portfolioData.getCostBasis().getMoney().get(0).getAmount() : 0;
  double cash = portfolioData.getMarketValue() != null ? portfolioData.getMarketValue().getMoney().get(0).getAmount() : 0;
  double portTodayGain  =  portfolioData.getCostBasis() != null ? portfolioData.getDaysGain().getMoney().get(0).getAmount() : 0;
  double changePercent = cash - portTodayGain != 0 ? portTodayGain/( cash - portTodayGain) : 0;
//  row.init(portfolioEntry.getTitle().getPlainText(), portTodayGain,  changePercent, portTodayGain);
  return row;
}
  
}
