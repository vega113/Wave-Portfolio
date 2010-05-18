package com.aggfi.portfolio.wave.client.format;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.google.gwt.i18n.client.NumberFormat;


public class FormatBigNumbers {
	public FormatBigNumbers(CwConstants constants, CwMessages messages) {
		super();
		this.constants = constants;
		this.messages = messages;
	}
	CwConstants constants;
	CwMessages messages;
	
	public String format(double bnum){
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
	}
	
	public String formatChange(double changeAbsVal, double changePercent,
			CwMessages messages) {
		NumberFormat nfmt1 = NumberFormat.getFormat("########.##");
		NumberFormat nfmt2 = NumberFormat.getFormat("###.##'%'");
		String sign1 = changeAbsVal < 0 ? "" : "+";
		String sign2 = changePercent < 0 ? "" : "+";
		
		String changeAbsValStr = sign1 + nfmt1.format(changeAbsVal);
		String changePercentStr = sign2 + nfmt2.format(changePercent*100);
		String headerStr = messages.cwChangeHeader(changeAbsValStr,changePercentStr);
		return headerStr;
	}
}
