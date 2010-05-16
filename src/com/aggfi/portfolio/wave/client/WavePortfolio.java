package com.aggfi.portfolio.wave.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
//import com.google.gwt.core.client.JavaScriptObject;
//import com.google.gwt.core.client.JsArray;
//
//import org.cobogw.gwt.waveapi.gadget.client.StateUpdateEvent;
//import org.cobogw.gwt.waveapi.gadget.client.StateUpdateEventHandler;
//import org.cobogw.gwt.waveapi.gadget.client.WaveGadget;
//
import com.google.gwt.gadgets.client.DynamicHeightFeature;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.NeedsDynamicHeight;
import com.google.gwt.gadgets.client.UserPreferences;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;

//import com.aggfi.portfolio.wave.client.finance.AuthSub;
//import com.aggfi.portfolio.wave.client.finance.FinanceRetrievePortfolios;
import com.aggfi.portfolio.wave.client.finance.AuthSub;
import com.aggfi.portfolio.wave.client.finance.FinanceFeature;
import com.aggfi.portfolio.wave.client.finance.FinanceRetrievePortfolios;
import com.aggfi.portfolio.wave.client.finance.NeedsFinance;
//import com.aggfi.portfolio.wave.client.finance.Quote;
//import com.aggfi.portfolio.wave.client.finance.QuoteUpdateEvent;
//import com.aggfi.portfolio.wave.client.finance.QuoteUpdateEventHandler;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortRow;
import com.allen_sauer.gwt.log.client.DivLogger;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */ 
@ModulePrefs(title = "WavePortfolio 11",author="Yuri Zelikov",author_email="vega113@aggfi.com", width = 550)
//public class WavePortfolio extends WaveGadget<UserPreferences> implements NeedsDynamicHeight, NeedsFinance{
//	public class WavePortfolio extends WaveGadget<UserPreferences> implements NeedsDynamicHeight, NeedsRpc, NeedsIntrinsics {
//public class WavePortfolio implements EntryPoint {
public class WavePortfolio extends Gadget<UserPreferences> implements NeedsDynamicHeight , NeedsFinance{

	//
	private String portUserName = "Yuri";
	private String portUserId = "Yuri";

	/**
	 * An instance of the constants.
	 */
	private CwConstants constants = GWT.create(CwConstants.class);

	private CwMessages messages = GWT.create(CwMessages.class);
	private List<DisclosureWidget> overviewDsList = null;

	FinanceRetrievePortfolios finService = new FinanceRetrievePortfolios();

	FlexTable layoutOverview = null;;

	
	@Override
	protected void init(UserPreferences preferences) {
		try{


			dhf.getContentDiv().add(new AuthSub());
			DockPanel dock = createWidgetPanel();
			dhf.getContentDiv().add(dock);
//			retrieveOverviewPortHeaders("http://localhost:8888/json");//FIXME change URL
		}catch(Exception e){
			handleError(e);
		}

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				initRemoteLogger(RootPanel.get());
			}
		});

		dhf.adjustHeight();
		try{
			Log.debug("before: populatePortfolioNames" );
			populatePortfolioNames(layoutOverview);
			Log.debug("after: populatePortfolioNames" );
			DeferredCommand.addCommand(new Command() {
				
				@Override
				public void execute() {
					for(DisclosureWidget dsWidget : overviewDsList){
						refreshOverviewPortData(dsWidget);
					}
					
				}
			});
		}catch(Exception e){
			handleError(e);
		}
//		getWave().addStateUpdateEventHandler(new StateUpdateEventHandler() {
//			@Override
//			public void onUpdate(StateUpdateEvent event) {
//				DeferredCommand.addCommand(new Command() {
//
//					@Override
//					public void execute() {
//						dhf.adjustHeight();
//					}
//				});
//			}
//		});
//		dhf.adjustHeight();
		
