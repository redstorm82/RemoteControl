package example.com.remotecontrol.mcontrol.inputcontrol.entity;

public class AccelerationSensorEntity {
    private float ax = 0.0F;
    private float ay = 0.0F;
    private float az = 0.0F;

    public AccelerationSensorEntity() {
    }

    public AccelerationSensorEntity(float paramFloat1, float paramFloat2, float paramFloat3) {
        this.ax = paramFloat1;
        this.ay = paramFloat2;
        this.az = paramFloat3;
    }

    public float getAx() {
        return this.ax;
    }

    public void setAx(float paramFloat) {
        this.ax = paramFloat;
    }

    public float getAy() {
        return this.ay;
    }

    public void setAy(float paramFloat) {
        this.ay = paramFloat;
    }

    public float getAz() {
        return this.az;
    }

    public void setAz(float paramFloat) {
        this.az = paramFloat;
    }
}
