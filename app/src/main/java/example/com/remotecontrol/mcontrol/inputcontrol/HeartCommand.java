package example.com.remotecontrol.mcontrol.inputcontrol;

import java.util.Map;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mbus.util.MsgDataParser;

public class HeartCommand extends AbstractMbusCommand {
    private String heartbeat = "heartbeat";

    public HeartCommand() {
    }

    public HeartCommand(String paramString) {
        this.heartbeat = paramString;
    }

    public byte[] dataToBytes()
            throws Exception {
        byte[] arrayOfByte = new byte[0];
        arrayOfByte = this.heartbeat.getBytes();
        return arrayOfByte;
    }

    public void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception {
        if (null == paramArrayOfByte)
            return;
        String str = new String(paramArrayOfByte);
        Map localMap = MsgDataParser.parserMsgDataByTokenizer(str);
        if ((null == localMap) || (localMap.size() == 0))
            return;
        if (null == localMap.get("heartbeat"))
            return;
        setHeartbeat((String) localMap.get("heartbeat"));
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_HEARTBEAT;
    }

    public int getCurrentMsgDataLen() {
        try {
            return dataToBytes().length;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public String getHeartbeat() {
        return this.heartbeat;
    }

    public void setHeartbeat(String paramString) {
        this.heartbeat = paramString;
    }
}
