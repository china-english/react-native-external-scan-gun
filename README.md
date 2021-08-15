# React native 外接设备(扫码枪、键盘等)

> 本插件仅测试过外接 USB 扫码枪，其他外接设备若不正常可以在本项目中提 issue

### 下载

`npm install react-native-external-scan-gun`
或者
`yarn add react-native-external-scan-gun`

### 集成安装(仅支持安卓)

  1. 打开 `android/app/src/main/java/[...]/MainApplication.java` 文件, 并在文件顶部引入插件包，代码 `import com.afei.scangun.ScanGunPackage;`
  2. 打开 `android/app/src/main/java/[...]/MainActivity.java`  文件, 并添加如下代码
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
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK && event.getDeviceId() != -1) {
            ScanGunManager.getInstance().analysisKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    ...
  }
  ```

### 用法

```
...
import scanGun from 'react-native-external-scan-gun';
...
    useEffect(() => {
      const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample);
      const eventListener = eventEmitter.addListener(
        scanGun.onScanCodeRecevieData,
        code => {
          // TODO: 扫码之后的回调
        },
      );
      return () => {
        // 移除监听事件
        eventListener.remove();
      };
    }, []);
...
```

### 备注

以上是基于 expo eject 项目为基础的链接方式，原生项目链接时请参考 [React Native](https://reactnative.dev/blog/2019/07/03/version-60#native-modules-are-now-autolinked)