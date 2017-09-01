package example.com.remotecontrol.mcontrol.setting.entity;

public class NetworkStatusEntity {
    private String type = "ap";
    private String result;
    private String name;
    private String mac;
    private String state = "on";

    public NetworkStatusEntity() {
    }

    public NetworkStatusEntity(String paramString) {
        this.type = paramString;
    }

    public NetworkStatusEntity(String paramString1, String paramString2, String paramString3, String paramString4) {
        this.type = paramString1;
        this.name = paramString2;
        this.mac = paramString3;
        this.state = paramString4;
    }

    public NetworkStatusEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
        this.type = paramString1;
        this.name = paramString2;
        this.mac = paramString3;
        this.state = paramString4;
        this.result = paramString5;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String paramString) {
        this.type = paramString;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String paramString) {
        this.result = paramString;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String paramString) {
        this.mac = paramString;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String paramString) {
        this.state = paramString;
    }
}
