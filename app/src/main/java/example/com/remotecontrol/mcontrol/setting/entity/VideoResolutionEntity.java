package example.com.remotecontrol.mcontrol.setting.entity;

public class VideoResolutionEntity {
    private String resolution = "720p";
    private String current = "";
    private String result;

    public VideoResolutionEntity() {
    }

    public VideoResolutionEntity(String paramString) {
        this.resolution = paramString;
    }

    public VideoResolutionEntity(String paramString1, String paramString2) {
        this.resolution = paramString1;
        this.current = paramString2;
    }

    public VideoResolutionEntity(String paramString1, String paramString2, String paramString3) {
        this.resolution = paramString1;
        this.current = paramString2;
        this.result = paramString3;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String paramString) {
        this.resolution = paramString;
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
