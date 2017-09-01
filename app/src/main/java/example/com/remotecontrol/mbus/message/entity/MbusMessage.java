package example.com.remotecontrol.mbus.message.entity;

import java.nio.ByteBuffer;

import example.com.remotecontrol.mbus.util.ByteUtil;

public class MbusMessage {
    private String startCharacter = "AABB";
    private byte majorVersion = 1;
    private byte minorVersion = 1;
    private byte checkFlag = 1;
    private byte encryptionFlag = 1;
    /*private byte[] reserve = new byte[16];*/
    private int msgSeq = 0;
    private String msgType;
    private int msgDataLen = 0;
    private int checkCode = 0;
    private int encryptionCode = 0;
    public static final int HEADER_LENGTH = 32;
    public static final int MAX_COMMAND_LENGTH = 1024;
    public static final byte DEFAULT_FLAG = 1;
    public static final byte IGNORE_FLAG = 0;

    public byte[] msgHeaderToBytes()
            throws Exception {
        ByteBuffer localByteBuffer = ByteBuffer.allocate(HEADER_LENGTH);
        localByteBuffer.put(getStartCharacter().getBytes());
        localByteBuffer.put(getMajorVersion());
        localByteBuffer.put(getMinorVersion());
        localByteBuffer.put(getCheckFlag());
        localByteBuffer.put(getEncryptionFlag());
        /*localByteBuffer.put(getReserve());*/
        setMsgSeq(ByteUtil.getCount());
        localByteBuffer.putInt(getMsgSeq());
        localByteBuffer.put(ByteUtil.stringToByteByLength(getMsgType(), 16));
        localByteBuffer.putInt(getMsgDataLen());
        return localByteBuffer.array();
    }

    public void bytesToMsgHeader(byte[] paramArrayOfByte)
            throws Exception {
        if (paramArrayOfByte.length < HEADER_LENGTH)
            return;
        byte[] arrayOfByte1 = new byte[4];
        byte[] arrayOfByte2 = new byte[16];
        System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, 4);
        setStartCharacter(new String(arrayOfByte1));
        setMajorVersion(paramArrayOfByte[4]);
        setMinorVersion(paramArrayOfByte[5]);
        setCheckFlag(paramArrayOfByte[6]);
        setEncryptionFlag(paramArrayOfByte[7]);
        /*System.arraycopy(paramArrayOfByte, 8, arrayOfByte2, 0, 16);
        setReserve(arrayOfByte2);*/
        arrayOfByte1 = new byte[4];
        System.arraycopy(paramArrayOfByte, 8, arrayOfByte1, 0, 4);
        setMsgSeq(ByteUtil.byteArray2int(arrayOfByte1));
        arrayOfByte2 = new byte[16];
        System.arraycopy(paramArrayOfByte, 12, arrayOfByte2, 0, 16);
        setMsgType(new String(arrayOfByte2).trim());
        arrayOfByte1 = new byte[4];
        System.arraycopy(paramArrayOfByte, 28, arrayOfByte1, 0, 4);
        setMsgDataLen(ByteUtil.byteArray2int(arrayOfByte1));
    }

    public String getStartCharacter() {
        return this.startCharacter;
    }

    public void setStartCharacter(String paramString) {
        this.startCharacter = paramString;
    }

    public byte getMajorVersion() {
        return this.majorVersion;
    }

    public void setMajorVersion(byte paramByte) {
        this.majorVersion = paramByte;
    }

    public byte getMinorVersion() {
        return this.minorVersion;
    }

    public void setMinorVersion(byte paramByte) {
        this.minorVersion = paramByte;
    }

    public byte getCheckFlag() {
        return this.checkFlag;
    }

    public void setCheckFlag(byte paramByte) {
        this.checkFlag = paramByte;
    }

    public byte getEncryptionFlag() {
        return this.encryptionFlag;
    }

    public void setEncryptionFlag(byte paramByte) {
        this.encryptionFlag = paramByte;
    }

    /*public byte[] getReserve() {
        return this.reserve;
    }

    public void setReserve(byte[] paramArrayOfByte) {
        this.reserve = paramArrayOfByte;
    }*/

    public int getMsgSeq() {
        return this.msgSeq;
    }

    public void setMsgSeq(int paramInt) {
        this.msgSeq = paramInt;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public void setMsgType(String paramString) {
        this.msgType = paramString;
    }

    public int getMsgDataLen() {
        return this.msgDataLen;
    }

    public void setMsgDataLen(int paramInt) {
        this.msgDataLen = paramInt;
    }

    public int getCheckCode() {
        return this.checkCode;
    }

    public void setCheckCode(int paramInt) {
        this.checkCode = paramInt;
    }

    public int getEncryptionCode() {
        return this.encryptionCode;
    }

    public void setEncryptionCode(int paramInt) {
        this.encryptionCode = paramInt;
    }
}

