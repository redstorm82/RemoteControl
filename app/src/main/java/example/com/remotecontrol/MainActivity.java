package example.com.remotecontrol;

import android.hardware.Sensor;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;

import example.com.remotecontrol.multiscreen.VEvent.VInputEvent;
import example.com.remotecontrol.util.OPENLOG;
import example.com.remotecontrol.multiscreen.mdnsdevice.MdnsDevice;
import example.com.remotecontrol.multiscreen.VEvent.VSensor;

public class MainActivity extends AppCompatActivity {
    // ---------------- Sensor ----------------
    private VSensor mVSensor;
    private VInputEvent mVInputEvent;
    private MdnsDevice mMdnsDevice;
    private static final String TAG = "MainActivity";

    /**
     * 鼠标模式
     */
    private boolean isMouseMode = false;

    /** 屏幕锁 */
    private PowerManager.WakeLock mWakeLock;
    //private String ip = "10.10.30.56";
    private String ip = "10.10.56.3";
    private String mac = "AC-2B-6E-C0-71-FA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMdnsDevice = new MdnsDevice("test",ip);
        mVSensor = new VSensor();
        mVSensor.initSensor(this,mMdnsDevice);
        OPENLOG.I("create sensor ");

        // 获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mVInputEvent = new VInputEvent();
        mVInputEvent.initInputEvent(mMdnsDevice, dm.widthPixels, dm.heightPixels);
        OPENLOG.I("init VInputEvent");
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (VSensor.SENSOR_MANUAL_OPEN == mVSensor.getSensorState()
                || VSensor.SENSOR_AUTO_OPEN == mVSensor.getSensorState()) {
            mVSensor.closeSensor(VSensor.SENSOR_INIT, Sensor.TYPE_ALL + "");
        }

        // 释放屏幕锁
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }

        mVInputEvent.closetInputEvent(0,"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        OPENLOG.I("open all sensor ");
      /*  // 获取屏幕锁，不熄屏
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
        mWakeLock.acquire();*/
        //mVSensor.openSensor(VSensor.SENSOR_AUTO_OPEN,Sensor.TYPE_ALL + "");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://4
            {
                if (mCurrentDevice != null && null != mCurrentDevice.adapter())
                    mCurrentDevice.adapter().key(mCurrentDevice, KeyInfo.KEYCODE_BACK);
            }
            return true;
            case KeyEvent.KEYCODE_HOME:
                releasePlayer();
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (mCurrentDevice != null && null != mCurrentDevice.adapter()) {
                    mCurrentDevice.adapter().key(mCurrentDevice, KeyInfo.KEYCODE_VOLUME_UP);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (mCurrentDevice != null && null != mCurrentDevice.adapter()) {
                    mCurrentDevice.adapter().key(mCurrentDevice, KeyInfo.KEYCODE_VOLUME_DOWN);
                }
                // modify by zyc only for adjust TV's volume
                return true;
            case KeyEvent.KEYCODE_MENU://82
                if (mCurrentDevice != null && null != mCurrentDevice.adapter()) {
                    mCurrentDevice.adapter().key(mCurrentDevice, KeyInfo.KEYCODE_MENU);
                }
                break;
            default:
                mGamepad.handleOnKeyDown(keyCode, event, mCurrentDevice);
                break;
        }*/
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        OPENLOG.I("[onTouchEvent]");

        if (isMouseMode) {
            mVInputEvent.handleMouse(event);
        } else {
            return mVInputEvent.handleTouch(event);
        }
        return super.onTouchEvent(event);
    }
}
