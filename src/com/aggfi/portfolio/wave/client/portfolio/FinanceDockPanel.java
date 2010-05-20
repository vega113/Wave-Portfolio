package com.aggfi.portfolio.wave.client.portfolio;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class FinanceDockPanel extends DockPanel {
	
	private FlexTable layoutOverview;

	public FinanceDockPanel(CwConstants constants, CwMessages messages, String TAB_PANEL_WIDTH) {
		Log.trace("Entering createWidgetPanel");
		final FlexTable layout = new FlexTable();

		layout.setCellSpacing(1);
		//	    layout.setWidth(LAYOUT_WIDTH);

		DockPanel dock = this;
		//	    dock.setStyleName("cw-DockPanel");
		dock.setSpacing(4);
		dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);


//		HTML welcomeStr = new HTML(messages.portfolios());
//		dock.add(welcomeStr, DockPanel.NORTH);

		//create tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth(TAB_PANEL_WIDTH);
		tabPanel.setAnimationEnabled(true);

		// Add a home tab
		String[] tabTitles = {constants.cwOverview(),constants.cwFundamentals(),constants.cwPerformance(),constants.cwTransactions()};
		tabPanel.add(layout, tabTitles[0]);

		tabPanel.add(new HTML(constants.cwFundamentals() + ": not Implemented Yet"), tabTitles[1]);

		tabPanel.add(new HTML(constants.cwPerformance() + ": not Implemented Yet"), tabTitles[2]);

		tabPanel.add(new HTML(constants.cwTransactions() + ": not Implemented Yet"), tabTitles[3]);

		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");    

		//------------------
		dock.add(tabPanel, DockPanel.SOUTH);

		Log.trace("Exiting createWidgetPanel");
		//add loading image
		layout.setWidget(0, 0, new HTML("<div><img src='http://www.google.com/ig/images/spinner.gif'>" + constants.cwLoading() + "</div> "));
		layoutOverview = layout;
	}

	public FlexTable getLayoutOverview() {
		return layoutOverview;
	}

}
