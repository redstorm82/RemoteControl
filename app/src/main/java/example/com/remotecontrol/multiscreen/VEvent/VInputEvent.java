package example.com.remotecontrol.multiscreen.VEvent;

import android.view.MotionEvent;

import example.com.remotecontrol.multiscreen.Constant;
import example.com.remotecontrol.multiscreen.mdnsdevice.MdnsDevice;
import example.com.remotecontrol.multiscreen.remote.base.MultiTouchInfo;
import example.com.remotecontrol.multiscreen.remote.base.Mouse;
import example.com.remotecontrol.util.OPENLOG;

/**
 * Created by shenshaohui on 2016/9/8.
 */
public class VInputEvent {
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

    private MdnsDevice mCurrentDevice = null;


    private int lastX = 0, lastY = 0;
    private long mouseDownTime = 0;
    private Mouse mMouse = new Mouse();

    /**
     * 触摸模式
     **/
    /*是否按着屏幕*/
    private boolean isTouchScreen = false;
    private long startPressTime;
    private MotionEvent mEvent;
    private MultiTouchInfo mMultiTouchInfo = new MultiTouchInfo();

    private int mScreenWidth, mScreenHeight;
    /**
     * 初始化各种Sensor
     * <功能描述>
     * @param device [参数说明]
     * @param screenHeight
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public void initInputEvent(final MdnsDevice device,int screenWidth,int screenHeight) {
        mCurrentDevice=device;
        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;
    }
    public void closetInputEvent(int closeType, String InputType) {

    }
    public void handleMouse(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        x = x * Constant.WIDTH_1080P / mScreenWidth;
        y = y * Constant.HEIGHT_1080P / mScreenHeight;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mouseDownTime = System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                if (deltaX == 0 && deltaY == 0) {
                    return;
                }

                // 双指滚动
                if (event.getPointerCount() == 2) {
                    mMouse.setAction(Mouse.ACTION_ROLL);
                    if (deltaY > 0) { // 下滚
                        mMouse.setType(Mouse.MOUSE_ROLL_DOWN);
                    } else { // 上滚
                        mMouse.setType(Mouse.MOUSE_ROLL_UP);
                    }

                    if (null != mCurrentDevice) {
                        mCurrentDevice.adapter().mouse(mCurrentDevice, mMouse);
                    }
                } else if (event.getPointerCount() == 1) {
                    mMouse.setAction(Mouse.ACTION_MOVE);
                    mMouse.setType(Mouse.MOUSE_LEFT);
                    mMouse.move(deltaX, deltaY);

                    if (null != mCurrentDevice) {
                        mCurrentDevice.adapter().mouse(mCurrentDevice, mMouse);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - mouseDownTime < 500) {
                    mMouse.setAction(Mouse.ACTION_SINGLE_CLICK);
                    mMouse.setType(Mouse.MOUSE_LEFT);

                    if (null != mCurrentDevice) {
                        mCurrentDevice.adapter().mouse(mCurrentDevice, mMouse);
                    }
                }
                break;

            default:
                break;
        }

        lastX = x;
        lastY = y;
    }

    /**
     * 触摸处理
     *
     * @param event [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     */
    public boolean handleTouch(MotionEvent event) {
        int pointerCount = event.getPointerCount();

        if (pointerCount > 5) {
            pointerCount = 5;
        }
        mMultiTouchInfo.setFingerNum(pointerCount);

        int action = (event.getAction() & MotionEvent.ACTION_MASK);// 统一单点和多点
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                OPENLOG.I("---> ACTION_DOWN <---");
                startPressTime = System.currentTimeMillis();
                isTouchScreen = false;
                mEvent = null;

                getMultiTouchEntity(pointerCount, event, 1);
                break;
            case MotionEvent.ACTION_MOVE:
                OPENLOG.I("---> ACTION_MOVE <---");
                isTouchScreen = true;
                /*if ((System.currentTimeMillis() - startPressTime) > 1500
                        && isSupportEnlarge) {
                    if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                            || handleTouchManager != null) {
                        if (handleTouchManager.canVerticalEnlarger()) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                        }
                    }
                    mEvent = event;
                    startPressTime = System.currentTimeMillis();
                }*/

                getMultiTouchEntity(pointerCount, event, 1);
                break;
            case MotionEvent.ACTION_UP:
                OPENLOG.I("---> ACTION_UP <---");
                /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                        && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                        && isSupportEnlarge) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }*/
                isTouchScreen = false;
                mEvent = null;

                getMultiTouchEntity(pointerCount, event, 0);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                OPENLOG.I("---> ACTION_POINTER_DOWN <---");
                isTouchScreen = false;
                getMultiTouchEntity(pointerCount, event, 1);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                OPENLOG.I("---> ACTION_POINTER_UP <---");
                isTouchScreen = false;

                getMultiTouchEntity(pointerCount, event, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                OPENLOG.I("---> ACTION_CANCEL <---");
                isTouchScreen = false;
                /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                        && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                        && isSupportEnlarge) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }*/
                mEvent = null;
                break;
        }

        if (null != mCurrentDevice) {
            mMultiTouchInfo.print();
            mCurrentDevice.adapter().touch(mCurrentDevice, mMultiTouchInfo);
        } else {
            OPENLOG.E("onTouchEvent mCurrentDevice is null");
        }

        // 解决多点触控，其中一点不动，其它点无法响应Up事件
        if (mMultiTouchInfo.getFingerNum() > 1 && MotionEvent.ACTION_POINTER_UP == action) {
            MultiTouchInfo tmpTouchInfo = new MultiTouchInfo();
            int count = 0;

            for (int i = 0; i < mMultiTouchInfo.getFingerNum(); i++) {
                int press = mMultiTouchInfo.getFingerInfo(i).getPress();
                if (1 == press) {
                    count++;
                    int x = mMultiTouchInfo.getFingerInfo(i).getX();
                    int y = mMultiTouchInfo.getFingerInfo(i).getY();
                    tmpTouchInfo.setFingerInfo(i, x, y, press);
                }
            }
            tmpTouchInfo.setFingerNum(count);

            if (null != mCurrentDevice) {
                OPENLOG.I("send..." + tmpTouchInfo.getFingerNum());
                mCurrentDevice.adapter().touch(mCurrentDevice, tmpTouchInfo);
            }
        }
        return true;
    }

    /**
     * 多指触摸处理
     *
     * @param pointerCount
     * @param event
     * @param press
     * @return
     */
    private void getMultiTouchEntity(int pointerCount, MotionEvent event, int press) {
        int x = 0;
        int y = 0;
        int tempPress = 0;

        for (int p = 0; p < pointerCount; p++) {
            x = (int) event.getX(p);
            y = (int) event.getY(p);

            x = x * Constant.WIDTH_1080P / mScreenWidth;
            y = y * Constant.HEIGHT_1080P / mScreenHeight;

//            if(handleTouchManager !=null){
//            	int[]coordinates = handleTouchManager.coordinateAdapter(x, y);
//            	x=coordinates[0];
//            	y=coordinates[1];
//            }

            if (pointerCount > 1) {// 多指
                if (1 == press) {// point_down 事件
                    tempPress = press;
                } else if (0 == press) {// point_up 事件
                    if (p == event.getPointerId(event.getActionIndex())) {// 抬起的手指
                        tempPress = 0;
                    } else {
                        tempPress = 1;
                    }
                }
            } else {// 单指
                tempPress = press;
            }
            mMultiTouchInfo.setFingerInfo(p, x, y, tempPress);
        }
    }


}
