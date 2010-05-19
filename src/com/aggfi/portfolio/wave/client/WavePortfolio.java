package com.aggfi.portfolio.wave.client;

import java.util.ArrayList;
import java.util.List;

import com.aggfi.portfolio.wave.client.finance.FinanceRetrievePortfolios;
import com.aggfi.portfolio.wave.client.finance.feature.Data;
import com.aggfi.portfolio.wave.client.finance.feature.FinanceFeature;
import com.aggfi.portfolio.wave.client.finance.feature.NeedsFinance;
import com.aggfi.portfolio.wave.client.finance.feature.QuoteUpdateEvent;
import com.aggfi.portfolio.wave.client.finance.feature.QuoteUpdateEventHandler;
import com.aggfi.portfolio.wave.client.portfolio.AuthSub;
import com.aggfi.portfolio.wave.client.portfolio.DisclosureWidget;
import com.aggfi.portfolio.wave.client.portfolio.FinanceDockPanel;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.allen_sauer.gwt.log.client.DivLogger;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.gadgets.client.DynamicHeightFeature;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.NeedsDynamicHeight;
import com.google.gwt.gadgets.client.UserPreferences;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */ 
@ModulePrefs(title = "Aggfi Portfolio",author="Yuri Zelikov",author_email="vega113@aggfi.com", width = 550, height=800 )
//public class WavePortfolio extends WaveGadget<UserPreferences> implements NeedsDynamicHeight, NeedsFinance{
//	public class WavePortfolio extends WaveGadget<UserPreferences> implements NeedsDynamicHeight, NeedsRpc, NeedsIntrinsics {
//public class WavePortfolio implements EntryPoint {
public class WavePortfolio extends Gadget<UserPreferences> implements NeedsDynamicHeight , NeedsFinance{

	//
	/**
	 * An instance of the constants.
	 */
	private CwConstants constants = GWT.create(CwConstants.class);

	private CwMessages messages = GWT.create(CwMessages.class);

	FinanceRetrievePortfolios finService = new FinanceRetrievePortfolios();
	
