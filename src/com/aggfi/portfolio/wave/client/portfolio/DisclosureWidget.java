package com.aggfi.portfolio.wave.client.portfolio;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.format.FormatBigNumbers;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.aggfi.portfolio.wave.client.portfolio.OverviewTableTemplate;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
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

	private double mktValue;
	private boolean isClosed;
	
	private String formatPortHeader(String name, String portId, double changeAbsVal, double changePercent, double mktValue, CwConstants constants, CwMessages messages ){
		String headerStr = fmtBig.formatChange(changeAbsVal, changePercent, messages);
		return messages.cwOverviewPortHeader(name, headerStr, NumberFormat.getCurrencyFormat().format(mktValue));
	}

	  public DisclosureWidget(CwConstants constants, CwMessages messages, OverviewPortHeader portHeader) {
		  super();
		  this.constants = constants;
		  this.messages  = messages;
		  this.fmtBig = new FormatBigNumbers(constants, messages);
		  this.portName = portHeader.getPortName();
		  this.changeAbsVal = portHeader.getChangeAbsVal();
		  this.changePercent = portHeader.getChangePercent();
		  this.portId = portHeader.getPortId();
		  this.cash = NumberFormat.getCurrencyFormat().format(portHeader.getCash());
		  this.mktValue = portHeader.getMktValue();
		  this.header = formatPortHeader(portName, portId, changeAbsVal, changePercent, mktValue, constants, messages);
		  onInitialize();
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
//		  	table.clear();
			for(OverviewPortRow row : result){
				if(table.getRowCount() < row.getRowNum()){
					table.addRow(row);
				}else{
					table.updateRow(row,row.getRowNum());
				}
			}
			disclosurePanel.setContent(table);
	  }

	  /**
	   * Create a form that contains undisclosed advanced options.
	   */
	  private DisclosurePanel createAdvancedForm() {
	    // Create a table to layout the form options
	    
//	    layout.setTitle("Blah");

//	    // Add a title to the form
//	    layout.setHTML(0, 0, constants.cwDisclosurePanelFormTitle());
//	    cellFormatter.setColSpan(0, 0, 2);
//	    cellFormatter.setHorizontalAlignment(0, 0,
//	        HasHorizontalAlignment.ALIGN_CENTER);
//
//	    // Add some standard form options
//	    layout.setHTML(1, 0, constants.cwDisclosurePanelFormName());
//	    layout.setWidget(1, 1, new TextBox());
//	    layout.setHTML(2, 0, constants.cwDisclosurePanelFormDescription());
//	    layout.setWidget(2, 1, new TextBox());

	   

	    // Add advanced options to form in a disclosure panel
	    
	   
		  DisclosurePanel advancedDisclosure = new DisclosurePanel(header) ;
		  advancedDisclosure.setAnimationEnabled(true);
		  return advancedDisclosure;
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
