package com.aggfi.portfolio.wave.client.portfolio;

import java.util.HashMap;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class FinanceDockPanel extends DockPanel {
	
	private FlexTable layoutOverview;

	public FinanceDockPanel(CwConstants constants, CwMessages messages, int width) {
		Log.trace("Entering createWidgetPanel");
		final FlexTable portfolioNamesLayout = new FlexTable();

		portfolioNamesLayout.setCellSpacing(0);
		//	    layout.setWidth(LAYOUT_WIDTH);

		DockPanel dock = this;
		//	    dock.setStyleName("cw-DockPanel");
		dock.setSpacing(0);
		dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);


//		HTML welcomeStr = new HTML(messages.portfolios());
//		dock.add(welcomeStr, DockPanel.NORTH);

		//create tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth(width+"px");
		tabPanel.setAnimationEnabled(true);

		// Add a home tab
		String[] tabTitles = {constants.cwPortfolio(),constants.cwFeeds(),constants.cwSettings()};
		tabPanel.add(portfolioNamesLayout, tabTitles[0]);

//		tabPanel.add(new HTML(constants.cwNews() + ": not Implemented yet"), tabTitles[1]);

		tabPanel.add(new HTML(constants.cwFeeds() + ": This tab should contain transactions by user and by Aggfi community - each in separate tab. Displayed Aggfi transactions will be relavant to stocks in user portfolio only"), tabTitles[1]);

		tabPanel.add(new HTML(constants.cwSettings() + ": This tab should allow modification of user settings: add/remove columns in Portfolios tab, community settins - load contacts to Aggfi etc.."), tabTitles[2]);

		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");    

		//------------------
		dock.add(tabPanel, DockPanel.SOUTH);

		Log.trace("Exiting createWidgetPanel");
		//add loading image
		portfolioNamesLayout.setWidget(0, 0, new HTML("<div><img src='http://www.google.com/ig/images/spinner.gif'>" + constants.cwLoading() + "</div> "));
		layoutOverview = portfolioNamesLayout;
	}

	public FlexTable getLayoutOverview() {
		return layoutOverview;
	}

}
