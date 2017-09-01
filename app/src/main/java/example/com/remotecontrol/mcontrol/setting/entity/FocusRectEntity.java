package example.com.remotecontrol.mcontrol.setting.entity;

public class FocusRectEntity {
    private static final String TAG = "FocusRectEntity";
    private String result;
    private String actionType;
    private String x;
    private String y;
    private String w;
    private String h;

    public FocusRectEntity() {
    }

    public FocusRectEntity(String paramString) {
        this.actionType = paramString;
    }

    public FocusRectEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
        this.actionType = paramString1;
        this.x = paramString2;
        this.y = paramString3;
        this.w = paramString4;
        this.h = paramString5;
    }

    public void setResult(String paramString) {
        this.result = paramString;
    }

    public String getResult() {
        return this.result;
    }

    public void setActionType(String paramString) {
        this.actionType = paramString;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setX(String paramString) {
        this.x = paramString;
    }

    public String getX() {
        return this.x;
    }

    public void setY(String paramString) {
        this.y = paramString;
    }

    public String getY() {
        return this.y;
    }

    public void setWidth(String paramString) {
        this.w = paramString;
    }

    public String getWidth() {
        return this.w;
    }

    public void setHeight(String paramString) {
        this.h = paramString;
    }

    public String getHeight() {
        return this.h;
    }
}
