package example.com.remotecontrol.mcontrol.inputcontrol;

import java.util.StringTokenizer;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.TouchEntity;

public class TouchCommand extends AbstractMbusCommand {
    private int mode = 0;
    private int fingerNum = 0;
    private TouchEntity[] multiTouch = null;

    public TouchCommand() {
    }

    public TouchCommand(TouchEntity[] paramArrayOfTouchEntity) {
        this.multiTouch = paramArrayOfTouchEntity;
    }

    public TouchCommand(int paramInt, TouchEntity[] paramArrayOfTouchEntity) {
        this.mode = paramInt;
        this.multiTouch = paramArrayOfTouchEntity;
    }

    public TouchCommand(int paramInt1, int paramInt2, TouchEntity[] paramArrayOfTouchEntity) {
        this.mode = paramInt1;
        this.fingerNum = paramInt2;
        this.multiTouch = paramArrayOfTouchEntity;
    }

    public byte[] dataToBytes()
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("mode").append("=").append(getMode());
        if ((null != this.multiTouch) && (this.multiTouch.length > 0)) {
            this.fingerNum = this.multiTouch.length;
            localStringBuilder.append("&");
            localStringBuilder.append("fingernum").append("=").append(getFingerNum());
            for (int i = 0; i < this.fingerNum; ++i) {
                localStringBuilder.append("&");
                localStringBuilder.append("x").append("=").append(this.multiTouch[i].getxLoc());
                localStringBuilder.append("&");
                localStringBuilder.append("y").append("=").append(this.multiTouch[i].getyLoc());
                localStringBuilder.append("&");
                localStringBuilder.append("press").append("=").append(this.multiTouch[i].getPress());
            }
        }
        byte[] arrayOfByte = new byte[0];
        if (null != localStringBuilder)
            arrayOfByte = localStringBuilder.toString().getBytes();
        return arrayOfByte;
    }

    public void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception {
        if (null == paramArrayOfByte)
            return;
        String str1 = new String(paramArrayOfByte);
        if (str1 == null)
            return;
        StringTokenizer localStringTokenizer1 = new StringTokenizer(str1, "&");
        TouchEntity localTouchEntity = new TouchEntity();
        int i = 0;
        int j = 1;
        while (localStringTokenizer1.hasMoreElements()) {
            String str2 = localStringTokenizer1.nextToken();
            StringTokenizer localStringTokenizer2 = new StringTokenizer(str2, "=");
            if (localStringTokenizer2.hasMoreElements()) {
                String str3 = localStringTokenizer2.nextToken();
                String str4 = "0";
                if (localStringTokenizer2.hasMoreElements())
                    str4 = localStringTokenizer2.nextToken();
                if ("mode".equals(str3)) {
                    setMode(Integer.parseInt(str4));
                } else if ("fingernum".equals(str3)) {
                    setFingerNum(Integer.parseInt(str4));
                    setMultiTouch(new TouchEntity[Integer.parseInt(str4)]);
                } else if ("x".equals(str3)) {
                    localTouchEntity.setxLoc(Integer.parseInt(str4));
                    ++j;
                } else if ("y".equals(str3)) {
                    localTouchEntity.setyLoc(Integer.parseInt(str4));
                    ++j;
                } else if ("press".equals(str3)) {
                    localTouchEntity.setPress(Integer.parseInt(str4));
                    ++j;
                }
                if ((j % 3 == 0) && (i < this.fingerNum)) {
                    getMultiTouch()[(i++)] = localTouchEntity;
                    localTouchEntity = new TouchEntity();
                }
            }
        }
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_TOUCH;
    }

    public int getCurrentMsgDataLen() {
        try {
            return dataToBytes().length;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int paramInt) {
        this.mode = paramInt;
    }

    public int getFingerNum() {
        if (this.fingerNum > this.multiTouch.length)
            this.fingerNum = this.multiTouch.length;
        return this.fingerNum;
    }

    public void setFingerNum(int paramInt) {
        this.fingerNum = paramInt;
    }

    public TouchEntity[] getMultiTouch() {
        return this.multiTouch;
    }

    public void setMultiTouch(TouchEntity[] paramArrayOfTouchEntity) {
        this.multiTouch = paramArrayOfTouchEntity;
    }
}
