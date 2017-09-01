package example.com.remotecontrol.multiscreen.mdnsdevice;

import example.com.remotecontrol.multiscreen.remote.OtODeviceAdapter;

public class MdnsDevice extends Device {

    private OtODeviceAdapter mAdapter;

    private String mUUID = "11223344";
    public static final int TTL_DEVICE = 37 * 1000;
    /**
     * 生存时间，单位：毫秒.
     */
    private int mTTL;
    /**
     * 创建时间，单位：毫秒.
     */
    private long mCreatedTime;


    public MdnsDevice(String name, String ip) {
        super(name, ip, null);
        this.setMdns(true);
        this.setmTTL(TTL_DEVICE);
        this.setmCreatedTime(System.currentTimeMillis());
        mAdapter = OtODeviceAdapter.create();
    }

    public MdnsDevice(String name, String ip, String mac) {
        super(name, ip, mac);
        this.setMdns(true);
        this.setmTTL(TTL_DEVICE);
        this.setmCreatedTime(System.currentTimeMillis());
        mAdapter = OtODeviceAdapter.create();
    }

    public OtODeviceAdapter adapter() {
        if (mAdapter == null) {
            mAdapter = OtODeviceAdapter.create();
        }
        return mAdapter;
    }

    public String getmUUID() {
        return mUUID;
    }

    public void setmUUID(String mUUID) {
        this.mUUID = mUUID;
    }

    public int getmTTL() {
        return mTTL;
    }

    public void setmTTL(int mTTL) {
        this.mTTL = mTTL;
    }

    public long getmCreatedTime() {
        return mCreatedTime;
    }

    public void setmCreatedTime(long mCreatedTime) {
        this.mCreatedTime = mCreatedTime;
    }

    public void release() {
        if (mAdapter != null) {
            mAdapter.releaseRes();
            mAdapter = null;
        }
    }

}
