package com.aggfi.portfolio.wave.client.portfolio;

import java.util.HashMap;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.format.FormatBigNumbers;
import com.aggfi.portfolio.wave.client.portfolio.data.OverviewPortRow;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dev.util.msg.Formatter;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class OverviewFlexTable extends FlexTable {
	private CwConstants constants;
	private CwMessages messages;
	private FormatBigNumbers fmtBig;
	NumberFormat fmtDec2 = NumberFormat.getFormat("##,###,#00.00");
	NumberFormat fmtDec3 = NumberFormat.getFormat("##0.00");
	
	protected GlobalResources res;
	private HashMap<String, ColSettings> dockPanelSettings;
	
	public OverviewFlexTable(CwConstants cwConstants, CwMessages messages, HashMap<String, ColSettings> dockPanelSettings){
		this.constants = cwConstants;
		this.messages = messages;
		this.dockPanelSettings = dockPanelSettings;
		res = GlobalResources.INSTANCE;
		res.globalCSS().ensureInjected();
		this.setStylePrimaryName(res.globalCSS().waveList());
		this.getColumnFormatter().addStyleName(0, res.globalCSS().messageCol());
		this.getElement().setId("flexTable");
		fmtBig = new FormatBigNumbers(cwConstants, messages);
		initTable();
		
	}
	
	private void initTable(){
		//set spinning image while the data is loading...
		
		
		ColumnFormatter colFormatter = getColumnFormatter();
		try{
			if (dockPanelSettings.get("symbol").getColNum() > -1) {
				//			this.setText(0, 0, constants.cwName());
				Widget symbolW = new HTML(constants.cwSymbol());
				symbolW.setTitle(constants.cwSymbolTooltip());
				this.setWidget(0, dockPanelSettings.get("symbol").getColNum(), symbolW);
				//			colFormatter.setWidth(0, "16px");
				colFormatter.setWidth(dockPanelSettings.get("symbol").getColNum(),
						dockPanelSettings.get("symbol").getWidth() + "px");
			}
			
			if(dockPanelSettings.get("last").getColNum() > -1){
				this.setWidget(0, dockPanelSettings.get("last").getColNum(), new HTML(constants.cwLastPrice()));
//				colFormatter.setWidth(0, "10px");
				colFormatter.setWidth(dockPanelSettings.get("last").getColNum(), dockPanelSettings.get("last").getWidth() + "px");
			}
			
			if(dockPanelSettings.get("change").getColNum() > -1){
				Widget changeW = new HTML(constants.cwChange());
				changeW.setTitle(constants.cwStockChangeTooltip());
				this.setWidget(0, dockPanelSettings.get("change").getColNum(), changeW);
//				colFormatter.setWidth(0, "15px");
				colFormatter.setWidth(dockPanelSettings.get("change").getColNum(), dockPanelSettings.get("change").getWidth() + "px");
			}
			
			if(dockPanelSettings.get("volume").getColNum() > -1){
//				this.setText(0, col++, constants.cwMktCap());
				this.setText(0, dockPanelSettings.get("volume").getColNum(), constants.cwVolume());
//				colFormatter.setWidth(0, "15px");
				colFormatter.setWidth(dockPanelSettings.get("volume").getColNum(), dockPanelSettings.get("volume").getWidth() + "px");
				
			}
			
			if(dockPanelSettings.get("open").getColNum() > -1){
				this.setText(0, dockPanelSettings.get("open").getColNum(), constants.cwOpen());
//				colFormatter.setWidth(0, "11px");
				colFormatter.setWidth(dockPanelSettings.get("open").getColNum(), dockPanelSettings.get("open").getWidth() + "px");
			}
			
			if(dockPanelSettings.get("high").getColNum() > -1){
				this.setText(0, dockPanelSettings.get("high").getColNum(), constants.cwHigh());
//				colFormatter.setWidth(0, "11px");
				colFormatter.setWidth(dockPanelSettings.get("high").getColNum(), dockPanelSettings.get("high").getWidth() + "px");
			}
			
			if(dockPanelSettings.get("low").getColNum() > -1){
				this.setText(0, dockPanelSettings.get("low").getColNum(), constants.cwLow());
//				colFormatter.setWidth(0, "11px");
				colFormatter.setWidth(dockPanelSettings.get("low").getColNum(), dockPanelSettings.get("low").getWidth() + "px");
			}
			
			if(dockPanelSettings.get("gain").getColNum() > -1){
				this.setText(0, dockPanelSettings.get("gain").getColNum(), constants.cwDaysGain());
//				colFormatter.setWidth(0, "11px");
				colFormatter.setWidth(dockPanelSettings.get("gain").getColNum(), dockPanelSettings.get("gain").getWidth() + "px");
			}
			Widget spiningWidget = null;
			spiningWidget = new HTML("<div><img src='http://www.google.com/ig/images/spinner.gif'>" + constants.cwLoading() + "</div> ");
			this.setWidget(1, 0,spiningWidget );
			
			this.getRowFormatter().addStyleName(0, res.globalCSS().headerRow());
		
		}catch(Exception e){
			Log.error("", e);
		}
	}
	
	public void addRow(OverviewPortRow row){
		int rowsCount = getRowCount();
		updateRow(row, rowsCount);
		
	}

	
	public void updateRow(OverviewPortRow row, int rowsCount) {
		try{

			
			if(rowsCount%2 == 0){
				this.getRowFormatter().addStyleName(rowsCount, res.globalCSS().highlightRow());
			}else{
				this.getRowFormatter().addStyleName(rowsCount, res.globalCSS().regularRow());
			}
			
			FlexCellFormatter ff = this.getFlexCellFormatter();
			if (!row.isCashRow()) {
				Log.trace(dockPanelSettings.get("symbol").toString());
				//----------------------------row.getSymbol()
				if (dockPanelSettings.get("symbol").getColNum() > -1) {
					if (row.getSymbol() != null) {
						Anchor symbw = new Anchor(row.getSymbol());
						symbw.setHTML("<a href='#' onclick=window.open('http://www.google.com/finance?q=" + row.getExchange() + ":" + row.getSymbol() + "')>" +row.getSymbol() + "</a>");
//						symbw.setHref("http://www.google.com/finance?q=" + row.getExchange() + ":" + row.getSymbol());
						symbw.setTitle(row.getName());
						this.setWidget(rowsCount, dockPanelSettings.get("symbol").getColNum(), symbw);
					}
				}
				
				Log.trace(dockPanelSettings.get("last").toString());
				if(dockPanelSettings.get("last").getColNum() > -1){
					if (row.getLastPrice() != 0) {
						double pre = row.getPreLastPrice();
						double last = row.getLastPrice();
						//--------------- TODO REMOVE
						Log.info("in table last : " + row.getLastPrice());
						//-------------------
						//now find how many digits changed
						if(pre != last){
							Widget w =  lastPriceByChange(pre,last);
							this.setWidget(rowsCount, dockPanelSettings.get("last").getColNum(), w);
							if (row.getLastTradeTime() != null && !"".equals(row.getLastTradeTime())) {
								w.setTitle(messages.cwLastTradeTime(row.getLastTradeTime()));
							}
							OverviewPortRow.OverviewTimer t = row.getTimer();
							t.setF(this);
							Widget w2 = lastPriceByChange(last,last);
							w2.setStyleName(res.globalCSS().black());
							t.setLast(w2);
							t.schedule(constants.cwChangeHighlightDuration());
						}
					}else{
						Log.warn("No Last for: " + row.toString()); //FIXME - check why last no bigger than -1
					}
				}
				
				if(dockPanelSettings.get("change").getColNum() > -1){
					Widget changeWidget = null;
					if(getWidget(rowsCount, dockPanelSettings.get("change").getColNum()) == null){
						changeWidget = fmtBig.formatChange(row.getChangeAbsVal(), row.getChangePercent(), messages);
					}else{
						changeWidget = fmtBig.formatChange(row.getChangeAbsVal(), row.getChangePercent(), messages, (Panel)getWidget(rowsCount, dockPanelSettings.get("change").getColNum()));
					}
					if (changeWidget != null) {
						changeWidget.setTitle(constants.cwStockChangeTooltip());
						this.setWidget(rowsCount, dockPanelSettings.get("change").getColNum(), changeWidget);
						if(row.getChangeAbsVal() < 0){
							changeWidget.setStyleName( res.globalCSS().downChange());
						}else if(row.getChangeAbsVal() > 0){
							changeWidget.setStyleName(res.globalCSS().upChange());
						}else{
							changeWidget.setStyleName( res.globalCSS().noChange());
						}
					}
				}
				
				if(dockPanelSettings.get("volume").getColNum() > -1){
					if (row.getVolume() > -1) {
						this.setText(rowsCount, dockPanelSettings.get("volume").getColNum(), fmtBig.format(row.getVolume()));
					}
				}
				
				if(dockPanelSettings.get("open").getColNum() > -1){
					if (row.getOpen() > -1) {
						this.setText(rowsCount, dockPanelSettings.get("open").getColNum(), fmtDec2.format(row.getOpen()));
					}
				}
				
				if(dockPanelSettings.get("high").getColNum() > -1){
					if (row.getHigh() > -1) {
						this.setText(rowsCount, dockPanelSettings.get("high").getColNum(), fmtDec2.format(row.getHigh()));
					}
				}
				
				if(dockPanelSettings.get("low").getColNum() > -1){
					if (row.getLow() > -1) {
						this.setText(rowsCount, dockPanelSettings.get("low").getColNum(), fmtDec2.format(row.getLow()));
					}
				}
				
				if(dockPanelSettings.get("gain").getColNum() > -1){
					this.setText(rowsCount, dockPanelSettings.get("gain").getColNum(), fmtDec3.format(row.getDaysGain()));
				}
				
				//-----------------------
			}else{
				this.setText(rowsCount, 0, row.getSymbol());
				this.setText(rowsCount, 1, row.getCash());
				ff.setColSpan(rowsCount, 1, 2);
				this.getRowFormatter().addStyleName(rowsCount, res.globalCSS().cashRow());
			}
			Log.debug("exit updateRow");
		
		}catch(Throwable t){
			Log.error("update row: ", t);
		}
	}
	
	
	private Widget lastPriceByChange(double pre, double last) {
		//TODO - add feature - only the digits that differ from pre should change, not the whole price
		
//		String lastStr = fmtDec2.format(last);
		String lastStr = Double.toString(last);
		int count = 0;
		if(pre == 0){
			pre = last;
		}
		if(Math.abs(last - pre) > 0.01){
			count++;
		}
		if(Math.abs(last - pre) > 0.1){
			count++;
		}
//		String[] s1 = new String[2];
//		s1[0] = lastStr.substring(0,lastStr.length()-count);
//		s1[1] = lastStr.substring(lastStr.length()-count,lastStr.length());
//		HTML html1 = new HTML(s1[0]);
//		HTML html2 = new HTML(s1[1]);
		
		HTML html = new HTML(lastStr);
		if(last > pre){
			html.setStyleName(res.globalCSS().upChange());
		}else if(pre > last){
			html.setStyleName(res.globalCSS().downChange());
		}else{
			html.setStyleName(res.globalCSS().black());
		}
//		HorizontalPanel p = new HorizontalPanel();
//		p.add(html1);
//		p.add(html2);
		return html;
	}

	public void clear(){
		while(getRowCount() > 1){
			removeRow(getRowCount()-1);
		}
	}

	
}
