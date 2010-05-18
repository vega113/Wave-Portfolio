package com.aggfi.portfolio.wave.client.portfolio.data;

import com.aggfi.portfolio.wave.client.finance.feature.Data;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IAbstractRow {

	public abstract String getName();

	public abstract String getSymbol();

	public abstract int getRowNum();

	public abstract String getStockId();
	
	public abstract AsyncCallback<Data> getCallback();

}