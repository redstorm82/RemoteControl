package example.com.remotecontrol.mcontrol.inputcontrol.entity;

public class LinearAccelerationSensorEntity {
    private float axisX = 0.0F;
    private float axisY = 0.0F;
    private float axisZ = 0.0F;

    public LinearAccelerationSensorEntity() {
    }

    public LinearAccelerationSensorEntity(float paramFloat1, float paramFloat2, float paramFloat3) {
        this.axisX = paramFloat1;
        this.axisY = paramFloat2;
        this.axisZ = paramFloat3;
    }

    public float getAxisX() {
        return this.axisX;
    }

    public void setAxisX(float paramFloat) {
        this.axisX = paramFloat;
    }

    public float getAxisY() {
        return this.axisY;
    }

    public void setAxisY(float paramFloat) {
        this.axisY = paramFloat;
    }

    public float getAxisZ() {
        return this.axisZ;
    }

    public void setAxisZ(float paramFloat) {
        this.axisZ = paramFloat;
    }
}

