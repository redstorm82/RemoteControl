package example.com.remotecontrol.multiscreen.remote;

import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

import example.com.remotecontrol.mbus.transport.udp.MbusUdp;
import example.com.remotecontrol.mbus.util.Constants;
import example.com.remotecontrol.mbus.util.EventKey;
import example.com.remotecontrol.mcontrol.setting.SettingCommand;
import example.com.remotecontrol.mcontrol.setting.entity.MuteStatusEntity;

/**
 * 控制盒子输出TV端声音功能
 * @author  ZhouYuChao/907753
 * @version  [版本号, 2013-12-27]
 * @since  [产品/模块版本]
 */
public class RemoteSettingMute {
    private static final String TAG = "RemoteSettingMute";

    private SettingCommand sendSettingCommand = null;
    
    /** udp客户端 */
    private MbusUdp udpClient = null;
    private String mDeviceIP = null;
    
    private MuteStatusEntity mMuteStatusEntity = null;

    public RemoteSettingMute(String devIP) {
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

    /**
     * 发送设置静音消息
     * <功能描述>
     * @param mute [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public void sendSetMuteEvent(boolean mute){
        Log.d(TAG, "[sendSetMuteEvent] begin");
        if (null == mMuteStatusEntity) {
            mMuteStatusEntity = new MuteStatusEntity();
            mMuteStatusEntity.setType(EventKey.MUTE_VALUE_TYPE_TV);
            if (mute){
                mMuteStatusEntity.setMute(EventKey.MUTE_VALUE_DISABLE);
                Log.d(TAG, "[sendSetMuteEvent] sound off ");
            } else {
                mMuteStatusEntity.setMute(EventKey.MUTE_VALUE_ENABLE);
                Log.d(TAG, "[sendSetMuteEvent] sound on ");
            }
        }
        
        sendSettingCommand = new SettingCommand(EventKey.ACTION_SET_MUTE, false, mMuteStatusEntity);
        
        try {
            if (null == udpClient) {
                udpClient = new MbusUdp(mDeviceIP, Constants.REMOTE_PORT);
                udpClient.connect();
            }
            Log.d(TAG, "[sendSetMuteEvent] send...");
            new Thread() {
                public void run() {
                    try {
                        udpClient.send(sendSettingCommand, mDeviceIP);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 查询静音状态
     * <功能描述> [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public void sendCheckMuteStateEvent(){
        Log.d(TAG, "[sendSetMuteEvent] begin");
        if (null == mMuteStatusEntity) {
            mMuteStatusEntity = new MuteStatusEntity();
            mMuteStatusEntity.setType(EventKey.MUTE_VALUE_TYPE_TV);
        }
        
        sendSettingCommand = new SettingCommand(EventKey.ACTION_GET_MUTE, false, mMuteStatusEntity);
        
        try {
            if (null == udpClient) {
                udpClient = new MbusUdp(mDeviceIP, Constants.REMOTE_PORT);
                udpClient.connect();
            }
            Log.d(TAG, "[sendSetMuteEvent] send...");
            udpClient.send(sendSettingCommand, mDeviceIP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void release(){
        if(udpClient != null){
            try {
                udpClient.disconnect();
                udpClient = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
