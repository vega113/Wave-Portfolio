package com.aggfi.portfolio.wave.client;


import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortRow;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("gfinance")
public interface GFinanceService extends RemoteService {
	OverviewPortHeader[] retrievePortfolioNames(String userId) throws IllegalArgumentException;
	OverviewPortRow[] retrieveOverview(String userId, String portName) throws IllegalArgumentException;
}
