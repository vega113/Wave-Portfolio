package com.aggfi.portfolio.wave.client.format;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.portfolio.GlobalResources;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class FormatBigNumbers {
	public FormatBigNumbers(CwConstants constants, CwMessages messages) {
		super();
		this.constants = constants;
		this.messages = messages;
		res = GlobalResources.INSTANCE;
		res.globalCSS().ensureInjected();
	}
	
	protected GlobalResources res;
	CwConstants constants;
	CwMessages messages;
	private HTML changeAbsHtml = new HTML();
	private HTML changePercentHtml = new HTML();
	
	public String format(double bnum){
		try{
			String[] curMult = {"", constants.cwThousand(), constants.cwMillion(), constants.cwBillion()};
			int counter = 0;
			NumberFormat fmt = NumberFormat.getFormat("###.##");
			double curNumber = (double)bnum;
			while(curNumber/1000 > 999.99999){
				curNumber = curNumber/1000;
				counter++;
			}
			if(curNumber > 999.99999){
				curNumber = curNumber/1000;
				counter++;
			}
			return fmt.format(curNumber) + curMult[counter];
		}catch(Throwable t){
			Log.warn(t.getMessage());
			return bnum + "";
		}
	}
	
	public Widget formatChange(double changeAbsVal, double changePercent,CwMessages messages,Panel panel){
		NumberFormat nfmt1 = NumberFormat.getFormat("########.##");
		NumberFormat nfmt2 = NumberFormat.getFormat("###.##'%'");
		String sign1 = changeAbsVal < 0 ? "" : "+";
		String sign2 = changePercent < 0 ? "" : "+";
		
		String changeAbsValStr = sign1 + nfmt1.format(changeAbsVal);
		String changePercentStr = sign2 + nfmt2.format(changePercent*100);
		changeAbsHtml.setHTML(changeAbsValStr);
		changePercentHtml.setHTML("(" + changePercentStr + ")");
		
		if(changeAbsVal > 0){
			changeAbsHtml.setStyleName(res.globalCSS().upChange());
			changePercentHtml.setStyleName(res.globalCSS().upChange());
		}else if(changeAbsVal < 0){
			changeAbsHtml.setStyleName(res.globalCSS().downChange());
			changePercentHtml.setStyleName(res.globalCSS().downChange());
		}else {
			changeAbsHtml.setStyleName(res.globalCSS().noChange());
			changePercentHtml.setStyleName(res.globalCSS().noChange());
		}
		
		panel.add(changeAbsHtml);
		panel.add(changePercentHtml);
//		String headerStr = messages.cwChangeHeader(changeAbsValStr,changePercentStr);
		return panel;
		
	}
	
	public Widget formatChange(double changeAbsVal, double changePercent,CwMessages messages) {
		
		return formatChange(changeAbsVal, changePercent, messages, new VerticalPanel());
	}
}
