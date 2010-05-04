package com.aggfi.portfolio.wave.client;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.format.FormatBigNumbers;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortHeader;
import com.aggfi.portfolio.wave.client.portfolio.OverviewPortRow;
import com.aggfi.portfolio.wave.client.portfolio.OverviewTableTemplate;
//import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;

public class DisclosureWidget extends VerticalPanel {
	
	  private static final String DW_WIDTH = "550px";
	/**
	   * An instance of the constants.
	   */
	private CwConstants constants = null;
	private CwMessages messages = null;
	protected String portName = "";
	private String userId = "";
	protected DisclosurePanel disclosurePanel;
	private boolean isPortRetrieved = false;
	private OverviewTableTemplate table = null;
	private double changeAbsVal;
	private double changePercent;
	private String header;
	private double gain;
	
	private FormatBigNumbers fmtBig;
	
	private String formatPortHeader(String name, double changeAbsVal, double changePercent, double gain, CwConstants constants, CwMessages messages ){
		String headerStr = fmtBig.formatChange(changeAbsVal, changePercent, messages);
		NumberFormat nfmt3 = NumberFormat.getFormat("########.##");
		String sign3 = gain < 0 ? "" : "+";
		String gainStr = sign3 + nfmt3.format(gain);
		return messages.cwOverviewPortHeader(name, headerStr);
	}

	  public DisclosureWidget(CwConstants constants, CwMessages messages, String userId, OverviewPortHeader portHeader) {
		  super();
		  this.constants = constants;
		  this.messages  = messages;
		  this.fmtBig = new FormatBigNumbers(constants, messages);
		  this.userId = userId;
		  this.portName = portHeader.getPortName();
		  this.changeAbsVal = portHeader.getChangeAbsVal();
		  this.changePercent = portHeader.getChangePercent();
		  this.gain = portHeader.getGain();
		  this.header = formatPortHeader(portName, changeAbsVal, changePercent,gain, constants, messages);
		  onInitialize();
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
	    disclosurePanel.setWidth(DW_WIDTH);
	    DecoratorPanel dpanel = new DecoratorPanel();
	    dpanel.add(disclosurePanel);
	    this.add(dpanel);

	    // Return the panel
	  }
	  
	  public void setOpenHandler(OpenHandler<DisclosurePanel> oHandler){
		  disclosurePanel.addOpenHandler(oHandler);
	  }
	  
	  public void portPopulate(OverviewPortRow[] result){
		  	table.clear();
			for(OverviewPortRow row : result){
				table.addRow(row);
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


}
