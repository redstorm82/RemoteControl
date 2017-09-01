package example.com.remotecontrol.mcontrol.syn;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mcontrol.syn.entity.ClickEntity;
import example.com.remotecontrol.mcontrol.syn.entity.SlideEntity;

public class SynCommand extends AbstractMbusCommand {
    private String action = "";
    private SlideEntity slideEntity = null;
    private ClickEntity clickEntity = null;

    public SynCommand() {
    }

    public SynCommand(String paramString, SlideEntity paramSlideEntity) {
        this.action = paramString;
        this.slideEntity = paramSlideEntity;
    }

    public SynCommand(String paramString, ClickEntity paramClickEntity) {
        this.action = paramString;
        this.clickEntity = paramClickEntity;
    }

    public SynCommand(String paramString) {
        this.action = paramString;
    }

    public byte[] dataToBytes()
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        if (getAction().length() > 2) {
            localStringBuilder.append(getAction());
        } else {
            localStringBuilder.append("action").append("=").append(getAction());
            localStringBuilder.append("&");
            if ("1".equals(this.action))
                localStringBuilder.append("packagename").append("=").append(this.clickEntity.getPackagename());
            else if ("3".equals(this.action))
                localStringBuilder.append("filmtotal").append("=").append(this.clickEntity.getPackagename());
            else if (!"2".equals(this.action))
                if (!"4".equals(this.action)) ;
        }
        return localStringBuilder.toString().getBytes();
    }

    public void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception {
        if (paramArrayOfByte == null)
            return;
        String str = new String(paramArrayOfByte);
        if (str == null)
            return;
        this.action = str;
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_SYN;
    }

    public int getCurrentMsgDataLen() {
        try {
            return dataToBytes().length;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String paramString) {
        this.action = paramString;
    }

    public SlideEntity getSlideEntity() {
        return this.slideEntity;
    }

    public void setSlideEntity(SlideEntity paramSlideEntity) {
        this.slideEntity = paramSlideEntity;
    }

    public ClickEntity getClickEntity() {
        return this.clickEntity;
    }

    public void setClickEntity(ClickEntity paramClickEntity) {
        this.clickEntity = paramClickEntity;
    }
}
