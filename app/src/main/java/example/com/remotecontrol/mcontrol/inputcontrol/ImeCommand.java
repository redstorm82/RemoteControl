package example.com.remotecontrol.mcontrol.inputcontrol;

import java.util.Map;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mbus.util.MsgDataParser;
import example.com.remotecontrol.mbus.util.StringUtil;

public class ImeCommand extends AbstractMbusCommand {
    private String action = "";
    private String command = "";
    private String inputContent = "";
    private int step = 0;
    private int inputType;
    private int inputSwitch = -1;
    private int imeoptins = 0;
    private int encrypt = 0;

    public ImeCommand() {
    }

    public ImeCommand(String paramString) {
        this.action = paramString;
    }

    public ImeCommand(String paramString1, String paramString2) {
        this.action = paramString1;
        this.command = paramString2;
    }

    public ImeCommand(String paramString1, String paramString2, String paramString3) {
        this.action = paramString1;
        this.command = paramString2;
        this.inputContent = paramString3;
    }

    public ImeCommand(String paramString1, String paramString2, String paramString3, int paramInt) {
        this.action = paramString1;
        this.command = paramString2;
        this.inputContent = paramString3;
        this.step = paramInt;
    }

    public ImeCommand(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2) {
        this.action = paramString1;
        this.command = paramString2;
        this.inputContent = paramString3;
        this.step = paramInt1;
        this.inputType = paramInt2;
    }

    public byte[] dataToBytes()
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("action").append("=").append(getAction());
        localStringBuilder.append("&");
        localStringBuilder.append("command").append("=").append(getCommand());
        localStringBuilder.append("&");
        localStringBuilder.append("inputcontent").append("=").append(getInputContent());
        localStringBuilder.append("&");
        localStringBuilder.append("step").append("=").append(getStep());
        localStringBuilder.append("&");
        localStringBuilder.append("inputtype").append("=").append(getInputType());
        localStringBuilder.append("&");
        localStringBuilder.append("inputswitch").append("=").append(getInputSwitch());
        localStringBuilder.append("&");
        localStringBuilder.append("imeoptins").append("=").append(getImeoptins());
        localStringBuilder.append("&");
        localStringBuilder.append("encrypt").append("=").append(getEncrypt());
        return localStringBuilder.toString().getBytes();
    }

    public void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception {
        if (paramArrayOfByte == null)
            return;
        String str1 = new String(paramArrayOfByte);
        Map localMap = MsgDataParser.parserMsgDataByTokenizer(str1);
        if ((localMap == null) || (localMap.size() == 0))
            return;
        setAction(StringUtil.defaultString((String) localMap.get("action")));
        setCommand(StringUtil.defaultString((String) localMap.get("command")));
        setInputContent(StringUtil.defaultString((String) localMap.get("inputcontent")));
        try {
            String str2 = (String) localMap.get("step");
            if (StringUtil.isNotEmpty(str2))
                setStep(Integer.valueOf(str2).intValue());
            String str3 = (String) localMap.get("inputtype");
            if (StringUtil.isNotEmpty(str3))
                setInputType(Integer.valueOf(str3).intValue());
            String str4 = (String) localMap.get("inputswitch");
            if (StringUtil.isNotEmpty(str4))
                setInputSwitch(Integer.valueOf(str4).intValue());
            String str5 = (String) localMap.get("imeoptins");
            if (StringUtil.isNotEmpty(str5))
                setImeoptins(Integer.valueOf(str5).intValue());
            String str6 = (String) localMap.get("encrypt");
            if (StringUtil.isNotEmpty(str6))
                setEncrypt(Integer.valueOf(str6).intValue());
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.printStackTrace();
        }
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_IME;
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

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String paramString) {
        this.command = paramString;
    }

    public String getInputContent() {
        return this.inputContent;
    }

    public void setInputContent(String paramString) {
        this.inputContent = paramString;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int paramInt) {
        this.step = paramInt;
    }

    public int getInputType() {
        return this.inputType;
    }

    public void setInputType(int paramInt) {
        this.inputType = paramInt;
    }

    public int getInputSwitch() {
        return this.inputSwitch;
    }

    public void setInputSwitch(int paramInt) {
        this.inputSwitch = paramInt;
    }

    public int getImeoptins() {
        return this.imeoptins;
    }

    public void setImeoptins(int paramInt) {
        this.imeoptins = paramInt;
    }

    public int getEncrypt() {
        return this.encrypt;
    }

    public void setEncrypt(int paramInt) {
        this.encrypt = paramInt;
    }
}
