package example.com.remotecontrol.util;

import android.telephony.SmsManager;

/**
 * Created by lenovo on 2016/7/29.
 */
public class Sms {
    public static void sendSmsMsg(String number, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,null, message, null, null);
        } catch (Exception e){
            e.printStackTrace();
            new Throwable("sendSmsMsg failed");
        }
    }
}
