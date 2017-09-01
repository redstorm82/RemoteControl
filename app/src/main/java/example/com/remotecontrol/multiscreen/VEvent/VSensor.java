package example.com.remotecontrol.multiscreen.VEvent;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;

import example.com.remotecontrol.multiscreen.mdnsdevice.MdnsDevice;
import example.com.remotecontrol.multiscreen.remote.base.GSensor;

public class VSensor {
    private static final String TAG = VSensor.class.getSimpleName();
    
    /** 坐标系特殊处理列表：3D终极狂飙2 */
    private static final String SPECIAL_LIST_CAR3D2 = "com.sxiaoao.car3d2";
    
    /** 标记是否开启sensor:0初始 */
    public static final int SENSOR_INIT = 0;
    /** 标记是否开启sensor:1手动开启 */
    public static final int SENSOR_MANUAL_OPEN = 1;
    /** 标记是否开启sensor:2手动关闭 */
    public static final int SENSOR_MANUAL_CLOSE = 2;
    /** 标记是否开启sensor:3自动开启 */
    public static final int SENSOR_AUTO_OPEN = 3;
    /** 标记是否开启sensor:4自动关闭 */
    public static final int SENSOR_AUTO_CLOSE = 4;
    
    private String mTVCurrentPackageName = "";
    
    // ---------------- Sensor ---------------- 
    private GSensor mSensor = new GSensor(); // RemoteSensor
    private SensorManager mSensorMgr;

    /** 加速度感应器 */
    private Sensor mAccSensor = null;
    /** 重力感应器 */
    private Sensor mGravitySensor = null;
    /** 陀螺仪 */
    private Sensor mGyroscopeSensor = null;
    /** 方向感应器 */
    private static Sensor mOrnSensor = null;
    /** 线性加速度 */
    private static Sensor mLinearAcceleration = null;
    /** 旋转向量 */
    private static Sensor mRotationVector = null;
    
    private SensorEventListener mSensorEventListener;
    
    /** 标记是否开启sensor:0初始，1手动开启，2手动关闭，3自动开启，4自动关闭 */
    private int mIsSensorOn = SENSOR_INIT;

