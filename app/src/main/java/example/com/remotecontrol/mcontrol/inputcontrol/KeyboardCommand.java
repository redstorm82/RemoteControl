package example.com.remotecontrol.mcontrol.inputcontrol;

import java.util.Map;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mbus.util.MsgDataParser;

public class KeyboardCommand extends AbstractMbusCommand {
    private int keyValue = 0;
    private int functionKey = 0;
    private int action = -1;

    public KeyboardCommand() {
    }

    public KeyboardCommand(int paramInt1, int paramInt2) {
        this.keyValue = paramInt1;
        this.functionKey = paramInt2;
    }

    public KeyboardCommand(int paramInt1, int paramInt2, int paramInt3) {
        this.keyValue = paramInt1;
        this.functionKey = paramInt2;
        this.action = paramInt3;
    }

    public void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception {
        if (null == paramArrayOfByte)
            return;
        String str = new String(paramArrayOfByte);
        Map localMap = MsgDataParser.parserMsgDataByTokenizer(str);
        if ((null == localMap) || (localMap.size() == 0))
            return;
        try {
            if (null != localMap.get("keyvalue"))
                setKeyValue(Integer.parseInt((String) localMap.get("keyvalue")));
            if (null != localMap.get("functionkey"))
                setKeyValue(Integer.parseInt((String) localMap.get("functionkey")));
            if (null != localMap.get("action"))
                setKeyValue(Integer.parseInt((String) localMap.get("action")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
    }

    public byte[] dataToBytes()
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("keyvalue").append("=").append(getKeyValue());
        localStringBuilder.append("&");
        localStringBuilder.append("functionkey").append("=").append(getFunctionKey());
        localStringBuilder.append("&");
        if (-1 != getAction())
            localStringBuilder.append("action").append("=").append(getAction());
        byte[] arrayOfByte = new byte[0];
        if (null != localStringBuilder)
            arrayOfByte = localStringBuilder.toString().getBytes();
        return arrayOfByte;
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_KEYBOARD;
    }

    public int getCurrentMsgDataLen() {
        try {
            return dataToBytes().length;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public int getKeyValue() {
        return this.keyValue;
    }

    public void setKeyValue(int paramInt) {
        this.keyValue = paramInt;
    }

    public int getFunctionKey() {
        return this.functionKey;
    }

    public void setFunctionKey(int paramInt) {
        this.functionKey = paramInt;
    }

    public void setAction(int paramInt) {
        this.action = paramInt;
    }

    public int getAction() {
        return this.action;
    }
}
