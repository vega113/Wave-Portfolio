package com.aggfi.portfolio.wave.client.finance;

import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortHeader;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.gdata.client.finance.DaysGain;
import com.google.gwt.gdata.client.finance.MarketValue;
import com.google.gwt.gdata.client.finance.PortfolioData;
import com.google.gwt.gdata.client.finance.PortfolioEntry;

public class PortfolioTools {

	public static OverviewPortHeader buildOverviewPortHeader(PortfolioEntry portfolioEntry) {
		Log.debug("entering buildOverviewPortHeader");
		OverviewPortHeader header = new OverviewPortHeader();
	    return updateOverviewPortHeader(portfolioEntry,header);
	  }

	public static OverviewPortHeader updateOverviewPortHeader(
			PortfolioEntry portfolioEntry,OverviewPortHeader header) {
		String portfolioId = portfolioEntry.getId().getValue();
	    PortfolioData portfolioData = portfolioEntry.getPortfolioData();
	    MarketValue mv = null;
	    double costBasis = 0;
	    costBasis = portfolioData.getCostBasis() !=  null && portfolioData.getCostBasis().getMoney() != null ? portfolioData.getCostBasis().getMoney()[0].getAmount() : 0;
	    mv = portfolioData.getMarketValue();
	    double mktValue = mv != null && mv.getMoney() != null ? mv.getMoney()[0].getAmount() : 0;
	    DaysGain dg = portfolioData.getDaysGain();
	    double portTodayGain  =  dg != null ? dg.getMoney()[0].getAmount() : 0;
	    double changePercent = mktValue - portTodayGain != 0 ? portTodayGain/( mktValue - portTodayGain) : 0;
	    String portName = portfolioEntry.getTitle().getText();
	    double gain = portfolioData.getGain() != null && portfolioData.getGain().getMoney() != null ? portfolioData.getGain().getMoney()[0].getAmount() : 0;
	    double cash = mktValue-gain-costBasis;
	    header.init(portName,portfolioId, portTodayGain,  changePercent, cash, mktValue);
	    Log.debug("Exiting updateOverviewPortHeader: " + ", " + header.toString() );
		return header;
	}
}