    /**
     * 初始化各种Sensor
     * <功能描述>
     * @param activity
     * @param device [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public void initSensor(final Activity activity, final MdnsDevice device) {
        mSensorMgr = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        mAccSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravitySensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mGyroscopeSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mOrnSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mLinearAcceleration = mSensorMgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mRotationVector = mSensorMgr.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
      
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent e) {
                float x = 0.0f, y = 0.0f, z = 0.0f;
                int sensorType = e.sensor.getType();
                
                if (mSensor == null) {
                    return ;
                }
            
                x = e.values[0];
                y = e.values[1];
                z = e.values[2];

                // 旋转角度
                int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                switch (rotation) {
                case Surface.ROTATION_0:
                    break;
                case Surface.ROTATION_90:
                    // 方向传感器
                    if (sensorType == Sensor.TYPE_ORIENTATION) {
                        y = -e.values[2];
                        z = -e.values[1];
                    } else if (sensorType == Sensor.TYPE_ACCELEROMETER){
                        if (SPECIAL_LIST_CAR3D2.equals(mTVCurrentPackageName)) {
                            x = e.values[0];
                            y = e.values[1];
                        } else {
                            x = -e.values[1];
                            y = e.values[0];
                        }
                    } else {
                        // 其他传感器
                        x = -e.values[1];
                        y = e.values[0];
                    }
                    break;
                case Surface.ROTATION_180:
                    break;
                case Surface.ROTATION_270:
                     // 方向传感器
                    if (sensorType == Sensor.TYPE_ORIENTATION) {
                        y = e.values[2];
                        z = e.values[1];
                    } else if (sensorType == Sensor.TYPE_ACCELEROMETER){
                        if (SPECIAL_LIST_CAR3D2.equals(mTVCurrentPackageName)) {
                            x = -e.values[0];
                            y = -e.values[1];
                        } else {
                            x = e.values[1];
                            y = -e.values[0];
                        }
                    } else {
                        // 其他传感器
                        x = e.values[1];
                        y = -e.values[0];
                    }
                    break;
                }
                mSensor.setX(x);
                mSensor.setY(y);
                mSensor.setZ(z);
                mSensor.setType(sensorType);
                
                if (null != device) {
                    device.adapter().sensor(device, mSensor);
                } else {
                    Log.e(TAG, "initSensor ---> mCurrentDevice is null");
                }
            }
        };
    }
    
    /**
     * 开启Sensor
     * @param openType
     * @param sensorType [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public void openSensor(int openType, String sensorType) {
//        Log.d(TAG, "openSensor begin -> " + mIsSensorOn);
        if (SENSOR_MANUAL_OPEN == mIsSensorOn || SENSOR_AUTO_OPEN == mIsSensorOn || null == mSensorMgr) {
//            Log.d(TAG, "openSensor -> Skip has been opened " + mIsSensorOn);
            return;
        }
        
        int sensorDelay = SensorManager.SENSOR_DELAY_GAME;
        int type = 0;
        Log.d(TAG, "openSensor ---> sensorType : " + sensorType);
        
        String slip[] = sensorType.split(",");
        for (int i = 0; i < slip.length; i++) {
            Log.d(TAG, "openSensor ---> " + slip[i]);
            try {
                type = Integer.parseInt(slip[i].trim());
            } catch (Exception e) {
                Log.e(TAG, "openSensor Exception---> " + e.getMessage()); 
                type= 0;
            }
            
            switch (type){
            case Sensor.TYPE_ALL:
                mSensorMgr.registerListener(mSensorEventListener, mAccSensor, sensorDelay);
                mSensorMgr.registerListener(mSensorEventListener, mGravitySensor, sensorDelay);
                mSensorMgr.registerListener(mSensorEventListener, mGyroscopeSensor, sensorDelay);
                mSensorMgr.registerListener(mSensorEventListener, mOrnSensor, sensorDelay);
                mSensorMgr.registerListener(mSensorEventListener, mLinearAcceleration, sensorDelay);
                mSensorMgr.registerListener(mSensorEventListener, mRotationVector, sensorDelay);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                mSensorMgr.registerListener(mSensorEventListener, mAccSensor, sensorDelay);
                break;
            case Sensor.TYPE_GRAVITY:
                mSensorMgr.registerListener(mSensorEventListener, mGravitySensor, sensorDelay);
                break;
            case Sensor.TYPE_GYROSCOPE:
                mSensorMgr.registerListener(mSensorEventListener, mGyroscopeSensor, sensorDelay);
                break;
            case Sensor.TYPE_ORIENTATION:
                mSensorMgr.registerListener(mSensorEventListener, mOrnSensor, sensorDelay);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                mSensorMgr.registerListener(mSensorEventListener, mLinearAcceleration, sensorDelay);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                mSensorMgr.registerListener(mSensorEventListener, mRotationVector, sensorDelay);
                break;
            default:
                Log.e(TAG, "openSensor ---> unknow type : " + type);
                mIsSensorOn = SENSOR_INIT;
                return ;
            }
        }
        mIsSensorOn = openType;
        Log.d(TAG, "openSensor : openType -> " + mIsSensorOn + ", sensorType -> " + sensorType);
    }
    
    /**
     * 关闭Sensor
     * @param closeType
     * @param sensorType [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public void closeSensor(int closeType, String sensorType) {
//        Log.d(TAG, "closeSensor begin -> " + mIsSensorOn);
        if (SENSOR_MANUAL_CLOSE == mIsSensorOn || SENSOR_AUTO_CLOSE == mIsSensorOn || null == mSensorMgr) {
//            Log.d(TAG, "closeSensor -> Skip has been closed " + mIsSensorOn);
            return;
        }
        
        int type = 0;
        Log.d(TAG, "closeSensor ---> sensorType : " + sensorType);
        
        String slip[] = sensorType.split(",");
        for (int i = 0; i < slip.length; i++) {
            Log.d(TAG, "closeSensor ---> " + slip[i]);
            try {
                type = Integer.parseInt(slip[i].trim());
            } catch (Exception e) {
                Log.e(TAG, "closeSensor Exception---> " + e.getMessage()); 
                type= 0;
            }
            
            switch (type){
            case Sensor.TYPE_ALL:
                mSensorMgr.unregisterListener(mSensorEventListener, mAccSensor);
                mSensorMgr.unregisterListener(mSensorEventListener, mGravitySensor);
                mSensorMgr.unregisterListener(mSensorEventListener, mGyroscopeSensor);
                mSensorMgr.unregisterListener(mSensorEventListener, mOrnSensor);
                mSensorMgr.unregisterListener(mSensorEventListener, mLinearAcceleration);
                mSensorMgr.unregisterListener(mSensorEventListener, mRotationVector);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                mSensorMgr.unregisterListener(mSensorEventListener, mAccSensor);
                break;
            case Sensor.TYPE_GRAVITY:
                mSensorMgr.unregisterListener(mSensorEventListener, mGravitySensor);
                break;
            case Sensor.TYPE_GYROSCOPE:
                mSensorMgr.unregisterListener(mSensorEventListener, mGyroscopeSensor);
                break;
            case Sensor.TYPE_ORIENTATION:
                mSensorMgr.unregisterListener(mSensorEventListener, mOrnSensor);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                mSensorMgr.unregisterListener(mSensorEventListener, mLinearAcceleration);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                mSensorMgr.unregisterListener(mSensorEventListener, mRotationVector);
                break;
            default:
                Log.e(TAG, "closeSensor ---> unknow type...");
                break;
            }
        }

        mIsSensorOn = closeType;
        Log.d(TAG, "closeSensor : closeType -> " + mIsSensorOn + ", sensorType -> " + sensorType);
    }
    
    public void releaseSensor(){
        if (null != mSensorMgr) {
            mSensorMgr = null;
        }
        
        if (null != mSensorEventListener) {
            mSensorEventListener = null;
        }
        
        if (null != mAccSensor) {
            mAccSensor = null;
        }

        if (null != mGravitySensor) {
            mGravitySensor = null;
        }

        if (null != mOrnSensor) {
            mOrnSensor = null;
        }

        if (null != mSensor) {
            mSensor = null;
        }

        if (null != mLinearAcceleration) {
            mLinearAcceleration = null;
        }

        if (null != mRotationVector) {
            mRotationVector = null;
        }
    }

    public void setCurrentTvPackage(String curPackage){
        mTVCurrentPackageName = curPackage;
    }
    
    public String getCurrentTvPackage(){
        return mTVCurrentPackageName;
    }
    
    public int getSensorState() {
        return mIsSensorOn;
    }
}
