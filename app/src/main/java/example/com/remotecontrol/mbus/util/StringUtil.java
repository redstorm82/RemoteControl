package example.com.remotecontrol.mbus.util;

public class StringUtil {
    public static String defaultString(String paramString) {
        return (paramString == null) ? "" : paramString;
    }

    public static boolean isEmpty(String paramString) {
        return (paramString == null) || ("".equals(paramString));
    }

    public static boolean isNotEmpty(String paramString) {
        return (paramString != null) && (!"".equals(paramString));
    }
}

