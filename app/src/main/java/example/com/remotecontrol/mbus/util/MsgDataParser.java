package example.com.remotecontrol.mbus.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class MsgDataParser {
    private static final int KEY_VALUE_ARRAY_MAX_LENGTH = 2;
    private static final int KEY_VALUE_ARRAY_MIN_LENGTH = 1;

    public static Map<String, String> parserMsgDataByTokenizer(String paramString) {
        HashMap localHashMap = new HashMap();
        if (paramString != null) {
            StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, "&");
            while (localStringTokenizer1.hasMoreElements()) {
                String str1 = localStringTokenizer1.nextToken();
                StringTokenizer localStringTokenizer2 = new StringTokenizer(str1, "=");
                if (localStringTokenizer2.hasMoreElements()) {
                    String str2 = localStringTokenizer2.nextToken();
                    if (localStringTokenizer2.hasMoreElements())
                        localHashMap.put(str2, localStringTokenizer2.nextToken());
                    else
                        localHashMap.put(str2, "");
                }
            }
        }
        return localHashMap;
    }

    public static Map<String, String> parserMsgDataByPattern(String paramString) {
        HashMap localHashMap = new HashMap();
        if (paramString != null) {
            Pattern localPattern = Pattern.compile("&");
            String[] arrayOfString1 = localPattern.split(paramString);
            for (String str : arrayOfString1) {
                if (str == null)
                    continue;
                String[] arrayOfString3 = str.split("=");
                if (arrayOfString3.length == 2) {
                    localHashMap.put(arrayOfString3[0], arrayOfString3[1]);
                } else {
                    if (arrayOfString3.length != 1)
                        continue;
                    localHashMap.put(arrayOfString3[0], "");
                }
            }
        }
        return localHashMap;
    }

    public static Map<String, String> parserMsgDataBySplit(String paramString) {
        HashMap localHashMap = new HashMap();
        if (paramString != null) {
            String[] arrayOfString1 = paramString.split("&");
            for (String str : arrayOfString1) {
                if (str == null)
                    continue;
                String[] arrayOfString3 = str.split("=");
                if (arrayOfString3.length == 2) {
                    localHashMap.put(arrayOfString3[0], arrayOfString3[1]);
                } else {
                    if (arrayOfString3.length != 1)
                        continue;
                    localHashMap.put(arrayOfString3[0], "");
                }
            }
        }
        return localHashMap;
    }
}
