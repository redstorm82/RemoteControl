package example.com.remotecontrol.mcontrol.setting.entity;

public class MuteStatusEntity {
    private String result;
    private String mMuteState;
    private String mType;

    public MuteStatusEntity() {
    }

    public MuteStatusEntity(String paramString1, String paramString2) {
        this.mMuteState = paramString1;
        this.mType = paramString2;
    }

    public void setMute(String paramString) {
        this.mMuteState = paramString;
    }

    public String getMute() {
        return this.mMuteState;
    }

    public void setType(String paramString) {
        this.mType = paramString;
    }

    public String getType() {
        return this.mType;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String paramString) {
        this.result = paramString;
    }
}
