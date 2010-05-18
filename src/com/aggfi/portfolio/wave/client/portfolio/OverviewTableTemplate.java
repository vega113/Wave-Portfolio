package com.aggfi.portfolio.wave.client.portfolio;

import com.aggfi.portfolio.wave.client.WavePortfolio.CwConstants;
import com.aggfi.portfolio.wave.client.WavePortfolio.CwMessages;
import com.aggfi.portfolio.wave.client.format.FormatBigNumbers;
import com.aggfi.portfolio.wave.client.portfolio.data.IOverviewRow;
import com.allen_sauer.gwt.log.client.Log;
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
//		CellFormatter formatter = getCellFormatter();
		int col = 0;
//		this.setText(0, 0, constants.cwName());
		this.setText(0, 0, constants.cwSymbol());
		this.setText(0, 1, constants.cwLastPrice());
		this.setText(0, 2, constants.cwChange());
//		this.setText(0, col++, constants.cwMktCap());
		this.setText(0, 3, constants.cwVolume());
		this.setText(0, 4, constants.cwOpen());
		this.setText(0, 5, constants.cwHigh());
		this.setText(0, 6, constants.cwLow());
		this.setText(0, 7, constants.cwDaysGain());
	}
	
	public void addRow(IOverviewRow row){
		int rowsCount = getRowCount();
		updateRow(row, rowsCount);
		
	}

	public void updateRow(IOverviewRow row, int rowsCount) {
		NumberFormat fmtDec2 = NumberFormat.getFormat("######00.00");
		NumberFormat fmtDec3 = NumberFormat.getFormat("##0.00");
		

		
		String changeStr = fmtBig.formatChange(row.getChangeAbsVal(), row.getChangePercent(), messages);
		int col = 0;
		if (!row.isCashRow()) {
			if (row.getName() != null) {
//				this.setText(rowsCount, col, row.getName());
			}
			if (row.getSymbol() != null) {
				this.setText(rowsCount, 0, row.getSymbol());
			}
			if (row.getLastPrice() > -1) {
				this.setText(rowsCount, 1, fmtDec2.format(row.getLastPrice()));
			}
			if (changeStr != null) {
				this.setText(rowsCount, 2, changeStr);
			}
//			if (row.getMktCap() > -1) {
//				this.setText(rowsCount, col++, fmtBig.format(row.getMktCap()));
//			}
			if (row.getVolume() > -1) {
				this.setText(rowsCount, 3, fmtBig.format(row.getVolume()));
				Log.debug("row.getVolume: " + row.getVolume() + ", fmtBig: " +fmtBig.format(row.getVolume()) );
			}
			if (row.getOpen() > -1) {
				this.setText(rowsCount, 4, fmtDec2.format(row.getOpen()));
			}
			if (row.getHigh() > -1) {
				this.setText(rowsCount, 5, fmtDec2.format(row.getHigh()));
			}
			if (row.getLow() > -1) {
				this.setText(rowsCount, 6, fmtDec2.format(row.getLow()));
			}
			this.setText(rowsCount, 7, fmtDec3.format(row.getDaysGain()));
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
