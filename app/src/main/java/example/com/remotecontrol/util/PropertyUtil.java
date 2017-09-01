package example.com.remotecontrol.util;

import android.util.Log;

import java.lang.reflect.Method;

public class PropertyUtil {
    private static final String TAG = "PropertyUtil";

    /**
     * 根据键值获取参数值.
     * @param key 键值
     * @param defaultvalue 默认值，键值无法查询到时使用该值
     * @return 键值查询到参数值
     */
    public static String getProperties(String key, String defaultvalue) {
        String Value = null;
        Class<?> SystemPropertiesClass = null;
        Method method = null;

        try {

            SystemPropertiesClass = Class.forName("android.os.SystemProperties");
            Log.i(TAG, "SystemPropertiesClass:" + SystemPropertiesClass);
            
            Class<?> getType[] = new Class[2];
            getType[0] = String.class;
            getType[1] = String.class;
            method = SystemPropertiesClass.getMethod("get", getType);

            Object arglist[] = new Object[2];
            arglist[0] = key;
            arglist[1] = defaultvalue;

            Object receiver = new Object();

            Object returnvalue = method.invoke(receiver, arglist);
            Log.w(TAG, "return value:" + returnvalue);
            if (receiver != null) {
                Value = (String) returnvalue;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Can not find class");
        }
        return Value;
    }

}
