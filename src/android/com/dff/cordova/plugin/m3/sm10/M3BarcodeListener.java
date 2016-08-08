package com.dff.cordova.plugin.m3.sm10;

import org.json.JSONException;
import org.json.JSONObject;

import com.dff.cordova.plugin.common.AbstractPluginListener;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import com.m3.sdk.scannerlib.BarcodeListener;

public class M3BarcodeListener extends AbstractPluginListener implements BarcodeListener {
	public static final String LOG_TAG = "com.dff.cordova.plugin.m3.sm10.M3BarcodeListener"; 

	@Override
	public void onBarcode(String barcode) {
		CordovaPluginLog.d(LOG_TAG, "onBarcode:" + barcode);
		
		if (this.callback != null) {
			this.sendPluginResult(barcode);
		}
	}

	@Override
	public void onGetSymbology(int symbol, int val) {
		CordovaPluginLog.d(LOG_TAG, "onGetSymbology:" + symbol + ", " + val);
		
		if (this.callback != null) {
			try {
				JSONObject jsonSymbology = new JSONObject();
				jsonSymbology.put("symbol", symbol);
				jsonSymbology.put("val", val);
				
				this.sendPluginResult(symbol);
			}
			catch (JSONException je) {
				this.sendPluginResult(je);
			}			
		}
	}

}
