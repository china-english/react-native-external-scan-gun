
package com.afei.scangun;

import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class ScanGunModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String onScanCodeRecevieData  = "onScanCodeRecevieData";


  public ScanGunModule(ReactApplicationContext reactContext) {

    super(reactContext);
    this.reactContext = reactContext;
    ScanGunManager.getInstance().setReceiveCallback(new ScanCodeCallback() {
      @Override
      public void onScanCode(String value) {
        ScanGunModule.this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(onScanCodeRecevieData,value);
      }
    });

  }

  @Override
  public String getName() {
    return "scanGun";
  }

  // 清除上次记录的输入内容
  @ReactMethod
  public void clearScanBarCodeText(){
    ScanGunManager.getInstance().clearScanBarCodeText();
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("onScanCodeRecevieData", onScanCodeRecevieData);
    return constants;
  }

}