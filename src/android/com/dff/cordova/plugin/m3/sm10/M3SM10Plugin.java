package com.dff.cordova.plugin.m3.sm10;

import android.Manifest;
import android.content.pm.PackageManager;
import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import com.dff.cordova.plugin.m3.sm10.action.M3Action;
import com.dff.cordova.plugin.m3.sm10.action.ScanDispose;
import com.dff.cordova.plugin.m3.sm10.action.ScanStart;
import com.m3.sdk.scannerlib.Barcode;
import com.m3.sdk.scannerlib.BarcodeManager;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class M3SM10Plugin extends CommonPlugin {

    public static final String TAG = "com.dff.cordova.plugin.m3.sm10.M3SM10Plugin";
    private static final String PERMISSION = Manifest.permission.WRITE_SETTINGS;
    private static final int PERMISSION_CODE = 0;

    private HashMap<String, Class<? extends M3Action>> actions = new HashMap<String, Class<? extends M3Action>>();

    private Barcode mBarcode = null;
    private M3BarcodeListener mListener = null;
    private BarcodeManager mManager = null;

    public M3SM10Plugin() {
        super(TAG);

        this.actions.put(ScanDispose.ACTION_NAME, ScanDispose.class);
        this.actions.put(ScanStart.ACTION_NAME, ScanStart.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!cordova.hasPermission(PERMISSION)) {
            getReadAndWritePermission(PERMISSION_CODE);
        }
    }

    private void getReadAndWritePermission(int requestCode) {
        cordova.requestPermission(this, requestCode, PERMISSION);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException {
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                CordovaPluginLog.e(TAG, "WRITE SETTINGS - PERMISSIONS DENIED");
                return;
            }
        }
    }

    /**
     * Called after plugin construction and fields have been initialized.
     */
    @Override
    public void pluginInitialize() {
        super.pluginInitialize();

        this.mBarcode = new Barcode(this.cordova.getActivity());
        this.mManager = new BarcodeManager(this.cordova.getActivity());
        this.mBarcode.setScanner(true);

        this.mListener = new M3BarcodeListener();
        this.mManager.addListener(this.mListener);
    }

    /**
     * The final call you receive before your activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mManager.removeListener(this.mListener);
        this.mManager.dismiss();
        this.mBarcode.setScanner(false);
    }

    /**
     * Executes the request.
     * <p>
     * This method is called from the WebView thread. To do a non-trivial amount
     * of work, use: cordova.getThreadPool().execute(runnable);
     * <p>
     * To run on the UI thread, use:
     * cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return Whether the action was valid.
     */
    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext)
        throws JSONException {

        CordovaPluginLog.i(TAG, "call for action: " + action + "; args: " + args);

        if (action.equals("onBarcode")) {
            if (this.mListener != null) {
                this.mListener.setCallBack(callbackContext);
            } else {
                LOG.e(TAG, "barcode listener not initialized");
            }

            return true;
        }

        CordovaAction cordovaAction = null;

        if (this.actions.containsKey(action)) {
            Class<? extends M3Action> actionClass = this.actions.get(action);

            CordovaPluginLog.d(TAG, "found action: " + actionClass.getName());

            try {
                cordovaAction = actionClass.getConstructor(
                    String.class,
                    JSONArray.class,
                    CallbackContext.class,
                    CordovaInterface.class,
                    Barcode.class).newInstance(action, args, callbackContext, this.cordova, this.mBarcode);
            } catch (InstantiationException e) {
                CordovaPluginLog.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                CordovaPluginLog.e(TAG, e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                CordovaPluginLog.e(TAG, e.getMessage(), e);
            } catch (InvocationTargetException e) {
                CordovaPluginLog.e(TAG, e.getMessage(), e);
            } catch (NoSuchMethodException e) {
                CordovaPluginLog.e(TAG, e.getMessage(), e);
            } catch (SecurityException e) {
                CordovaPluginLog.e(TAG, e.getMessage(), e);
            }
        }

        if (cordovaAction != null) {
            this.cordova.getThreadPool().execute(cordovaAction);
            return true;
        }

        return super.execute(action, args, callbackContext);
    }
}
