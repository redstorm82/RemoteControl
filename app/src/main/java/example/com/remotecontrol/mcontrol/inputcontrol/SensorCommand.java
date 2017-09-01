package example.com.remotecontrol.mcontrol.inputcontrol;

import java.util.Map;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mbus.util.MsgDataParser;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.AccelerationSensorEntity;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.GravitySensorEntity;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.GyroscopeSensorEntity;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.LinearAccelerationSensorEntity;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.OrientationSensorEntity;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.RotationVectorSensorEntity;
import example.com.remotecontrol.mcontrol.inputcontrol.entity.SensorType;

public class SensorCommand extends AbstractMbusCommand {
    private int dataType = 0;
    private int sensorType = 0;
    private int accuracy = 0;
    private AccelerationSensorEntity accelerationSensorEntity = null;
    private OrientationSensorEntity orientationSensorEntity = null;
    private GyroscopeSensorEntity gyroscopeSensorEntity = null;
    private GravitySensorEntity gravitySensorEntity = null;
    private LinearAccelerationSensorEntity linearAccelerationSensorEntity = null;
    private RotationVectorSensorEntity rotationVectorSensorEntity = null;

    public SensorCommand() {
    }

    public SensorCommand(int paramInt1, int paramInt2, int paramInt3) {
        this.dataType = paramInt1;
        this.sensorType = paramInt2;
        this.accuracy = paramInt3;
    }

    public SensorCommand(int paramInt1, int paramInt2, AccelerationSensorEntity paramAccelerationSensorEntity) {
        this.dataType = paramInt1;
        this.sensorType = 1;
        this.accuracy = paramInt2;
        this.accelerationSensorEntity = paramAccelerationSensorEntity;
    }

    public SensorCommand(AccelerationSensorEntity paramAccelerationSensorEntity) {
        this.sensorType = 1;
        this.accelerationSensorEntity = paramAccelerationSensorEntity;
    }

    public SensorCommand(int paramInt1, int paramInt2, OrientationSensorEntity paramOrientationSensorEntity) {
        this.dataType = paramInt1;
        this.sensorType = 3;
        this.accuracy = paramInt2;
        this.orientationSensorEntity = paramOrientationSensorEntity;
    }

    public SensorCommand(OrientationSensorEntity paramOrientationSensorEntity) {
        this.sensorType = 3;
        this.orientationSensorEntity = paramOrientationSensorEntity;
    }

    public SensorCommand(int paramInt1, int paramInt2, GyroscopeSensorEntity paramGyroscopeSensorEntity) {
        this.dataType = paramInt1;
        this.sensorType = 4;
        this.accuracy = paramInt2;
        this.gyroscopeSensorEntity = paramGyroscopeSensorEntity;
    }

    public SensorCommand(GyroscopeSensorEntity paramGyroscopeSensorEntity) {
        this.sensorType = 4;
        this.gyroscopeSensorEntity = paramGyroscopeSensorEntity;
    }

    public SensorCommand(int paramInt1, int paramInt2, GravitySensorEntity paramGravitySensorEntity) {
        this.dataType = paramInt1;
        this.sensorType = 9;
        this.accuracy = paramInt2;
        this.gravitySensorEntity = paramGravitySensorEntity;
    }

    public SensorCommand(GravitySensorEntity paramGravitySensorEntity) {
        this.sensorType = 9;
        this.gravitySensorEntity = paramGravitySensorEntity;
    }

    public SensorCommand(int paramInt1, int paramInt2, LinearAccelerationSensorEntity paramLinearAccelerationSensorEntity) {
        this.dataType = paramInt1;
        this.sensorType = 10;
        this.accuracy = paramInt2;
        this.linearAccelerationSensorEntity = paramLinearAccelerationSensorEntity;
    }

    public SensorCommand(LinearAccelerationSensorEntity paramLinearAccelerationSensorEntity) {
        this.sensorType = 10;
        this.linearAccelerationSensorEntity = paramLinearAccelerationSensorEntity;
    }

    public SensorCommand(int paramInt1, int paramInt2, RotationVectorSensorEntity paramRotationVectorSensorEntity) {
        this.dataType = paramInt1;
        this.sensorType = 11;
        this.accuracy = paramInt2;
        this.rotationVectorSensorEntity = paramRotationVectorSensorEntity;
    }

    public SensorCommand(RotationVectorSensorEntity paramRotationVectorSensorEntity) {
        this.sensorType = 11;
        this.rotationVectorSensorEntity = paramRotationVectorSensorEntity;
    }