	/**
	 * stores dsWidgets - i.e. portfolio header rows, it is require cause the method that populates portfolio names is asynchronous
	 */
	private List<DisclosureWidget>  dsOverviewList = null;

	
	@Override
	protected void init(UserPreferences preferences) {
		FinanceDockPanel dock = null;
		try{

			RootPanel.get().setWidth("450px");
			dock = new FinanceDockPanel(constants,messages,TAB_PANEL_WIDTH);
			dynHightFeature.getContentDiv().add(dock);
			dynHightFeature.getContentDiv().add(new AuthSub());
		}catch(Exception e){
			handleError(e);
		}

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				initRemoteLogger(RootPanel.get());
			}
		});

		try{
			populatePortfolioNames(dock.getLayoutOverview());
			
			//schedule positions refresh - each  second
//			 Timer t = new Timer() {
//			      public void run() {
//			    	  Log.debug("In timer");
//			    	  DeferredCommand.addCommand(new Command() {
//							@Override
//							public void execute() {
//								for(DisclosureWidget dsWidget : dsOverviewList){
//									if(!dsWidget.isClosed()){
//										refreshOverviewPortData(dsWidget);
//									}
//								}
//							}
//						});
//			      }
//			    };

			    // Schedule the timer to run once in 5 seconds.
//			    t.scheduleRepeating(1000);
		}catch(Exception e){
			handleError(e);
		}
		dynHightFeature.adjustHeight();
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
		
		
	}


	protected DynamicHeightFeature dynHightFeature = null;
	@Override
	public void initializeFeature(DynamicHeightFeature feature) {
		dynHightFeature= feature;

	}
	
	private FinanceFeature financeFearure;
	@Override
	public void initializeFeature(FinanceFeature feature) {
		Log.debug("initialized finance feature: " +feature.toString() + ", quote: " + feature.getQuoteInstance().toString());
		this.financeFearure = feature;
		financeFearure.addQuoteUpdateEventHandler(quoteUpdateEventHandler);
	}

	
	/**
	 * This is the entry point method.
	 */

	/*
	public void onModuleLoad() {
		FinanceDockPanel dock = null;
			
		try{
			AbsolutePanel panel = null;

			panel = RootPanel.get("container2");
						panel.add(new AuthSub());

			panel = RootPanel.get("container1");
			dock = new FinanceDockPanel(constants,messages,portUserName,TAB_PANEL_WIDTH);
			panel.add(dock);

			panel = RootPanel.get("container3");
			initRemoteLogger(panel);
			populatePortfolioNames(dock.getLayoutOverview());
			DeferredCommand.addCommand(new Command() {
				
				@Override
				public void execute() {
					for(DisclosureWidget dsWidget : dsOverviewList){
						refreshOverviewPortData(dsWidget);
					}
					
				}
			});
		}catch(Exception e){
			handleError(e);
		}
	}
	
	*/

	protected void populatePortfolioNames(final FlexTable layout) {
		Log.debug("Entering: populatePortfolioNames" );
		finService.retrievePortfolioNames(new AsyncCallback<OverviewPortHeader[]>() {
			public void onFailure(Throwable error) {
				Log.warn("Exception in call to finService.retrievePortfolioNames", error);
				handleError(error);
			}
			public void onSuccess(OverviewPortHeader[] portHeaders) {
				Log.debug("Success on finService.retrievePortfolioNames");
				dsOverviewList = displayPortfolioNames(portHeaders,layout);
				dynHightFeature.adjustHeight();
			}
		});
		Log.debug("Exiting populatePortfolioNames" );
	}


	protected List<DisclosureWidget> displayPortfolioNames(OverviewPortHeader[] portHeaders, FlexTable layout) {
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		int counter = 0;
		List<DisclosureWidget> dsList = new ArrayList<DisclosureWidget>();
		for(OverviewPortHeader portHeader : portHeaders){
			cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
			final DisclosureWidget dsWidget = new DisclosureWidget(constants,messages,portHeader);
			dsWidget.setOpenHandler(new OpenHandler<DisclosurePanel> () {

				@Override
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					DeferredCommand.addCommand(new Command() {
						@Override
						public void execute() {
							refreshOverviewPortData(dsWidget);
							dynHightFeature.adjustHeight();
							dsWidget.setClosed(false);
						}
					});
				}
			});
			
			dsWidget.setCloseHandler(new CloseHandler<DisclosurePanel>() {
				
				@Override
				public void onClose(CloseEvent<DisclosurePanel> event) {
					dynHightFeature.adjustHeight();
					dsWidget.setClosed(true);
				}
			});
			layout.setWidget(counter, 0, dsWidget);
			counter++;
			dsList.add(dsWidget);
		}
		return dsList;
	}
	
	QuoteUpdateEventHandlerImpl quoteUpdateEventHandler = new QuoteUpdateEventHandlerImpl();
	class QuoteUpdateEventHandlerImpl implements QuoteUpdateEventHandler
	 {
		private OverviewPortRow[] result;
		private Iterable<AsyncCallback<Data>> callbacksList;
		
		public void setResult(OverviewPortRow[] result) {
			this.result = result;
		}

		public void setCallbacksList(Iterable<AsyncCallback<Data>> callbacksList) {
			this.callbacksList = callbacksList;
		}

		public void setDsWidget(DisclosureWidget dsWidget) {
			this.dsWidget = dsWidget;
		}

		private DisclosureWidget dsWidget;

		@Override
		public void onUpdate(QuoteUpdateEvent event) {
			try {
				for(AsyncCallback<Data> callback : callbacksList){
					callback.onSuccess(event.getData());
				}
				dsWidget.portPopulate( result);
			} catch (Exception e) {
				Log.error("in callback", e);
			}
		}
	};
	
	OverviewAsyncCallbackImpl overviewCallback = new OverviewAsyncCallbackImpl();
	class OverviewAsyncCallbackImpl implements AsyncCallback<OverviewPortRow[]>{
		
		private DisclosureWidget dsWidget;

		public void setDsWidget(DisclosureWidget dsWidget) {
			this.dsWidget = dsWidget;
		}

		@Override
		public void onSuccess(final OverviewPortRow[] result) {
			
			// now create list of symbols
			final List<AsyncCallback<Data>> callbacksList = new ArrayList<AsyncCallback<Data>>();
			for(OverviewPortRow row : result){
				symbolsList.add(row.getSymbol());
				callbacksList.add(row.getCallback());
			}
			quoteUpdateEventHandler.setCallbacksList(callbacksList);
			quoteUpdateEventHandler.setDsWidget(dsWidget);
			quoteUpdateEventHandler.setResult(result);
			financeFearure.getQuoteInstance().getQuotes(symbolsList.toArray(new String[1]));
		}

		@Override
		public void onFailure(Throwable caught) {
			Log.error("Error in DsWidget callback! ", caught);
		}
	};

	List<String> symbolsList = new ArrayList<String>();
	private void refreshOverviewPortData(final DisclosureWidget dsWidget) {
		Log.debug("before: refreshOverviewPortData");
		symbolsList.clear();
		overviewCallback.setDsWidget(dsWidget);
		finService.retrievePortfolioOverview(dsWidget.getPortId(), dsWidget.getCash(), constants.cwCash(),  overviewCallback);
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


	private static final String TAB_PANEL_WIDTH = "500px";

	/**
	 * The constants used in this Content Widget.
	 */
	public  interface CwConstants extends Constants{

		@DefaultStringValue(value = "500px")
		String cwDW_WIDTH();
		
		@DefaultStringValue(value = "Loading...")
		String cwLoading();

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
		
		@DefaultStringValue(value = "Cash")
		String cwCash();

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
		@DefaultMessage("Google Finance Portfolios")
		String portfolios();

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
		 * @param cash  - Portfolio cash
		 * @param costBasis  - the total portfolio market value
		 * @return Whole overview portfolio header
		 */
		@DefaultMessage("| {0}|  Change: {1}, Total Mkt Value: {2}")
		String cwOverviewPortHeader(String name, String change, String mktVakue);
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
