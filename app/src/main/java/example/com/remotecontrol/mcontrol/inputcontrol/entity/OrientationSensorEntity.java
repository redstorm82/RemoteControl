package example.com.remotecontrol.mcontrol.inputcontrol.entity;

public class OrientationSensorEntity {
    private float azimuth = 0.0F;
    private float pitch = 0.0F;
    private float roll = 0.0F;

    public OrientationSensorEntity() {
    }

    public OrientationSensorEntity(float paramFloat1, float paramFloat2, float paramFloat3) {
        this.azimuth = paramFloat1;
        this.pitch = paramFloat2;
        this.roll = paramFloat3;
    }

    public float getAzimuth() {
        return this.azimuth;
    }

    public void setAzimuth(float paramFloat) {
        this.azimuth = paramFloat;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float paramFloat) {
        this.pitch = paramFloat;
    }

    public float getRoll() {
        return this.roll;
    }

    public void setRoll(float paramFloat) {
        this.roll = paramFloat;
    }
}
