# React native External equipment (code scanning gun, keyboard, etc.)

> This plug-in has only tested the external USB code scanning gun. If other external devices are abnormal, you can raise the issue in this project

### Integrated installation (Android only)

  1. Open `android/app/src/main/java/[...]/MainApplication.java` 
  - The plug-in package is introduced at the top of the file, including the code `import com.afei.scangun.ScanGunPackage;`
  2. Open `android/app/src/main/java/[...]/MainActivity.java` , and add the following code
  ```
  ...
  import android.view.KeyEvent;
  ...
  import com.afei.scangun.ScanGunManager;
  ...

  public class MainActivity extends ReactActivity {
    ...

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            ScanGunManager.getInstance().analysisKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    ...
  }
  ```

### Usage

```
...
import scanGun from 'react-native-external-scan-gun';
...
    useEffect(() => {
      const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample);
      const eventListener = eventEmitter.addListener(
        scanGun.onScanCodeRecevieData,
        code => {
          // TODO: Callback after code scanning
        },
      );
      return () => {
        // Remove listening event
        eventListener.remove();
      };
    }, []);
...
```

### Note
The above links are based on the project after expo eject. Please refer to the tutorial on [react native](https://reactnative.dev/blog/2019/07/03/version-60#native-modules-are-now-autolinked) when linking native projects.