    public byte[] dataToBytes()
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("datatype").append("=").append(getDataType());
        localStringBuilder.append("&");
        localStringBuilder.append("sensortype").append("=").append(getSensorType());
        localStringBuilder.append("&");
        localStringBuilder.append("accuracy").append("=").append(getAccuracy());
        switch (getSensorType()) {
            case 1:
                localStringBuilder.append("&");
                localStringBuilder.append(encodeAccelerationMsgData(getAccelerationSensorEntity()));
                break;
            case 3:
                localStringBuilder.append("&");
                localStringBuilder.append(encodeOrientationMsgData(getOrientationSensorEntity()));
                break;
            case 4:
                localStringBuilder.append("&");
                localStringBuilder.append(encodeGyroscopeMsgData(getGyroscopeSensorEntity()));
                break;
            case 9:
                localStringBuilder.append("&");
                localStringBuilder.append(encodeGravityMsgData(getGravitySensorEntity()));
                break;
            case 10:
                localStringBuilder.append("&");
                localStringBuilder.append(encodeLinearAccelerationMsgData(getLinearAccelerationSensorEntity()));
                break;
            case 11:
                localStringBuilder.append("&");
                localStringBuilder.append(encodeRotationVectorMsgData(getRotationVectorSensorEntity()));
            case 2:
            case 5:
            case 6:
            case 7:
            case 8:
        }
        byte[] arrayOfByte = new byte[0];
        if (null != localStringBuilder)
            arrayOfByte = localStringBuilder.toString().getBytes();
        return arrayOfByte;
    }

    public void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception {
        if (null == paramArrayOfByte)
            return;
        String str = new String(paramArrayOfByte);
        Map localMap = MsgDataParser.parserMsgDataByTokenizer(str);
        if ((null == localMap) || (localMap.size() == 0))
            return;
        try {
            if (null != localMap.get("datatype"))
                setDataType(Integer.parseInt((String) localMap.get("datatype")));
            if (null != localMap.get("sensortype"))
                setSensorType(Integer.parseInt((String) localMap.get("sensortype")));
            if (null != localMap.get("accuracy"))
                setAccuracy(Integer.parseInt((String) localMap.get("accuracy")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }


        switch (getSensorType()) {
            case SensorType.SENSOR_TYPE_ACCELERATION:
                this.accelerationSensorEntity = decodeAccelerationMsgData(localMap);
                break;
            case SensorType.SENSOR_TYPE_ORIENTATION:
                this.orientationSensorEntity = decodeOrientationMsgData(localMap);
                break;
            case SensorType.SENSOR_TYPE_GYROSCOPE:
                this.gyroscopeSensorEntity = decodeGyroscopeMsgData(localMap);
                break;
            case SensorType.SENSOR_TYPE_GRAVITY:
                this.gravitySensorEntity = decodeGravityMsgData(localMap);
                break;
            case SensorType.SENSOR_TYPE_LINEAR_ACCELERATION:
                this.linearAccelerationSensorEntity = decodeLinearAccelerationMsgData(localMap);
                break;
            case SensorType.SENSOR_TYPE_ROTATION_VECTOR:
                this.rotationVectorSensorEntity = decodeRotationVectorMsgData(localMap);
            case 2:
            case 5:
            case 6:
            case 7:
            case 8:
                default:
                    break;
        }
    }

    public String getCurrentMsgType() {
        return MbusMessageType.MSGTYPE_REMOTE_SENSOR;
    }

    public int getCurrentMsgDataLen() {
        try {
            return dataToBytes().length;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    private StringBuilder encodeAccelerationMsgData(AccelerationSensorEntity paramAccelerationSensorEntity) {
        StringBuilder localStringBuilder = new StringBuilder();
        if (null != paramAccelerationSensorEntity) {
            localStringBuilder.append("x").append("=").append(paramAccelerationSensorEntity.getAx());
            localStringBuilder.append("&");
            localStringBuilder.append("y").append("=").append(paramAccelerationSensorEntity.getAy());
            localStringBuilder.append("&");
            localStringBuilder.append("z").append("=").append(paramAccelerationSensorEntity.getAz());
        }
        return localStringBuilder;
    }

    private AccelerationSensorEntity decodeAccelerationMsgData(Map<String, String> paramMap) {
        if ((null == paramMap) || (paramMap.size() == 0))
            return null;
        AccelerationSensorEntity localAccelerationSensorEntity = new AccelerationSensorEntity();
        try {
            if (null != paramMap.get("x"))
                localAccelerationSensorEntity.setAx(Float.parseFloat((String) paramMap.get("x")));
            if (null != paramMap.get("y"))
                localAccelerationSensorEntity.setAy(Float.parseFloat((String) paramMap.get("y")));
            if (null != paramMap.get("z"))
                localAccelerationSensorEntity.setAz(Float.parseFloat((String) paramMap.get("z")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
        return localAccelerationSensorEntity;
    }

    private StringBuilder encodeOrientationMsgData(OrientationSensorEntity paramOrientationSensorEntity) {
        StringBuilder localStringBuilder = new StringBuilder();
        if (null != paramOrientationSensorEntity) {
            localStringBuilder.append("azimuth").append("=").append(paramOrientationSensorEntity.getAzimuth());
            localStringBuilder.append("&");
            localStringBuilder.append("pitch").append("=").append(paramOrientationSensorEntity.getPitch());
            localStringBuilder.append("&");
            localStringBuilder.append("roll").append("=").append(paramOrientationSensorEntity.getRoll());
        }
        return localStringBuilder;
    }

    private OrientationSensorEntity decodeOrientationMsgData(Map<String, String> paramMap) {
        if ((null == paramMap) || (paramMap.size() == 0))
            return null;
        OrientationSensorEntity localOrientationSensorEntity = new OrientationSensorEntity();
        try {
            if (null != paramMap.get("azimuth"))
                localOrientationSensorEntity.setAzimuth(Float.parseFloat((String) paramMap.get("azimuth")));
            if (null != paramMap.get("pitch"))
                localOrientationSensorEntity.setPitch(Float.parseFloat((String) paramMap.get("pitch")));
            if (null != paramMap.get("roll"))
                localOrientationSensorEntity.setRoll(Float.parseFloat((String) paramMap.get("roll")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
        return localOrientationSensorEntity;
    }

    private StringBuilder encodeGyroscopeMsgData(GyroscopeSensorEntity paramGyroscopeSensorEntity) {
        StringBuilder localStringBuilder = new StringBuilder();
        if (null != paramGyroscopeSensorEntity) {
            localStringBuilder.append("x").append("=").append(paramGyroscopeSensorEntity.getAxisX());
            localStringBuilder.append("&");
            localStringBuilder.append("y").append("=").append(paramGyroscopeSensorEntity.getAxisY());
            localStringBuilder.append("&");
            localStringBuilder.append("z").append("=").append(paramGyroscopeSensorEntity.getAxisZ());
        }
        return localStringBuilder;
    }

    private GyroscopeSensorEntity decodeGyroscopeMsgData(Map<String, String> paramMap) {
        if ((null == paramMap) || (paramMap.size() == 0))
            return null;
        GyroscopeSensorEntity localGyroscopeSensorEntity = new GyroscopeSensorEntity();
        try {
            if (null != paramMap.get("x"))
                localGyroscopeSensorEntity.setAxisX(Float.parseFloat((String) paramMap.get("x")));
            if (null != paramMap.get("y"))
                localGyroscopeSensorEntity.setAxisY(Float.parseFloat((String) paramMap.get("y")));
            if (null != paramMap.get("z"))
                localGyroscopeSensorEntity.setAxisZ(Float.parseFloat((String) paramMap.get("z")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
        return localGyroscopeSensorEntity;
    }

    private StringBuilder encodeGravityMsgData(GravitySensorEntity paramGravitySensorEntity) {
        StringBuilder localStringBuilder = new StringBuilder();
        if (null != paramGravitySensorEntity) {
            localStringBuilder.append("x").append("=").append(paramGravitySensorEntity.getAxisX());
            localStringBuilder.append("&");
            localStringBuilder.append("y").append("=").append(paramGravitySensorEntity.getAxisY());
            localStringBuilder.append("&");
            localStringBuilder.append("z").append("=").append(paramGravitySensorEntity.getAxisZ());
        }
        return localStringBuilder;
    }

    private GravitySensorEntity decodeGravityMsgData(Map<String, String> paramMap) {
        if ((null == paramMap) || (paramMap.size() == 0))
            return null;
        GravitySensorEntity localGravitySensorEntity = new GravitySensorEntity();
        try {
            if (null != paramMap.get("x"))
                localGravitySensorEntity.setAxisX(Float.parseFloat((String) paramMap.get("x")));
            if (null != paramMap.get("y"))
                localGravitySensorEntity.setAxisY(Float.parseFloat((String) paramMap.get("y")));
            if (null != paramMap.get("z"))
                localGravitySensorEntity.setAxisZ(Float.parseFloat((String) paramMap.get("z")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
        return localGravitySensorEntity;
    }

    private StringBuilder encodeLinearAccelerationMsgData(LinearAccelerationSensorEntity paramLinearAccelerationSensorEntity) {
        StringBuilder localStringBuilder = new StringBuilder();
        if (null != paramLinearAccelerationSensorEntity) {
            localStringBuilder.append("x").append("=").append(paramLinearAccelerationSensorEntity.getAxisX());
            localStringBuilder.append("&");
            localStringBuilder.append("y").append("=").append(paramLinearAccelerationSensorEntity.getAxisY());
            localStringBuilder.append("&");
            localStringBuilder.append("z").append("=").append(paramLinearAccelerationSensorEntity.getAxisZ());
        }
        return localStringBuilder;
    }

    private LinearAccelerationSensorEntity decodeLinearAccelerationMsgData(Map<String, String> paramMap) {
        if ((null == paramMap) || (paramMap.size() == 0))
            return null;
        LinearAccelerationSensorEntity localLinearAccelerationSensorEntity = new LinearAccelerationSensorEntity();
        try {
            if (null != paramMap.get("x"))
                localLinearAccelerationSensorEntity.setAxisX(Float.parseFloat((String) paramMap.get("x")));
            if (null != paramMap.get("y"))
                localLinearAccelerationSensorEntity.setAxisY(Float.parseFloat((String) paramMap.get("y")));
            if (null != paramMap.get("z"))
                localLinearAccelerationSensorEntity.setAxisZ(Float.parseFloat((String) paramMap.get("z")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
        return localLinearAccelerationSensorEntity;
    }

    private StringBuilder encodeRotationVectorMsgData(RotationVectorSensorEntity paramRotationVectorSensorEntity) {
        StringBuilder localStringBuilder = new StringBuilder();
        if (null != paramRotationVectorSensorEntity) {
            localStringBuilder.append("x").append("=").append(paramRotationVectorSensorEntity.getAxisX());
            localStringBuilder.append("&");
            localStringBuilder.append("y").append("=").append(paramRotationVectorSensorEntity.getAxisY());
            localStringBuilder.append("&");
            localStringBuilder.append("z").append("=").append(paramRotationVectorSensorEntity.getAxisZ());
            localStringBuilder.append("&");
            localStringBuilder.append("value").append("=").append(paramRotationVectorSensorEntity.getNumerValue());
        }
        return localStringBuilder;
    }

    private RotationVectorSensorEntity decodeRotationVectorMsgData(Map<String, String> paramMap) {
        if ((null == paramMap) || (paramMap.size() == 0))
            return null;
        RotationVectorSensorEntity localRotationVectorSensorEntity = new RotationVectorSensorEntity();
        try {
            if (null != paramMap.get("x"))
                localRotationVectorSensorEntity.setAxisX(Float.parseFloat((String) paramMap.get("x")));
            if (null != paramMap.get("y"))
                localRotationVectorSensorEntity.setAxisY(Float.parseFloat((String) paramMap.get("y")));
            if (null != paramMap.get("z"))
                localRotationVectorSensorEntity.setAxisZ(Float.parseFloat((String) paramMap.get("z")));
            if (null != paramMap.get("value"))
                localRotationVectorSensorEntity.setNumerValue(Float.parseFloat((String) paramMap.get("value")));
        } catch (NumberFormatException localNumberFormatException) {
            localNumberFormatException.getStackTrace();
        }
        return localRotationVectorSensorEntity;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int paramInt) {
        this.dataType = paramInt;
    }

    public int getSensorType() {
        return this.sensorType;
    }

    public void setSensorType(int paramInt) {
        this.sensorType = paramInt;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(int paramInt) {
        this.accuracy = paramInt;
    }

    public AccelerationSensorEntity getAccelerationSensorEntity() {
        return this.accelerationSensorEntity;
    }

    public void setAccelerationSensorEntity(AccelerationSensorEntity paramAccelerationSensorEntity) {
        this.accelerationSensorEntity = paramAccelerationSensorEntity;
    }

    public OrientationSensorEntity getOrientationSensorEntity() {
        return this.orientationSensorEntity;
    }

    public void setOrientationSensorEntity(OrientationSensorEntity paramOrientationSensorEntity) {
        this.orientationSensorEntity = paramOrientationSensorEntity;
    }

    public GyroscopeSensorEntity getGyroscopeSensorEntity() {
        return this.gyroscopeSensorEntity;
    }

    public void setGyroscopeSensorEntity(GyroscopeSensorEntity paramGyroscopeSensorEntity) {
        this.gyroscopeSensorEntity = paramGyroscopeSensorEntity;
    }

    public GravitySensorEntity getGravitySensorEntity() {
        return this.gravitySensorEntity;
    }

    public void setGravitySensorEntity(GravitySensorEntity paramGravitySensorEntity) {
        this.gravitySensorEntity = paramGravitySensorEntity;
    }

    public LinearAccelerationSensorEntity getLinearAccelerationSensorEntity() {
        return this.linearAccelerationSensorEntity;
    }

    public void setLinearAccelerationSensorEntity(LinearAccelerationSensorEntity paramLinearAccelerationSensorEntity) {
        this.linearAccelerationSensorEntity = paramLinearAccelerationSensorEntity;
    }

    public RotationVectorSensorEntity getRotationVectorSensorEntity() {
        return this.rotationVectorSensorEntity;
    }

    public void setRotationVectorSensorEntity(RotationVectorSensorEntity paramRotationVectorSensorEntity) {
        this.rotationVectorSensorEntity = paramRotationVectorSensorEntity;
    }
}

