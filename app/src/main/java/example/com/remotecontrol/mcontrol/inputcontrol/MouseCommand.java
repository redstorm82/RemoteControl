package example.com.remotecontrol.mcontrol.inputcontrol;

import java.util.Map;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mbus.util.MsgDataParser;

public class MouseCommand extends AbstractMbusCommand {
    private int clickType = 1;
    private int mode = 0;
    private int dx = 0;
    private int dy = 0;

    public MouseCommand() {
    }

    public MouseCommand(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.clickType = paramInt1;
        this.mode = paramInt2;
        this.dx = paramInt3;
        this.dy = paramInt4;
    }

    public byte[] dataToBytes()
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("clicktype").append("=").append(getClickType());
        localStringBuilder.append("&");
        localStringBuilder.append("mode").append("=").append(getMode());
        localStringBuilder.append("&");
        localStringBuilder.append("x").append("=").append(getDx());
        localStringBuilder.append("&");
        localStringBuilder.append("y").append("=").append(getDy());
        return localStringBuilder.toString().getBytes();
    }

    public void dataFromBytes(byte[] paramArrayOfByte) {
        if (null == paramArrayOfByte)
            return;
        String str = new String(paramArrayOfByte);
        Map localMap = MsgDataParser.parserMsgDataByTokenizer(str);
        if ((null == localMap) || (localMap.size() == 0))
            return;
        try {
            if (null != localMap.get("clicktype"))
                setClickType(Integer.parseInt((String) localMap.get("clicktype")));
            if (null != localMap.get("clicktype"))
                setClickType(Integer.parseInt((String) localMap.get("clicktype")));
            if (null != localMap.get("mode"))
                setMode(Integer.parseInt((String) localMap.get("mode")));
            if (null != localMap.get("x"))
                setDx(Integer.parseInt((String) localMap.get("x")));
            if (null != localMap.get("y"))
                setDy(Integer.parseInt((String) localMap.get("y")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_MOUSE;
    }

    public int getCurrentMsgDataLen() {
        try {
            return dataToBytes().length;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public int getClickType() {
        return this.clickType;
    }

    public void setClickType(int paramInt) {
        this.clickType = paramInt;
    }

    public int getDx() {
        return this.dx;
    }

    public void setDx(int paramInt) {
        this.dx = paramInt;
    }

    public int getDy() {
        return this.dy;
    }

    public void setDy(int paramInt) {
        this.dy = paramInt;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int paramInt) {
        this.mode = paramInt;
    }
}
