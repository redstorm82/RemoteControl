package example.com.remotecontrol.mcontrol.log;

import java.util.Map;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mbus.util.MsgDataParser;

public class LogCommand extends AbstractMbusCommand {
    private String tag;
    private String logMsg;

    public LogCommand() {
    }

    public LogCommand(String paramString1, String paramString2) {
        this.tag = paramString1;
        this.logMsg = paramString2;
    }

    public void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception {
        if (null == paramArrayOfByte)
            return;
        String str = new String(paramArrayOfByte);
        Map localMap = MsgDataParser.parserMsgDataByTokenizer(str);
        if ((null == localMap) || (localMap.size() == 0))
            return;
        if (null != localMap.get("tag"))
            setTag((String) localMap.get("tag"));
        if (null == localMap.get("logmsg"))
            return;
        setLogMsg((String) localMap.get("logmsg"));
    }

    public byte[] dataToBytes()
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("tag").append("=").append(getTag());
        localStringBuilder.append("&");
        localStringBuilder.append("logmsg").append("=").append(getLogMsg());
        byte[] arrayOfByte = new byte[0];
        if (null != localStringBuilder)
            arrayOfByte = localStringBuilder.toString().getBytes();
        return arrayOfByte;
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_LOG;
    }

    public int getCurrentMsgDataLen() {
        try {
            return dataToBytes().length;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String paramString) {
        this.tag = paramString;
    }

    public String getLogMsg() {
        return this.logMsg;
    }

    public void setLogMsg(String paramString) {
        this.logMsg = paramString;
    }
}
