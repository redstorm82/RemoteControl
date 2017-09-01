package example.com.remotecontrol.mcontrol.syn.entity;

public class SlideEntity {
    private String rang;
    private String direction;

    public SlideEntity(String paramString1, String paramString2) {
        this.rang = paramString1;
        this.direction = paramString2;
    }

    public String getRang() {
        return this.rang;
    }

    public void setRang(String paramString) {
        this.rang = paramString;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String paramString) {
        this.direction = paramString;
    }
}

