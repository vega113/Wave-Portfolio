package com.aggfi.portfolio.wave.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aggfi.portfolio.wave.client.finance.FinanceRetrievePortfolios;
import com.aggfi.portfolio.wave.client.finance.OverviewAsyncCallbackImpl;
import com.aggfi.portfolio.wave.client.finance.PoolFinanceCallbacks;
import com.aggfi.portfolio.wave.client.finance.feature.FinanceFeature;
import com.aggfi.portfolio.wave.client.finance.feature.NeedsFinance;
//import com.aggfi.portfolio.wave.client.finance.OverviewAsyncCallbackImpl;
//import com.aggfi.portfolio.wave.client.finance.PoolFinanceCallbacks;
//import com.aggfi.portfolio.wave.client.finance.feature.Data;
//import com.aggfi.portfolio.wave.client.finance.feature.FinanceFeature;
//import com.aggfi.portfolio.wave.client.finance.feature.NeedsFinance;
//import com.aggfi.portfolio.wave.client.finance.feature.PoolQuoteHandler;
//import com.aggfi.portfolio.wave.client.finance.feature.QuoteUpdateEvent;
//import com.aggfi.portfolio.wave.client.finance.feature.QuoteUpdateEventHandler;
import com.aggfi.portfolio.wave.client.portfolio.AuthSub;
import com.aggfi.portfolio.wave.client.portfolio.ColSettings;
import com.aggfi.portfolio.wave.client.portfolio.DisclosureWidget;
import com.aggfi.portfolio.wave.client.portfolio.ResolutionEnum;
import com.aggfi.portfolio.wave.client.portfolio.FinanceDockPanel;
import com.aggfi.portfolio.wave.client.portfolio.PortfolioPreferences;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.allen_sauer.gwt.log.client.DivLogger;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
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
@ModulePrefs(title = "Aggfi Portfolios: based on Google Finance",author="Yuri Zelikov",author_email="vega113@aggfi.com",author_link="http://aggfi.com" )
//public class WavePortfolio extends WaveGadget<UserPreferences> implements NeedsDynamicHeight, NeedsFinance{
//	public class WavePortfolio extends WaveGadget<UserPreferences> implements NeedsDynamicHeight, NeedsRpc, NeedsIntrinsics {
//public class WavePortfolio implements EntryPoint {
public class WavePortfolio extends Gadget<PortfolioPreferences> implements NeedsDynamicHeight , NeedsFinance{
	
	private static HashMap<ResolutionEnum,Integer> resolutionsMap = new HashMap<ResolutionEnum, Integer>();
	
