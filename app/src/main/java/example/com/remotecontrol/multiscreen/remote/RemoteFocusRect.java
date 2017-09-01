package example.com.remotecontrol.multiscreen.remote;

import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

import example.com.remotecontrol.mbus.transport.udp.MbusUdp;
import example.com.remotecontrol.mbus.util.Constants;
import example.com.remotecontrol.mbus.util.EventKey;
import example.com.remotecontrol.mcontrol.setting.SettingCommand;
import example.com.remotecontrol.mcontrol.setting.entity.FocusRectEntity;

public class RemoteFocusRect {
private static final String TAG = "RemoteFocusRect";
    
    /** udp客户端 */
    private MbusUdp udpClient = null;
    private String mDeviceIP = null;

    private FocusRectEntity mFocusRectEntity;
    private SettingCommand mCommand = null;
    
    int x, y, width, height;
    
    public RemoteFocusRect(String devIP) {
        udpClient = new MbusUdp(devIP, Constants.REMOTE_PORT);
        try {
            udpClient.connect();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        mDeviceIP = devIP;
    }
    
    protected void destory() {
        try {
            udpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRemote(String address) {
        mDeviceIP = address;
    }

    public void setActionMoveParams(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * 发送设置聚焦事件 <功能描述>
     * 
     * @param actionType:EventKey.START_FOCUS,EventKey.STOP_FOCUS,EventKey.MOVE_FOCUS
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public void sendFocusRectEvent(String actionType) {
        Log.d(TAG, "[sendFocusRectEvent] begin");
        if (null == mFocusRectEntity) {
            mFocusRectEntity = new FocusRectEntity();
        }

        if (EventKey.START_FOCUS.equals(actionType)) {
            Log.d(TAG, "sendFocusRectEvent action START...");
            mFocusRectEntity.setX(x + "");
            mFocusRectEntity.setY(y + "");
            mFocusRectEntity.setWidth(width + "");
            mFocusRectEntity.setHeight(height + "");

        } else if (EventKey.STOP_FOCUS.equals(actionType)) {
            Log.d(TAG, "sendFocusRectEvent action STOP...");

        } else if (EventKey.MOVE_FOCUS.equals(actionType)) {
            Log.d(TAG, "sendFocusRectEvent action MOVE...");
            mFocusRectEntity.setX(x + "");
            mFocusRectEntity.setY(y + "");
            mFocusRectEntity.setWidth(width + "");
            mFocusRectEntity.setHeight(height + "");
        } else {
            Log.e(TAG, "unknow action!");
        }
        Log.d(TAG, "2013 mFocusRectEntity "+x+" "+y+" "+width+" "+height);
        
        mCommand = new SettingCommand(actionType, false, mFocusRectEntity);
        
        try {
            if (null == udpClient) {
                udpClient = new MbusUdp(mDeviceIP, Constants.REMOTE_PORT);
                udpClient.connect();
            }
            
            Log.d(TAG, "[sendFocusRectEvent] send...");
            
            new Thread() {
                public void run() {
                    try {
                        udpClient.send(mCommand, mDeviceIP);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
