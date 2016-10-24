package com.dff.cordova.plugin.m3.sm10.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;

import com.m3.sdk.scannerlib.Barcode;

public class ScanDispose extends M3Action {
	public static final String ACTION_NAME = "scanDispose";

	public ScanDispose(String action, JSONArray args, CallbackContext callbackContext, CordovaInterface cordova,
	        Barcode barcode) {
		super(action, args, callbackContext, cordova, barcode);
	}

	@Override
	public void run() {
		super.run();

		this.barcode.scanDispose();
		this.callbackContext.success();
	}
}
