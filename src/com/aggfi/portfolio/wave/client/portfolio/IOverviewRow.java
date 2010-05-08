package com.aggfi.portfolio.wave.client.portfolio;

public interface IOverviewRow  extends IAbstractRow{

	public abstract double getLastPrice();

	public abstract double getChangeAbsVal();

	public abstract double getChangePercent();

	public abstract long getMktCap();

	public abstract long getVolume();

	public abstract double getOpen();

	public abstract double getHigh();

	public abstract double getLow();

	public abstract double getDaysGain();

	public abstract String getCurrencyCode();

	public abstract boolean isCashRow();

}