	private ResolutionEnum currResolution = ResolutionEnum._1920x1280;

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
	protected void init(PortfolioPreferences preferences) {
		try{
			currResolution = preferences.getResolution().getValue();
			initResolutions();
			gadgetWidth = resolutionsMap.get(currResolution);
			
			
//			RootPanel.get().setWidth("0px");
//			RootPanel.get().setHeight("0px");
//			RootPanel.get().setVisible(false);
			initFinanceDockPanel(defaultDockPanelSettings());
		}catch(Exception e){
			handleError(e);
		}
		adjustHeight();
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

	private void initResolutions() {
		//resolution - pixels corresponding to that resolution
		resolutionsMap.put(ResolutionEnum._1024x1280, 300);
		resolutionsMap.put(ResolutionEnum._1920x1280, 500);
	}
	
	private void initFinanceDockPanel(final HashMap<String,ColSettings> dockPanelSettings) {
		FinanceDockPanel dock = null;
		int width = retrWidth();
		dock = new FinanceDockPanel(constants,messages,width);
		final FlexTable layout = dock.getLayoutOverview();
		dynHightFeature.getContentDiv().add(dock);
		dynHightFeature.getContentDiv().add(new AuthSub());

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				initRemoteLogger(RootPanel.get());
			}
		});

		populatePortfolioNames(layout,dockPanelSettings);
		Timer t = new Timer() {
			public void run() {
				DeferredCommand.addCommand(new Command() {
					@Override
					public void execute() {
						populatePortfolioNames(layout,dockPanelSettings);
						for(DisclosureWidget dsWidget : dsOverviewList){
							if(!dsWidget.isClosed()){
								Log.info("dsWidget is: " + dsWidget.isClosed() + ", " + dsWidget.getPortName());
								refreshOverviewPortData(dsWidget);
							}else{
								Log.trace("dsWidget Is closed: " + dsWidget.getPortName());
							}
						}
					}
				});
			}
		};

		// Schedule the timer to run once in 5 seconds.
		t.scheduleRepeating(5000);
	}

	private int retrWidth() {
		return gadgetWidth;
	}


	private void adjustHeight() {
		dynHightFeature.adjustHeight();
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
			dock = new FinanceDockPanel(constants,messages,TAB_PANEL_WIDTH);
			panel.add(dock);
			final FlexTable layout = dock.getLayoutOverview();
			panel = RootPanel.get("container3");
			initRemoteLogger(panel);
			populatePortfolioNames(dock.getLayoutOverview());
			
			Timer t = new Timer() {
				public void run() {
					DeferredCommand.addCommand(new Command() {
						@Override
						public void execute() {
							populatePortfolioNames(layout);
//							for(DisclosureWidget dsWidget : dsOverviewList){
//								if(!dsWidget.isClosed()){
//									Log.info("dsWidget is: " + dsWidget.isClosed() + ", " + dsWidget.getPortName());
//									refreshOverviewPortData(dsWidget);
//								}else{
//									Log.trace("dsWidget Is closed: " + dsWidget.getPortName());
//								}
//							}
						}
					});
				}
			};

			// Schedule the timer to run once in 5 seconds.
			t.scheduleRepeating(5000);
			
			
//			DeferredCommand.addCommand(new Command() {
//				
//				@Override
//				public void execute() {
//					for(DisclosureWidget dsWidget : dsOverviewList){
//						refreshOverviewPortData(dsWidget);
//					}
//					
//				}
//			});
		}catch(Exception e){
			handleError(e);
		}
	}
	
	*/
	
	/**
	 * Inner class to serve as callback passed to {@link FinanceRetrievePortfolios.retrievePortfolioNames()}
	 * Set layout field before use
	 * It's onSuccess method is invoked after retrieval of portfolio feed and construction of portHeaders
	 * portHeaders are passed to onSuccess
	 * There's only one instance per portfolio, so no need to pool this class
	 */
	protected class PopulatePortCallbackImpl implements AsyncCallback<OverviewPortHeader[]> {
		private FlexTable layout;
		private HashMap<String, ColSettings> dockPanelSettings;
		public void setLayout(FlexTable layout) {
			this.layout = layout;
		}
		public void onFailure(Throwable error) {
			Log.warn("Exception in call to finService.retrievePortfolioNames", error);
		}
		public void onSuccess(OverviewPortHeader[] portHeaders) {
			if(dsOverviewList == null){
				dsOverviewList = displayPortfolioNames(portHeaders,layout,dockPanelSettings);
			}else{
				dsOverviewList = updatePortfolioNames(portHeaders, layout, dsOverviewList,dockPanelSettings);
			}
			adjustHeight();
		}
		public void setDockPanelSettings(
				HashMap<String, ColSettings> dockPanelSettings) {
			this.dockPanelSettings = dockPanelSettings;
			
		}
	}

	PopulatePortCallbackImpl populatePortCallbackImpl = new PopulatePortCallbackImpl();

	private int gadgetWidth = 300;
	protected void populatePortfolioNames(final FlexTable layout, HashMap<String, ColSettings> dockPanelSettings) {
		populatePortCallbackImpl.setLayout(layout);
		populatePortCallbackImpl.setDockPanelSettings(dockPanelSettings);
		finService.retrievePortfolioNames(populatePortCallbackImpl );
		adjustHeight();
		Log.trace("Exiting populatePortfolioNames" );
	}


	protected List<DisclosureWidget> displayPortfolioNames(OverviewPortHeader[] portHeaders, FlexTable layout, HashMap<String, ColSettings> dockPanelSettings) {
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		int counter = 0;
		List<DisclosureWidget> dsList = new ArrayList<DisclosureWidget>();
		for(OverviewPortHeader portHeader : portHeaders){
			cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
			int width = retrWidth();
			final DisclosureWidget dsWidget = new DisclosureWidget(constants,messages,portHeader,dockPanelSettings,width);
			dsWidget.setOpenHandler(new OpenHandler<DisclosurePanel> () {

				@Override
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					DeferredCommand.addCommand(new Command() {
						@Override
						public void execute() {
//							refreshOverviewPortData(dsWidget);
							adjustHeight();
							dsWidget.setClosed(false);
						}
					});
				}
			});
			
			dsWidget.setCloseHandler(new CloseHandler<DisclosurePanel>() {
				
				@Override
				public void onClose(CloseEvent<DisclosurePanel> event) {
					adjustHeight();
					dsWidget.setClosed(true);
				}
			});
			layout.setWidget(counter, 0, dsWidget);
			counter++;
			dsList.add(dsWidget);
			adjustHeight();
		}
		return dsList;
	}
	
	
	protected List<DisclosureWidget> updatePortfolioNames(OverviewPortHeader[] portHeaders, FlexTable layout,List<DisclosureWidget> dsList, HashMap<String, ColSettings> dockPanelSettings) {
		Log.trace("Entering updatePortfolioNames");
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		if(dsList == null){
			dsList = new ArrayList<DisclosureWidget>();
		}
		int counter = 0;
		//create HashMap from List
		HashMap<String,DisclosureWidget> dsMap = new HashMap<String,DisclosureWidget>();
		for(DisclosureWidget tempWidget : dsList){
			dsMap.put(tempWidget.getPortId(), tempWidget);
		}
		for(OverviewPortHeader portHeader : portHeaders){
			cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
			DisclosureWidget dsWidget = dsMap.get(portHeader.getPortId());
			if(dsWidget == null){//TODO 
				Log.warn("Adding new portfolio inline! " + portHeader.getPortId());
				int width = retrWidth();
				final DisclosureWidget dsWidgetTemp = new DisclosureWidget(constants,messages,portHeader,dockPanelSettings,width);
				dsWidgetTemp.setOpenHandler(new OpenHandler<DisclosurePanel> () {

					@Override
					public void onOpen(OpenEvent<DisclosurePanel> event) {
						DeferredCommand.addCommand(new Command() {
							@Override
							public void execute() {
								refreshOverviewPortData(dsWidgetTemp);
								adjustHeight();
								dsWidgetTemp.setClosed(false);
							}
						});
					}
				});
				
				dsWidgetTemp.setCloseHandler(new CloseHandler<DisclosurePanel>() {
					
					@Override
					public void onClose(CloseEvent<DisclosurePanel> event) {
						adjustHeight();
						dsWidgetTemp.setClosed(true);
					}
				});
				//add new widget - in case something changed while the gadget is running
				layout.setWidget(counter, 0, dsWidgetTemp);
				dsList.add(dsWidgetTemp);
				
			}else{
				Log.trace("updating portfolio title: " + portHeader.getPortName());
				dsWidget.updatePortTitle(portHeader);
				Log.trace("updatePortfolioNames header : " + portHeader.toString());
			}
			counter++;
		}
		Log.trace("Exiting updatePortfolioNames");
		return dsList;
	}
	
	/**
	 * 
	 * @param dsWidget
	 */
	private void refreshOverviewPortData(final DisclosureWidget dsWidget) {
		Log.trace("before: refreshOverviewPortData");
		OverviewAsyncCallbackImpl overviewCallback =PoolFinanceCallbacks.instance().getOverviewAsyncCallbackImpll(dsWidget,financeFearure);
//		OverviewAsyncCallbackImpl overviewCallback =PoolFinanceCallbacks.instance().getOverviewAsyncCallbackImpll(dsWidget);
		finService.retrievePortfolioOverview(dsWidget.getPortId(), dsWidget.getCash(), constants.cwCash(),  overviewCallback);
		Log.trace("after: refreshOverviewPortData");
	} 


	public void initRemoteLogger(AbsolutePanel panel){
		//		Log.setUncaughtExceptionHandler();
		if (panel != null) {
			panel.add (Log.getLogger(DivLogger.class).getWidget());
			Log.info("Logger initialized: " + Log.class.getName());
		}
	}

	private void handleError(Throwable error) {
		Log.error("Trace: ", error);
	}
	
	private HashMap<String, ColSettings> defaultDockPanelSettings() {
		HashMap<String, ColSettings> settings = new HashMap<String, ColSettings>();
		// key - symbol name, width, column location number -> -1 - don't show
		ColSettings symbolSettings = null;
		ColSettings lastSettings = null;
		ColSettings changeSettings = null;
		ColSettings volumeSettings = null;
		ColSettings openSettings = null;
		ColSettings lowSettings = null;
		ColSettings highSettings = null;
		ColSettings gainSettings = null;
		if (currResolution == ResolutionEnum._1024x1280) {
			symbolSettings = new ColSettings("symbol", 10, 0);
			lastSettings = new ColSettings("last", 30, 1);
			changeSettings = new ColSettings("change", 20, 2);
			volumeSettings = new ColSettings("volume", 10, 3);
			openSettings = new ColSettings("open", 10, -1);
			lowSettings = new ColSettings("low", 10, -1);
			highSettings = new ColSettings("high", 10, -1);
			gainSettings = new ColSettings("gain", 10, 4);
		}else if (currResolution == ResolutionEnum._1920x1280) {
			symbolSettings = new ColSettings("symbol", 30, 0);
			lastSettings = new ColSettings("last", 50, 1);
			changeSettings = new ColSettings("change", 100, 2);
			volumeSettings = new ColSettings("volume", 100, 3);
			openSettings = new ColSettings("open", 50, 4);
			lowSettings = new ColSettings("low", 50, 5);
			highSettings = new ColSettings("high", 50, 6);
			gainSettings = new ColSettings("gain", 50, 7);
		}
		
		settings.put(symbolSettings.getColName(), symbolSettings);
		settings.put(lastSettings.getColName(), lastSettings);
		settings.put(changeSettings.getColName(), changeSettings);
		settings.put(volumeSettings.getColName(), volumeSettings);
		settings.put(openSettings.getColName(), openSettings);
		settings.put(lowSettings.getColName(), lowSettings);
		settings.put(highSettings.getColName(), highSettings);
		settings.put(gainSettings.getColName(), gainSettings);
		
		return settings;
	}


	/**
	 * The constants used in this Content Widget.
	 */
	public  interface CwConstants extends Constants{

		@DefaultStringValue(value = "300px")
		String cwDW_WIDTH();
		
		@DefaultStringValue(value = " Loading...")
		String cwLoading();

		@DefaultStringValue(value = "Portfolios")
		String cwPortfolio();

		@DefaultStringValue(value = "for")
		String cwFor();

		@DefaultStringValue(value = "Overview")
		String cwOverview();

		@DefaultStringValue(value = "News")
		String cwNews();

		@DefaultStringValue(value = "Feeds")
		String cwFeeds();

		@DefaultStringValue(value = "Settings")
		String cwSettings();
		
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

		@DefaultStringValue(value = "Last")
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

		@DefaultStringValue(value = "Gain")
		String cwDaysGain();
		
		@DefaultStringValue(value = "Mkt Value")
		String cwMktValue();
		
		//-------------------
		
		@DefaultStringValue(value = "Problem loading data, please check if you are signed in...")
		String cwSignIn();
		//-------------------
		@DefaultStringValue(value = "Click to show portfolio info")
		String cwPlusClosedTooltip();
		
		@DefaultStringValue(value = "Click to show less info")
		String cwPlusOpenTooltip();
		
		@DefaultStringValue(value = "Portfolio name")
		String cwPortNameTooltip();
		
		@DefaultStringValue(value = "Stock symbol")
		String cwSymbolTooltip();
		
		@DefaultStringValue(value = "Change in Portfolio value since Close")
		String cwPortChangeTooltip();
		
		@DefaultStringValue(value = "Change in Stock value since Close")
		String cwStockChangeTooltip();
		
		@DefaultStringValue(value = "Day's gain: Change x Shares")
		String cwDaysGainTooltip();
		
		@DefaultStringValue(value = "Market value of Portfolio")
		String cwPortMktValueTooltip();
		
		//-------------------------------

		@DefaultIntValue(value = 800)
		int cwChangeHighlightDuration();

	}
	public interface CwMessages extends Messages {
		/**
		 * @param Portfolios
		 * @param Username
		 * @return a welcome message for user
		 */
		@DefaultMessage("Google Finance Portfolios By Aggfi")
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
		 * @param costBasis  - the total portfolio market value
		 * @param color - the color of change - red,blue,green
		 * @return Whole overview portfolio header
		 */
		@DefaultMessage("Change: <font color={2}>{0}</font>, Mkt Value: {1}")
		String cwOverviewPortHeader(String change, String mktVakue, String color);
		
		@DefaultMessage("Last trade time: {0}")
		String cwLastTradeTime(String lastTradeTime);
		

		/**
		 * 
		 * @param portName
		 * @return
		 */
		@DefaultMessage("No positions in portfolio: {0}.")
		String cwNoPositionEntries(String portName);
	}
}
