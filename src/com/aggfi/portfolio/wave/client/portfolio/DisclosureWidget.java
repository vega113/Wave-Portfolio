package com.aggfi.portfolio.wave.client.portfolio;


import java.util.HashMap;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.format.FormatBigNumbers;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.aggfi.portfolio.wave.client.portfolio.OverviewFlexTable;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;

public class DisclosureWidget extends VerticalPanel {
	
	/**
	   * An instance of the constants.
	   */
	private CwConstants constants = null;
	private CwMessages messages = null;
	protected String portName = "";
	protected String portId = "";
	private String userId = "";
	protected DisclosurePanel disclosurePanel;
	private boolean isPortRetrieved = false;
	
	protected GlobalResources res;
	
	
	OverviewFlexTable table = null;
	
	private double mktValue;
	private boolean isClosed = true;
	private DisclosurePanel advancedDisclosure;
	private Widget header;
	
	private FormatBigNumbers fmtBig;
	private String cash;
	private HashMap<String, ColSettings> dockPanelSettings;
	public String getCash() {
		return cash;
	}

	Widget plusClosed =  new HTML("<span>[+]</span>");
	Widget plusOpen =  new HTML("<span>[-]</span>");
	private FlexTable headerTable;
	Panel dsPanel = null;// maybe change type of panel dependent on size //TODO
	private int width;
	
	private Widget formatPortHeader(String name, String portId, double changeAbsVal, double changePercent, double mktValue){
		dsPanel.clear();
		Widget changeVal = fmtBig.formatChange(changeAbsVal, changePercent, messages, dsPanel);
		headerTable = new FlexTable();
		ColumnFormatter colFormatter = headerTable.getColumnFormatter();
		 
		if(isClosed){
			headerTable.setWidget(0, 0, plusClosed);
		}else{
			headerTable.setWidget(0, 0, plusOpen);
		}
		
		Widget portfolioName = new HTML(name);
		portfolioName.setStyleName(res.globalCSS().bold());
		headerTable.setWidget(0, 1, portfolioName);
		portfolioName.setTitle(constants.cwPortNameTooltip());
		
		
//		Widget changeTtl = new HTML(constants.cwChange() + ":");
//		headerTable.setWidget(0, 2, changeTtl);
//		colFormatter.setWidth(2, "10%");
		

		if(changeAbsVal > 0){
			changeVal.setStyleName(res.globalCSS().upChange());
			colFormatter.setStyleName(0,res.globalCSS().upChange());
		}else if(changeAbsVal < 0){
			changeVal.setStyleName(res.globalCSS().downChange());
			colFormatter.setStyleName(0,res.globalCSS().downChange());
		}else {
			changeVal.setStyleName(res.globalCSS().noChange());
			colFormatter.setStyleName(0,res.globalCSS().noChange());
		}
		
		headerTable.setWidget(0, 2, changeVal);
		
		changeVal.setTitle(constants.cwPortChangeTooltip());
		

		setStylesWidth(colFormatter);
		
//		Widget mktValueTtl = new HTML(constants.cwMktValue() + ":");
//		headerTable.setWidget(0, 4, mktValueTtl);
//		colFormatter.setWidth(4, "15%");
		
		Widget mktValueVal = new HTML(NumberFormat.getCurrencyFormat().format(mktValue));
		mktValueVal.setTitle(constants.cwPortMktValueTooltip());
		headerTable.setWidget(0, 3, mktValueVal);
		
		headerTable.setStyleName(res.globalCSS().portHeaderBG());
		
		return headerTable;
	}


