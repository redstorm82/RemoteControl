package example.com.remotecontrol.mcontrol.inputcontrol.entity;

public class RotationVectorSensorEntity {
    private float axisX = 0.0F;
    private float axisY = 0.0F;
    private float axisZ = 0.0F;
    private float numerValue = 0.0F;

    public RotationVectorSensorEntity() {
    }

    public RotationVectorSensorEntity(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        this.axisX = paramFloat1;
        this.axisY = paramFloat2;
        this.axisZ = paramFloat3;
        this.numerValue = paramFloat4;
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

    public float getNumerValue() {
        return this.numerValue;
    }

    public void setNumerValue(float paramFloat) {
        this.numerValue = paramFloat;
    }
}

