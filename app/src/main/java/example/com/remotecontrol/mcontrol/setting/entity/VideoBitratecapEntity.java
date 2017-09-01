package example.com.remotecontrol.mcontrol.setting.entity;

public class VideoBitratecapEntity {
    private String videobitrate;
    private String current;
    private String result;

    public VideoBitratecapEntity() {
    }

    public VideoBitratecapEntity(String paramString) {
        this.videobitrate = paramString;
    }

    public VideoBitratecapEntity(String paramString1, String paramString2, String paramString3) {
        this.videobitrate = paramString1;
        this.current = paramString2;
        this.result = paramString3;
    }

    public String getVideobitrate() {
        return this.videobitrate;
    }

    public void setVideobitrate(String paramString) {
        this.videobitrate = paramString;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String paramString) {
        this.result = paramString;
    }

    public String getCurrent() {
        return this.current;
    }

    public void setCurrent(String paramString) {
        this.current = paramString;
    }
}
