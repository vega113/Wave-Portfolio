package com.aggfi.portfolio.wave.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReadStocks {
	public static void main(String[] args) {
		List<String> stocksList = new ArrayList<String>();
		stocksList.add("DOX");
		stocksList.add("GOOG");
		stocksList.add("ORCL");
		stocksList.add("GOOG");
		stocksList.add("GOOG");
		stocksList.add("GOOG");
		stocksList.add("GOOG");
		stocksList.add("GOOG");
		stocksList.add("GOOG");
		stocksList.add("ORCL");
		ReadStocks rs = new ReadStocks();
		List<HashMap<String, Object>> res = null;
		try {
			res = rs.retrStocksPrice(stocksList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (res != null && res.size() > 0) {
			for (HashMap<String, Object> stocks : res) {
				String fmtStr = "symbol: {0}, last quote: {1}, trade time: {2}, quote before last: {3}, trade time: {4}";
				String[] resStrs = new String[5];
				resStrs[0] = (String) stocks.get("symbol");
				resStrs[1] = (String) stocks.get("l_cur");
				resStrs[2] = (String) stocks.get("lt");
				resStrs[3] = (String) stocks.get("el_cur");
				resStrs[4] = (String) stocks.get("elt");
				String finalOutStr = MessageFormat.format(fmtStr, resStrs);
				System.out.println(finalOutStr);
			}
		}
	}
	public List<HashMap<String,Object>> retrStocksPrice(List<String>  stocksList) throws IOException{
		//		String urlFmt = "http://www.google.com/finance?q={0}&output=csv";
		String urlFmt = "http://www.google.com/finance/info?client=ig&q={0}";
		List<HashMap<String,Object>> quotesList = new ArrayList<HashMap<String,Object>> ();
		for(String symbol : stocksList){
			Object[] objArr = {symbol};
			String urlStr = MessageFormat.format(urlFmt, objArr);
			URL url = null;
			url = new URL(urlStr);
			boolean isRead = false;
			int retry = 2;
			HashMap<String,Object> symbHM = new HashMap<String, Object>();
			StringBuffer sb = new StringBuffer();
			
			while(!isRead && retry > 0){
				InputStream is = url.openStream();
				InputStreamReader isr    = new java.io.InputStreamReader(is);
				BufferedReader br      = new java.io.BufferedReader(isr);
				while (br.ready()){
					sb.append(br.readLine());
				}
				if(sb.length() > 0){
					isRead = true;
				}
				retry--;
			}
			String[] infoPtrnStrs = {"\"l_cur\" : \"","\"lt\" : \"","\"el_cur\": \"","\"elt\" : \""};
			String[] infoStrs = {"l_cur","lt","el_cur","elt"};
			// now search for last price
			String responseStr = sb.toString();
			if(responseStr.length() == 0){
				int counter = 0;
				for(String strInfo : infoStrs){
					String curInfo = "NaN";
					symbHM.put(infoStrs[counter],curInfo);
					counter++;
				}	
			}else{
				

				int counter = 0;
				for(String strInfo : infoPtrnStrs){
					String curInfo = extrctInfo(responseStr,strInfo);
					symbHM.put(infoStrs[counter],curInfo);
					counter++;
				}		
			}
			symbHM.put("symbol",symbol);
			quotesList.add(symbHM);

		}
		return quotesList;
	}
	private String extrctInfo(String responseStr, String strInfo) {
		int ind = responseStr.indexOf(strInfo) + strInfo.length();
		String tmp1 = responseStr.substring(ind,responseStr.length());
		String info = tmp1.split("\"")[0];
		return info;
	}
}



