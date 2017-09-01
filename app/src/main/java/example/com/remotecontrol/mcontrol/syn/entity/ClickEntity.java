package example.com.remotecontrol.mcontrol.syn.entity;

public class ClickEntity {
    private String packagename;

    public ClickEntity(String paramString) {
        this.packagename = paramString;
    }

    public String getPackagename() {
        return this.packagename;
    }

    public void setPackagename(String paramString) {
        this.packagename = paramString;
    }
}
