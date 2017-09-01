package example.com.remotecontrol.mbus.util;

import java.util.Arrays;

public final class ByteUtil {
    private static int count = 0;

    public static final String byteArray2String(byte[] paramArrayOfByte) {
        String str = "";
        try {
            str = new String(paramArrayOfByte, "UTF-8");
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return str.trim();
    }

    public static final byte[] string2ByteArray(String paramString) {
        byte[] arrayOfByte = null;
        try {
            arrayOfByte = paramString.getBytes("UTF-8");
        } catch (Exception localException) {
        }
        return arrayOfByte;
    }

    public static final String byteArray2HexString(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
        for (int i = 0; i < paramArrayOfByte.length; ++i) {
            localStringBuffer.append(Character.forDigit((paramArrayOfByte[i] & 0xF0) >> 4, 16));
            localStringBuffer.append(Character.forDigit(paramArrayOfByte[i] & 0xF, 16));
            localStringBuffer.append(" ");
            if ((i <= 0) || (i % 10 != 0))
                continue;
            localStringBuffer.append("\n");
        }
        return localStringBuffer.toString();
    }

    public static final int checkStartCharacter(byte[] paramArrayOfByte, String paramString) {
        int i = -1;
        byte[] arrayOfByte1 = paramString.getBytes();
        int j = arrayOfByte1.length;
        byte[] arrayOfByte2 = new byte[j];
        for (int k = 0; k + j <= paramArrayOfByte.length; ++k) {
            System.arraycopy(paramArrayOfByte, k, arrayOfByte2, 0, j);
            if (!Arrays.equals(arrayOfByte1, arrayOfByte2))
                continue;
            i = k;
            break;
        }
        return i;
    }

    public static final byte[] stringToByteByLength(String paramString, int paramInt) {
        byte[] arrayOfByte1 = new byte[paramInt];
        if (null != paramString) {
            byte[] arrayOfByte2 = paramString.getBytes();
            if (arrayOfByte2.length > paramInt)
                System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, paramInt);
            else
                System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
        }
        return arrayOfByte1;
    }

    public static final byte[] int2byteArray(int paramInt) {
        byte[] arrayOfByte = new byte[4];
        arrayOfByte[0] = (byte) (paramInt >>> 24);
        arrayOfByte[1] = (byte) (paramInt >>> 16);
        arrayOfByte[2] = (byte) (paramInt >>> 8);
        arrayOfByte[3] = (byte) paramInt;
        return arrayOfByte;
    }

    public static final byte[] int2byteArrayLow(int paramInt) {
        byte[] arrayOfByte = new byte[4];
        arrayOfByte[3] = (byte) (paramInt >>> 24);
        arrayOfByte[2] = (byte) (paramInt >>> 16);
        arrayOfByte[1] = (byte) (paramInt >>> 8);
        arrayOfByte[0] = (byte) paramInt;
        return arrayOfByte;
    }

    public static final int byteArray2int(byte[] paramArrayOfByte) {
        byte[] arrayOfByte = new byte[4];
        int i = arrayOfByte.length - 1;
        for (int j = paramArrayOfByte.length - 1; i >= 0; --j) {
            if (j >= 0)
                arrayOfByte[i] = paramArrayOfByte[j];
            else
                arrayOfByte[i] = 0;
            --i;
        }
        int k = (arrayOfByte[0] & 0xFF) << 24;
        int l = (arrayOfByte[1] & 0xFF) << 16;
        int i1 = (arrayOfByte[2] & 0xFF) << 8;
        int i2 = arrayOfByte[3] & 0xFF;
        return k + l + i1 + i2;
    }

    public static final int byteArray2intLow(byte[] paramArrayOfByte) {
        byte[] arrayOfByte = new byte[4];
        int i = arrayOfByte.length - 1;
        for (int j = paramArrayOfByte.length - 1; i >= 0; --j) {
            if (j >= 0)
                arrayOfByte[i] = paramArrayOfByte[j];
            else
                arrayOfByte[i] = 0;
            --i;
        }
        int k = (arrayOfByte[3] & 0xFF) << 24;
        int l = (arrayOfByte[2] & 0xFF) << 16;
        int i1 = (arrayOfByte[1] & 0xFF) << 8;
        int i2 = arrayOfByte[0] & 0xFF;
        return k + l + i1 + i2;
    }

    public static final synchronized int getCount() {
        if (count < 2147483647)
            count += 1;
        else
            count = 0;
        return count;
    }
    }

