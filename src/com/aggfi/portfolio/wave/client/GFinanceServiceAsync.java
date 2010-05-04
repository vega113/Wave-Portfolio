package com.aggfi.portfolio.wave.client;

import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortRow;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GFinanceServiceAsync {
	void retrievePortfolioNames(String userId, AsyncCallback<OverviewPortHeader[]> callback)
			throws IllegalArgumentException;

	void retrieveOverview(String userId, String portName,
			AsyncCallback<OverviewPortRow[]> callback);
}
