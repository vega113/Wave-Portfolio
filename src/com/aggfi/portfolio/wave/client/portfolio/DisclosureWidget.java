package com.aggfi.portfolio.wave.client.portfolio;


import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.format.FormatBigNumbers;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.aggfi.portfolio.wave.client.portfolio.OverviewTableTemplate;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

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
	private OverviewTableTemplate table = null;
	
	private double mktValue;
	private boolean isClosed = true;
	private DisclosurePanel advancedDisclosure;
	/**
	 * this day 'gain'
	 */
	private double changeAbsVal;
	/**
	 * this day change or gain in per cent
	 */
	private double changePercent;
	private String header;
	
	private FormatBigNumbers fmtBig;
	private String cash;
	public String getCash() {
		return cash;
	}

	
	
	private String formatPortHeader(String name, String portId, double changeAbsVal, double changePercent, double mktValue){
		String headerStr = fmtBig.formatChange(changeAbsVal, changePercent, messages);
		return messages.cwOverviewPortHeader(name, headerStr, NumberFormat.getCurrencyFormat().format(mktValue));
	}

	  public DisclosureWidget(CwConstants constants, CwMessages messages, OverviewPortHeader portHeader) {
		  super();
		  this.constants = constants;
		  this.messages  = messages;
		  this.fmtBig = new FormatBigNumbers(constants, messages);
		  portHeader2Members(portHeader);
		  onInitialize();
	}

	  protected void portHeader2Members(OverviewPortHeader portHeader) {
		  this.portName = portHeader.getPortName();
		  this.changeAbsVal = portHeader.getChangeAbsVal();
		  this.changePercent = portHeader.getChangePercent();
		  this.portId = portHeader.getPortId();
		  this.cash = NumberFormat.getCurrencyFormat().format(portHeader.getCash());
		  this.mktValue = portHeader.getMktValue();
		  this.header = formatPortHeader(portName, portId, changeAbsVal, changePercent, mktValue);
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
	    table = new OverviewTableTemplate(constants, messages);
	    disclosurePanel.add(table);
	    disclosurePanel.setWidth(constants.cwDW_WIDTH());
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
			  table.removeAllRows();
			  table.setText(0, 0, messages.cwNoPositionEntries(getPortName()));
		  }else{
			  for(OverviewPortRow row : result){
				  Log.trace("updating: " + row.toString());
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
		  advancedDisclosure.setHeader(new HTML(("<div>" + header + " Init!" + "</div>")));
		  return advancedDisclosure;
	  }
	  
	  public void updatePortTitle(OverviewPortHeader portHeader){
		  portHeader2Members(portHeader);
		  advancedDisclosure.setHeader(new HTML(("<div>" + header + " Updated!" + "</div>")));
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
		
	}
}
