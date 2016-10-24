package com.dff.cordova.plugin.m3.sm10.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;

import com.dff.cordova.plugin.common.action.CordovaAction;
import com.m3.sdk.scannerlib.Barcode;

public abstract class M3Action extends CordovaAction {
	private static String TAG = "com.dff.cordova.plugin.m3.sm10.action.M3Action";

	protected Barcode barcode;

	public M3Action(String action, JSONArray args, CallbackContext callbackContext, CordovaInterface cordova,
	        Barcode barcode) {
		super(action, args, callbackContext, cordova);
		this.barcode = barcode;
	}

}
