package com.dff.cordova.plugin.m3.sm10;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import com.m3.sdk.scannerlib.Barcode;
import com.m3.sdk.scannerlib.BarcodeManager;

public class M3SM10Plugin extends CommonPlugin {
	public static final String LOG_TAG = "com.dff.cordova.plugin.m3.sm10.M3SM10Plugin";
	
	private Barcode mBarcode = null;
	private M3BarcodeListener mListener = null;
	private BarcodeManager mManager = null;
	
	public M3SM10Plugin() {
		super(LOG_TAG);
	}
	
   /**
	* Called after plugin construction and fields have been initialized.
	*/
	@Override
	public void pluginInitialize() {
		super.pluginInitialize();
		
		mBarcode = new Barcode(this.cordova.getActivity());
		mManager = new BarcodeManager(this.cordova.getActivity());
		mBarcode.setScanner(true);
		
		mListener = new M3BarcodeListener();
		mManager.addListener(mListener);
	}
	
    /**
     * The final call you receive before your activity is destroyed.
     */
	@Override
	public void onDestroy() {		
		super.onDestroy();
		mManager.removeListener(mListener);
		mManager.dismiss();
		mBarcode.setScanner(false);
	}

    /**
     * Executes the request.
     *
     * This method is called from the WebView thread.
     * To do a non-trivial amount of work, use:
     * cordova.getThreadPool().execute(runnable);
     *
     * To run on the UI thread, use:
     * cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action The action to execute.
     * @param args The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return Whether the action was valid.
     */
 	@Override
     public boolean execute(String action
     		, final JSONArray args
     		, final CallbackContext callbackContext)
         throws JSONException {
 		
     	CordovaPluginLog.i(LOG_TAG, "call for action: " + action + "; args: " + args);
     	
     	if (action.equals("onBarcode")) {
     		if (mListener != null) {
     			mListener.setCallBack(callbackContext);     			
     		}
     		else {
     			LOG.e(LOG_TAG, "barcode listener not initialized");
     		}
     		
     		return true;
     	}
     	
     	return super.execute(action, args, callbackContext);
     }
}