//		fh.getQuoteInstance().getQuote("DOX");
		
			
//		try{
//			//				Quote quote  = null;
//			//				quote = fh.getQuoteInstance();
//			//				Log.debug("finance: " + fh.initFinance());
//			////				quote.getQuote("DOX");
//			fh.addQuoteUpdateEventHandler(new QuoteUpdateEventHandler() {
//
//				@Override
//				public void onUpdate(QuoteUpdateEvent event) {
//					Log.debug("Inside addQuoteUpdateEventHandler:onUpdate");
//					double last = event.getData().getLast();
//					Log.debug("Last price: " + last);
//				}
//			});
//		}catch(Throwable e){
//			Log.error("Error with finance", e);
//		}
		
	}


	protected DynamicHeightFeature dhf = null;
	@Override
	public void initializeFeature(DynamicHeightFeature feature) {
		dhf= feature;

	}
	
	private FinanceFeature fh;
	@Override
	public void initializeFeature(FinanceFeature feature) {
		this.fh = feature;
	}

	
	/*

	protected RpcFeature rpcf = null;
	@Override
	public void initializeFeature(RpcFeature feature) {
		rpcf = feature;
	}

	protected IntrinsicFeature imf = null;
	public void initializeFeature(IntrinsicFeature feature) {
	    this.imf = feature;
	  }
	 */

	/**
	 * This is the entry point method.
	 */

	/*
	public void onModuleLoad() {

		try{
			AbsolutePanel panel = null;

			panel = RootPanel.get("container2");
						panel.add(new AuthSub());

			panel = RootPanel.get("container1");
			DockPanel dock = createWidgetPanel();
			panel.add(dock);

			panel = RootPanel.get("container3");
			initRemoteLogger(panel);
			populatePortfolioNames(layoutOverview);
			DeferredCommand.addCommand(new Command() {
				
				@Override
				public void execute() {
					for(DisclosureWidget dsWidget : overviewDsList){
						refreshOverviewPortData(dsWidget);
					}
					
				}
			});
		}catch(Exception e){
			handleError(e);
		}
	}
	
	*/



	private DockPanel createWidgetPanel() {
		Log.debug("Entering createWidgetPanel");
		final FlexTable layout = new FlexTable();

		layout.setCellSpacing(1);
		//	    layout.setWidth(LAYOUT_WIDTH);

		DockPanel dock = new DockPanel();
		//	    dock.setStyleName("cw-DockPanel");
		dock.setSpacing(4);
		dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);


		HTML welcomeStr = new HTML(messages.portfoliosFor( constants.cwPortfolio(),portUserName));
		dock.add(welcomeStr, DockPanel.NORTH);

		//create tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth(TAB_PANEL_WIDTH);
		tabPanel.setAnimationEnabled(true);

		// Add a home tab
		String[] tabTitles = {constants.cwOverview(),constants.cwFundamentals(),constants.cwPerformance(),constants.cwTransactions()};
		tabPanel.add(layout, tabTitles[0]);

		tabPanel.add(new HTML("tab1"), tabTitles[1]);

		tabPanel.add(new HTML("tab2"), tabTitles[2]);

		tabPanel.add(new HTML("tab3"), tabTitles[3]);

		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");    

		//------------------
		dock.add(tabPanel, DockPanel.SOUTH);

		Log.debug("Exiting createWidgetPanel");
		layoutOverview = layout;
		return dock;
	}




	protected void populatePortfolioNames(final FlexTable layout) {
		
		finService.retrievePortfolioNames(new AsyncCallback<OverviewPortHeader[]>() {
			public void onFailure(Throwable error) {
				Log.warn("Exception in call to finService.retrievePortfolioNames", error);
				handleError(error);
			}
			public void onSuccess(OverviewPortHeader[] portHeaders) {
				Log.debug("Success on finService.retrievePortfolioNames");
				displayPortfolioNames(portHeaders,layout);
			}
		});
	}


	protected void displayPortfolioNames(OverviewPortHeader[] portHeaders, FlexTable layout) {
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		int counter = 0;
		List<DisclosureWidget> dsList = new ArrayList<DisclosureWidget>();
		for(OverviewPortHeader portHeader : portHeaders){
			cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
			final DisclosureWidget dsWidget = new DisclosureWidget(constants,messages,portUserId,portHeader);
			dsWidget.setOpenHandler(new OpenHandler<DisclosurePanel> () {

				@Override
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					refreshOverviewPortData(dsWidget);
				}
			});
			layout.setWidget(counter, 0, dsWidget);
			counter++;
			dsList.add(dsWidget);
		}
		overviewDsList = dsList;
	}

	private void refreshOverviewPortData(final DisclosureWidget dsWidget) {
		Log.debug("before: refreshOverviewPortData");
		finService.retrievePortfolioOverview(dsWidget.getPortId(),  new AsyncCallback<OverviewPortRow[]>() {

			@Override
			public void onSuccess(OverviewPortRow[] result) {
				dsWidget.portPopulate( result);
			}

			@Override
			public void onFailure(Throwable caught) {
				//						Log.error("Error in DsWidget callback! " + caught.getMessage());
			}
		});
		
		Log.debug("after: refreshOverviewPortData");
	} 


	public void initRemoteLogger(AbsolutePanel panel){
		//		Log.setUncaughtExceptionHandler();
		if (panel != null) {
			panel.add (Log.getLogger(DivLogger.class).getWidget());
		}
		Log.info("Logger initialized: " + Log.class.getName());
	}

	private void handleError(Throwable error) {

		Log.error(error.getMessage());	
		Log.error(error.toString());	
		for(StackTraceElement el : error.getStackTrace()){
			Log.error(el.toString());
			Log.trace("Trace: ", error);
		}
		if(error instanceof com.google.gwt.user.client.rpc.StatusCodeException){
			com.google.gwt.user.client.rpc.StatusCodeException ex = (com.google.gwt.user.client.rpc.StatusCodeException)error;
			Log.error("Status code: " + ex.getStatusCode() + ", Message: " + ex.getMessage() + "from method: " + ex.getStackTrace()[0].getMethodName());
			Log.trace("Trace: ", ex);
		}
	}


	private static final String TAB_PANEL_WIDTH = "550px";

	/**
	 * The constants used in this Content Widget.
	 */
	public  interface CwConstants extends Constants{


		@DefaultStringValue(value = "Portfolios")
		String cwPortfolio();

		@DefaultStringValue(value = "for")
		String cwFor();

		@DefaultStringValue(value = "Overview")
		String cwOverview();

		@DefaultStringValue(value = "Fundamentals")
		String cwFundamentals();

		@DefaultStringValue(value = "Performance")
		String cwPerformance();

		@DefaultStringValue(value = "Transactions")
		String cwTransactions();

		//---------------------------------- Big number Abbreviations
		@DefaultStringValue(value = "K")
		String cwThousand();

		@DefaultStringValue(value = "M")
		String cwMillion();

		@DefaultStringValue(value = "B")
		String cwBillion();



		//------------- Overview constants
		@DefaultStringValue(value = "Name")
		String cwName();

		@DefaultStringValue(value = "Symbol")
		String cwSymbol();

		@DefaultStringValue(value = "Last price")
		String cwLastPrice();

		@DefaultStringValue(value = "Change")
		String cwChange();

		@DefaultStringValue(value = "Mkt cap")
		String cwMktCap();

		@DefaultStringValue(value = "Volume")
		String cwVolume();

		@DefaultStringValue(value = "Open")
		String cwOpen();

		@DefaultStringValue(value = "Low")
		String cwLow();

		@DefaultStringValue(value = "High")
		String cwHigh();

		@DefaultStringValue(value = "Day's gain")
		String cwDaysGain();
	}
	public interface CwMessages extends Messages {
		/**
		 * @param Portfolios
		 * @param Username
		 * @return a welcome message for user
		 */
		@DefaultMessage("{0} for {1}")
		String portfoliosFor(String portfolios, String  userName);

		/**
		 * @param abs - absolute change in portfolio value 
		 * @param per - percentage change in portfolio value
		 * @return a combined portfolio change string
		 */
		@DefaultMessage("{0} ({1})")
		String cwChangeHeader(String abs, String per);

		/**
		 * 
		 * @param name
		 * @param change
		 * @return Whole overview portfolio header
		 */
		@DefaultMessage("{0}  Change: {1}")
		String cwOverviewPortHeader(String name, String change);
	}
	


	//	  public native void retrieveOverviewPortHeaders(String url) /*-{
	//	    var myObj = this;
	//		var params = {};
	//		params[gadgets.io.RequestParameters.METHOD] = gadgets.io.MethodType.GET;
	//		params[gadgets.io.RequestParameters.REFRESH_INTERVAL] = 1;
	//		gadgets.io.makeRequest(url, function(obj) {
	//		myObj.@com.aggfi.portfolio.wave.client.WavePortfolio::acceptOverviewPortHeaderJson(Ljava/lang/String;)(obj.text);
	//			}, params); 
	//	 }-*/;

}