	private void setStylesWidth(ColumnFormatter colFormatter) {
		if(width < 400){
			colFormatter.setWidth(0, "6%");
			colFormatter.setWidth(1, "23%");
			colFormatter.setWidth(2, "30%");
			colFormatter.setWidth(3, "31%");
		}else{
			colFormatter.setWidth(0, "15px");
			colFormatter.setWidth(1, "100px%");
			colFormatter.setWidth(2, "100px%");
			colFormatter.setWidth(2, "100px%");
//			colFormatter.setWidth(3, "26%");
		}
		colFormatter.setStyleName(1, res.globalCSS().leftAlign());
		colFormatter.setStyleName(2, res.globalCSS().centerAlign());
		colFormatter.setStyleName(3, res.globalCSS().rightAlign());
	}

	
	  public DisclosureWidget(CwConstants constants, CwMessages messages, OverviewPortHeader portHeader, HashMap<String, ColSettings> dockPanelSettings, int width) {
		  super();
		  this.dockPanelSettings = dockPanelSettings;
		  this.constants = constants;
		  this.messages  = messages;
		  this.fmtBig = new FormatBigNumbers(constants, messages);
		  this.width = width ;
		  if(width < 400){
			  dsPanel = new VerticalPanel();
		  }else{
			  dsPanel = new HorizontalPanel();
		  }
		  res = GlobalResources.INSTANCE;
		  res.globalCSS().ensureInjected();
		  portHeader2Members(portHeader);
		  onInitialize();
		  plusClosed.setTitle(constants.cwPlusClosedTooltip());
		  plusOpen.setTitle(constants.cwPlusOpenTooltip());
	}

	  protected Widget portHeader2Members(OverviewPortHeader portHeader) {
		  this.portName = portHeader.getPortName();
		  portHeader.getChangeAbsVal();
		  portHeader.getChangePercent();
		  this.portId = portHeader.getPortId();
		  this.cash = NumberFormat.getCurrencyFormat().format(portHeader.getCash());
		  this.mktValue = portHeader.getMktValue();
		  Widget w = formatPortHeader(portHeader.getPortName(), portHeader.getPortId(), portHeader.getChangeAbsVal(), portHeader.getChangePercent(), portHeader.getMktValue());
		  this.header = w;
		  return w;
	  }

	public String getPortId() {
		return portId;
	}

	/**
	   * Initialize this example.
	   */
	  public void onInitialize() {
	    // Add the disclosure panels to a panel
	    this.setSpacing(0);
	    disclosurePanel = createAdvancedForm();
	    table = new OverviewFlexTable(constants, messages,dockPanelSettings, width);
	    disclosurePanel.add(table);
	    disclosurePanel.setWidth(width+"px");
	    DecoratorPanel dpanel = new DecoratorPanel();
	    dpanel.add(disclosurePanel);
	    this.add(dpanel);

	    // Return the panel
	  }
	  
	  public void setOpenHandler(OpenHandler<DisclosurePanel> oHandler){
		  disclosurePanel.addOpenHandler(oHandler);
	  }
	  
	  public void setCloseHandler(CloseHandler<DisclosurePanel> cHandler){
		  disclosurePanel.addCloseHandler(cHandler);
	  }
	  
	  public void portPopulate(OverviewPortRow[] result){
		  if(result == null){
			  Log.warn("null in portPopulate!");
			  table.removeAllRows();
			  table.setText(0, 0, messages.cwNoPositionEntries(getPortName()));
		  }else{
			  for(OverviewPortRow row : result){
				  Log.debug("updating: " + row.toString());
				  table.updateRow(row,row.getRowNum());
			  }
		  }
	  }

	  /**
	   * Create a form that contains undisclosed advanced options.
	   */
	  private DisclosurePanel createAdvancedForm() {
		  advancedDisclosure = new DisclosurePanel();
		  advancedDisclosure.setAnimationEnabled(true);
		  advancedDisclosure.setHeader(header);
		  return advancedDisclosure;
	  }
	  
	public void updatePortTitle(OverviewPortHeader portHeader){
		  advancedDisclosure.setHeader(portHeader2Members(portHeader));
	  }


	public boolean isPortRetrieved() {
		return isPortRetrieved;
	}

	public void setPortRetrieved(boolean isPortRetrieved) {
		this.isPortRetrieved = isPortRetrieved;
	}

	public String getPortName() {
		return portName;
	}

	public String getUserId() {
		return userId;
	}

	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean b) {
		this.isClosed = b;
		if(b){
			setPlusClosed();
		}else{
			setPlusOpen();
		}
		
	}
	
	private void setPlusOpen(){
		headerTable.setWidget(0, 0, plusOpen);
	}
	
	private void setPlusClosed(){
		headerTable.setWidget(0, 0, plusClosed);
	}
}
