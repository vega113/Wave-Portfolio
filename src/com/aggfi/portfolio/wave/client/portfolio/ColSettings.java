package com.aggfi.portfolio.wave.client.portfolio;

public class ColSettings {
	private String colName;
	private int width;
	private int colNum;
	
	public ColSettings(String colName, int width, int colNum) {
		super();
		this.colName = colName;
		this.width = width;
		this.colNum = colNum;
	}
	@Override
	public String toString() {
		return "ColSettings [colName=" + colName + ", colNum=" + colNum
				+ ", width=" + width + "]";
	}
	public int getColNum() {
		return colNum;
	}
	public String getColName() {
		return colName;
	}
	public int getWidth() {
		return width;
	}
}
