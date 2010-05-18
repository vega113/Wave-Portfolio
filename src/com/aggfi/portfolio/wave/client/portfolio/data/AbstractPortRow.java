package com.aggfi.portfolio.wave.client.portfolio.data;

import java.io.Serializable;


public abstract class AbstractPortRow implements Serializable, IAbstractRow {
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IAbstractRow#getName()
	 */
	public String getName() {
		return this.name;
	}
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IAbstractRow#getSymbol()
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IAbstractRow#getRowNum()
	 */
	public int getRowNum() {
		return this.rowNum;
	}
	
	/* (non-Javadoc)
	 * @see com.aggfi.portfolio.wave.client.portfolio.IAbstractRow#getStockId()
	 */
	public String getStockId() {
		return stockId;
	}

	protected String name;
	protected String symbol;
	
	private int rowNum;
	private String stockId;
	
	public void initOverviewPortRow(String name, String symbol, int rowNum, String stockId) {
		
		
		this.name = name;
		this.symbol = symbol;
		this.rowNum = rowNum;
		this.stockId = stockId;
	}
}
