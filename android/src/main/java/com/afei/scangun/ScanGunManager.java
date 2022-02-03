package com.afei.scangun;

import android.util.Log;
import android.view.KeyEvent;
import static android.content.ContentValues.TAG;

public class ScanGunManager {

    private final static long MESSAGE_DELAY = 500; // 延迟500ms，判断扫码是否完成。
    private StringBuffer stringBufferResult; // 扫码内容

    private ScanCodeCallback scanCodeCallback;

    public interface OnScanCodeListener {
        void onScanCode(String value);
    }

    // 类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static ScanGunManager instance;

    // 构造器私有化
    private ScanGunManager(){}

    // 限制线程同时访问 jvm 中该类的所有实例同时访问对应的代码块
    public static synchronized ScanGunManager getInstance(){
        if (instance == null) {
            instance=new ScanGunManager();
            instance.stringBufferResult = new StringBuffer();
        }
        return instance;
    }

    public void setReceiveCallback(ScanCodeCallback scanCodeCallback) {
        this.scanCodeCallback = scanCodeCallback;
    }

    public void clearScanBarCodeText(){
        if (stringBufferResult != null){
            stringBufferResult.delete(0, stringBufferResult.length());
        }
    }

    // 外设事件解析
    public void analysisKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        boolean isShiftPressed = event.isShiftPressed();
        // 对按下的键进行操作,过滤 shift
        if (
            event.getAction() == KeyEvent.ACTION_DOWN
            && keyCode != KeyEvent.META_SHIFT_ON
            && keyCode != KeyEvent.KEYCODE_SHIFT_LEFT
            && keyCode != KeyEvent.KEYCODE_SHIFT_RIGHT
        ) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // 输入结束,返回字符串
                String barCode = stringBufferResult.toString();
                if (scanCodeCallback != null) {
                    scanCodeCallback.onScanCode(barCode);
                }
                stringBufferResult.delete(0, stringBufferResult.length());
            } else {
                // 处理输入的字符串
                Log.d(TAG, "event:" + event + "\nkeyCode:" + event.getKeyCode() + "\nisShiftPressed:" + event.isShiftPressed());
                char inputCode = getInputCode(keyCode, isShiftPressed);;
                if (inputCode != 0) {
                    if (stringBufferResult != null){
                        stringBufferResult.append(inputCode);
                    }
                }
            }
        }
    }

    //获取扫描内容
    private char getInputCode(int keyCode, boolean isShiftPressed) {
        char inputCode;

        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            // 大小写字母
            inputCode = (char) ((isShiftPressed ? 'A' : 'a') + keyCode - KeyEvent.KEYCODE_A);
        } else {
            // 数字及其他符号(限英文字符)
            inputCode = keyValue(keyCode, isShiftPressed);
        }
        Log.d(TAG, "result keyCode:" + inputCode + "keyCode=" +keyCode);

        return inputCode;
    }

    /**
     * 按键对应的char表
     */
    private char keyValue(int keyCode, boolean isShiftPressed) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                return isShiftPressed ? ')' : '0';
            case KeyEvent.KEYCODE_1:
                return isShiftPressed ? '!' : '1';
            case KeyEvent.KEYCODE_2:
                return isShiftPressed ? '@' : '2';
            case KeyEvent.KEYCODE_3:
                return isShiftPressed ? '#' : '3';
            case KeyEvent.KEYCODE_4:
                return isShiftPressed ? '$' : '4';
            case KeyEvent.KEYCODE_5:
                return isShiftPressed ? '%' : '5';
            case KeyEvent.KEYCODE_6:
                return isShiftPressed ? '^' : '6';
            case KeyEvent.KEYCODE_7:
                return isShiftPressed ? '&' : '7';
            case KeyEvent.KEYCODE_8:
                return isShiftPressed ? '*' : '8';
            case KeyEvent.KEYCODE_9:
                return isShiftPressed ? '(' : '9';
            case KeyEvent.KEYCODE_NUMPAD_SUBTRACT:
                return isShiftPressed ? '_' : '-';
            case KeyEvent.KEYCODE_MINUS:
                return isShiftPressed ? '_' : '-';
            case KeyEvent.KEYCODE_EQUALS:
                return isShiftPressed ? '+' : '=';
            case KeyEvent.KEYCODE_NUMPAD_ADD:
                return isShiftPressed ? '+' : '=';
            case KeyEvent.KEYCODE_GRAVE:
                return isShiftPressed ? '~' : '`';
            case KeyEvent.KEYCODE_BACKSLASH:
                return isShiftPressed ? '|' : '\\';
            case KeyEvent.KEYCODE_LEFT_BRACKET:
                return isShiftPressed ? '{' : '[';
            case KeyEvent.KEYCODE_RIGHT_BRACKET:
                return isShiftPressed ? '}' : ']';
            case KeyEvent.KEYCODE_SEMICOLON:
                return isShiftPressed ? ':' : ';';
            case KeyEvent.KEYCODE_APOSTROPHE:
                return isShiftPressed ? '"' : '\'';
            case KeyEvent.KEYCODE_COMMA:
                return isShiftPressed ? '<' : ',';
            case KeyEvent.KEYCODE_PERIOD:
                return isShiftPressed ? '>' : '.';
            case KeyEvent.KEYCODE_SLASH:
                return isShiftPressed ? '?' : '/';
            case KeyEvent.KEYCODE_SPACE:
                return ' ';
            default:
                Log.d(TAG, "键码为" + keyCode + "的按键未做处理或无法识别");
                return 0;
        }
    }
}
