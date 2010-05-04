package com.aggfi.portfolio.wave.client.portfolio;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.format.FormatBigNumbers;
import com.google.gwt.dev.util.msg.Formatter;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;

public class OverviewTableTemplate extends FlexTable {
	private CwConstants constants;
	private CwMessages messages;
	private FormatBigNumbers fmtBig;
	
	public OverviewTableTemplate(CwConstants cwConstants, CwMessages messages){
		this.constants = cwConstants;
		this.messages = messages;
		fmtBig = new FormatBigNumbers(cwConstants, messages);
		initTable();
		
	}
	
	private void initTable(){
		CellFormatter formatter = getCellFormatter();
		
		this.setText(0, 0, constants.cwName());
		this.setText(0, 1, constants.cwSymbol());
		this.setText(0, 2, constants.cwLastPrice());
		this.setText(0, 3, constants.cwChange());
		this.setText(0, 4, constants.cwMktCap());
		this.setText(0, 5, constants.cwVolume());
		this.setText(0, 6, constants.cwOpen());
		this.setText(0, 7, constants.cwHigh());
		this.setText(0, 8, constants.cwLow());
		this.setText(0, 9, constants.cwDaysGain());
	}
	
	public void addRow(OverviewPortRow row){
		int rowsCount = getRowCount();
		NumberFormat fmtDec2 = NumberFormat.getFormat("########.##");
		NumberFormat fmtDec3 = NumberFormat.getFormat("###.##");
		

		
		String changeStr = fmtBig.formatChange(row.getChangeAbsVal(), row.getChangePercent(), messages);
		
		if (!row.isCashRow()) {
			this.setText(rowsCount, 0, row.getName());
			this.setText(rowsCount, 1, row.getSymbol());
			this.setText(rowsCount, 2, fmtDec2.format(row.getLastPrice()));
			this.setText(rowsCount, 3, changeStr);
			this.setText(rowsCount, 4, fmtBig.format(row.getMktCap()));
			this.setText(rowsCount, 5, fmtBig.format(row.getVolume()));
			this.setText(rowsCount, 6, fmtDec2.format(row.getOpen()));
			this.setText(rowsCount, 7, fmtDec2.format(row.getHigh()));
			this.setText(rowsCount, 8, fmtDec2.format(row.getLow()));
			this.setText(rowsCount, 9, String.valueOf(row.getDaysGain()));
		}else{
			NumberFormat fmtCur = NumberFormat.getCurrencyFormat(row.getCurrencyCode());
			this.setText(rowsCount, 0, row.getName());
			this.setText(rowsCount, 2, fmtCur.format(row.getLastPrice()));
		}
		
	}
	
	
	public void clear(){
		int rowsCount = getRowCount();
		while(getRowCount() > 1){
			removeRow(getRowCount()-1);
		}
	}
	
	
}
