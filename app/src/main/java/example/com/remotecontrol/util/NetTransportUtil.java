package example.com.remotecontrol.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetTransportUtil {
    private static final String TAG = "NetTransportUtil";
  

    /**
     * 对str进行MD5加密
     * */
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            // return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    /**
     * 
     * 是否启用WIFI
     * @param context
     * @return boolean 
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        Log.d(TAG, "isWifiDisable" + wifiManager.isWifiEnabled());
        return wifiManager.isWifiEnabled();
    }
    /**
     * 是否连接WIFI
     */
    public static boolean isWifiConnected(Context mContext) {
		// 获取WifiManager对象
        //		WifiManager mWifiManager = (WifiManager) mContext
        //				.getSystemService(Context.WIFI_SERVICE);
		//return mWifiManager.isWifiEnabled();
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo.isConnected();
		
	}
    
    /*public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d(TAG, "wifi status:" + wifiNetworkInfo.getState());
        if (wifiNetworkInfo.isConnected()) {
           // return true;
        }

       // return false;
    	return isNetworkConnected(context);
    }
    
    public static boolean isNetworkConnected(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
                    .getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
            if (mNetworkInfo != null) {  
            	 Log.d(TAG, "wifi isAvailable:" + mNetworkInfo.isAvailable());
                return mNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }*/
    
    public static String getIpAdress() throws SocketException {
        String localIp = null;
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                .hasMoreElements();) {
            NetworkInterface intf = en.nextElement();
            if (intf.getDisplayName().contains("wlan")) {
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        if (!inetAddress.isLoopbackAddress()) {
                            localIp = inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
        }
        return localIp;
    }
    
    /**
	 * 获取手机的本地ip地址.
	 * @return 本地ip地址
	 * @throws SocketException
	 */
	public static InetAddress getInetAddress() throws SocketException {
		InetAddress IP = null;
		for (Enumeration<NetworkInterface> en = NetworkInterface
				.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			if (intf.getDisplayName().contains("wlan") || intf.getDisplayName().contains("eth")) {
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (inetAddress instanceof Inet4Address) {
						if (!inetAddress.isLoopbackAddress()) {
							IP = inetAddress;
						}
					}
				}
			}
		}
		return IP;
	}
    
    public static InetAddress getLocalInetAddress() throws SocketException {
    	InetAddress localInetAddress = null;
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                .hasMoreElements();) {
            NetworkInterface intf = en.nextElement();
            if (intf.getDisplayName().contains("wlan")) {
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        if (!inetAddress.isLoopbackAddress()) {
                            localInetAddress = inetAddress;
                        }
                    }
                }
            }
        }
        return localInetAddress;
    }
}