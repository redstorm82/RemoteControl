package example.com.remotecontrol.mcontrol.inputcontrol.entity;

import java.io.Serializable;

public class TouchEntity
        implements Serializable {
    private static final long serialVersionUID = 8878583323288578711L;
    private int xLoc = 0;
    private int yLoc = 0;
    private int press = 0;

    public TouchEntity() {
    }

    public TouchEntity(int paramInt1, int paramInt2, int paramInt3) {
        this.xLoc = paramInt1;
        this.yLoc = paramInt2;
        this.press = paramInt3;
    }

    public int getxLoc() {
        return this.xLoc;
    }

    public void setxLoc(int paramInt) {
        this.xLoc = paramInt;
    }

    public int getyLoc() {
        return this.yLoc;
    }

    public void setyLoc(int paramInt) {
        this.yLoc = paramInt;
    }

    public int getPress() {
        return this.press;
    }

    public void setPress(int paramInt) {
        this.press = paramInt;
    }
}
