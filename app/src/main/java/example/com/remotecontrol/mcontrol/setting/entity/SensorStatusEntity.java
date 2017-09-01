package example.com.remotecontrol.mcontrol.setting.entity;

public class SensorStatusEntity {
    private String result;
    private String state = "off";
    private String sensortype = "";

    public SensorStatusEntity() {
    }

    public SensorStatusEntity(String paramString) {
        this.state = paramString;
    }

    public SensorStatusEntity(String paramString1, String paramString2, String paramString3) {
        this.state = paramString1;
        this.sensortype = paramString2;
        this.result = paramString3;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String paramString) {
        this.result = paramString;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String paramString) {
        this.state = paramString;
    }

    public String getSensortype() {
        return this.sensortype;
    }

    public void setSensortype(String paramString) {
        this.sensortype = paramString;
    }
}

