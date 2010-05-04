package com.aggfi.portfolio.wave.server;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.aggfi.portfolio.wave.client.GFinanceService;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortRow;
import com.aggfi.portfolio.wave.shared.FieldVerifier;
import com.google.gdata.util.ServiceException;
import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GFinanceServiceImpl extends RemoteServiceServlet implements GFinanceService {

	@Override
	public OverviewPortHeader[] retrievePortfolioNames(String userId) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidUserId(userId)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Illegal user Id!");
		}
		
		OverviewPortHeader[] portHeaders = null;
		try {
			portHeaders = FinancePortfolioClient.retrievePortHeaders();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return portHeaders;
	}

	@Override
	public OverviewPortRow[] retrieveOverview(String userId, String portName)
			throws IllegalArgumentException {
		
		if (!FieldVerifier.isValidUserId(userId)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Illegal user Id!");
		}
		if (!FieldVerifier.isValidPortName(portName)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Invalid portfolio name!");
		}
		
		OverviewPortRow row = new OverviewPortRow();
		row.initOverviewPortRow(2.2, 0.3, 0.99, Long.valueOf("05454545445"), Long.valueOf("1123"), 27.2, 27.3, 27.1, 12, "Amdocs", "DOX");
		
		OverviewPortRow row1 = new OverviewPortRow();
		row1.initOverviewPortRow(2.2, 3, 0.025, Long.valueOf("12004545445"), Long.valueOf("9876543211"), 27.2, 27.3, 27.1, 12, "Amdocs", "DOX");
		
		OverviewPortRow row2 = new OverviewPortRow();
		row2.initCashOverviewPortRow(97354.76, "Cash", "USD");
		
		OverviewPortRow[] rows = new OverviewPortRow[3];
		rows[0] = row;
		rows[1] = row1;
		rows[2] = row2;
		return rows;
	}

	/**
	   * Do not validate HTTP headers - iGoogle munges them.
	   */
	  @Override
	  protected String readContent(HttpServletRequest request)
	      throws ServletException, IOException {
	    return RPCServletUtils.readContentAsUtf8(request, false);
	  }
	
}
