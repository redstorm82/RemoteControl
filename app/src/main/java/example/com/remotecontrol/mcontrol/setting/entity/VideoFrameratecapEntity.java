package example.com.remotecontrol.mcontrol.setting.entity;

public class VideoFrameratecapEntity {
    private String framerate;
    private String current;
    private String result;

    public VideoFrameratecapEntity() {
    }

    public VideoFrameratecapEntity(String paramString) {
        this.framerate = paramString;
    }

    public VideoFrameratecapEntity(String paramString1, String paramString2, String paramString3) {
        this.framerate = paramString1;
        this.current = paramString2;
        this.result = paramString3;
    }

    public String getFramerate() {
        return this.framerate;
    }

    public void setFramerate(String paramString) {
        this.framerate = paramString;
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
