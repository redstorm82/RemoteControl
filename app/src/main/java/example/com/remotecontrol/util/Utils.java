package example.com.remotecontrol.util;

/**
 * 
 * @author hailongqiu 356752238@qq.com
 *
 */
public class Utils {
	
	/**
	 * 获取SDK版本
	 */
	public static int getSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
		} catch (NumberFormatException e) {
		}
		return version;
	}

}
