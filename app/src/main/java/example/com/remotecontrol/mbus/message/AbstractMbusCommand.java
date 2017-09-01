package example.com.remotecontrol.mbus.message;

import java.nio.ByteBuffer;

import example.com.remotecontrol.mbus.message.entity.MbusMessage;
import example.com.remotecontrol.mbus.util.ByteUtil;
import example.com.remotecontrol.util.OPENLOG;

public abstract class AbstractMbusCommand extends MbusMessage
        implements IMbusCommand {
    private static final String TAG = "AbstractMbusCommand";

    public abstract byte[] dataToBytes()
            throws Exception;

    public abstract void dataFromBytes(byte[] paramArrayOfByte)
            throws Exception;

    public abstract String getCurrentMsgType();

    public abstract int getCurrentMsgDataLen();

    public void setMessage() {
        setMsgType(getCurrentMsgType());
    }

    public byte[] toBytes() throws Exception {
        this.setMessage();
        Object var1 = null;
        byte var2 = 8;
        byte[] var3 = this.dataToBytes();
        this.setMsgDataLen(var3.length);
        int var4 = MbusMessage.HEADER_LENGTH + var3.length;
        byte[] var7 = new byte[var4 + var2];
        System.arraycopy(this.msgHeaderToBytes(), 0, var7, 0, MbusMessage.HEADER_LENGTH);
        System.arraycopy(var3, 0, var7, MbusMessage.HEADER_LENGTH, var3.length);
        if (1 == this.getCheckFlag()) {
            byte[] var5 = new byte[var4];
            System.arraycopy(var7, 0, var5, 0, var4);
            int var6 = MbusUtil.getCRC32Value(var5);
            this.setCheckCode(var6);
        }

        if (1 == this.getEncryptionFlag()) {
            this.setEncryptionCode(MbusUtil.converInt(this.getCheckCode()));
        }

        ByteBuffer var8 = ByteBuffer.allocate(var2);
        var8.putInt(this.getCheckCode());
        var8.putInt(this.getEncryptionCode());
        System.arraycopy(var8.array(), 0, var7, var4, 8);
        return var7;
    }

    public byte[] getMsgDataBytes(byte[] paramArrayOfByte)
            throws Exception {
        byte[] arrayOfByte1 = new byte[0];
        if ((null == paramArrayOfByte) || (paramArrayOfByte.length < MbusMessage.HEADER_LENGTH ))
            return arrayOfByte1;
        byte[] arrayOfByte2 = new byte[MbusMessage.HEADER_LENGTH ];
        System.arraycopy(paramArrayOfByte, 0, arrayOfByte2, 0, MbusMessage.HEADER_LENGTH );
        bytesToMsgHeader(arrayOfByte2);
        int i = MbusMessage.HEADER_LENGTH  + getMsgDataLen();
        if (paramArrayOfByte.length >= i + 8) {
            int j = 0;
            int k = 0;
            byte[] arrayOfByte3 = new byte[4];
            System.arraycopy(paramArrayOfByte, i, arrayOfByte3, 0, 4);
            setCheckCode(ByteUtil.byteArray2int(arrayOfByte3));
            System.arraycopy(paramArrayOfByte, i + 4, arrayOfByte3, 0, 4);
            setEncryptionCode(ByteUtil.byteArray2int(arrayOfByte3));
            if (0 != getCheckFlag()) {
                if (1 == getCheckFlag()) {
                    byte[] arrayOfByte4 = new byte[i];
                    System.arraycopy(paramArrayOfByte, 0, arrayOfByte4, 0, i);
                    j = MbusUtil.getCRC32Value(arrayOfByte4);
                }
                if (j != getCheckCode()) {
                    OPENLOG.I("AbstractMbusCommand check code error");
                    return null;
                }
            } else {
                OPENLOG.I("AbstractMbusCommand check code is 0 ,return null");
                return null;
            }
            if (0 != getEncryptionFlag()) {
                if (1 == getEncryptionFlag())
                    k = MbusUtil.converInt(j);
                if (k != getEncryptionCode()) {
                    OPENLOG.I("AbstractMbusCommand encryption code error");
                    return null;
                }
            } else {
                OPENLOG.I("AbstractMbusCommand encryption code is 0 ,return null");
                return null;
            }
        }
        if (paramArrayOfByte.length > i) {
            arrayOfByte1 = new byte[getMsgDataLen()];
            System.arraycopy(paramArrayOfByte, MbusMessage.HEADER_LENGTH , arrayOfByte1, 0, getMsgDataLen());
        }
        return arrayOfByte1;
    }

    public String toString() {
        String str = null;
        try {
            byte[] arrayOfByte = toBytes();
            str = ByteUtil.byteArray2HexString(arrayOfByte);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return str;
    }
}
