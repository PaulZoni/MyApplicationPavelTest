package s.hfad.com.myapplicationpaveltest.service_and_receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SMSMonitor extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String KEY_SMS_INTENT = "sms_body";
    private Context mContext;
    private String senderSMS;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (intent != null /*&& intent.getAction() != null && ACTION.compareToIgnoreCase(intent.getAction()) == 0*/){
            Object[]objectsPDU = (Object[]) intent.getExtras().get("pdus");
            if (objectsPDU!=null){
                SmsMessage[] messages = new SmsMessage[objectsPDU.length];
                for (int i = 0; i <objectsPDU.length ; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) objectsPDU[i]);
                }
                checkMessage(messages);
            }else System.out.println("SmsMessage[] equals null");

        }

    }

    private void checkMessage(SmsMessage[] messages) {
        String sms_from = messages[0].getDisplayOriginatingAddress();
        //if (sms_from.equals(senderSMS)){
            StringBuilder bodyText = new StringBuilder();
            for (int i = 0; i <messages.length ; i++) {
                bodyText.append(messages[i].getMessageBody());
            }
            String body = bodyText.toString();
            Intent mIntent = new Intent(mContext,SmsService.class);
            mIntent.putExtra(KEY_SMS_INTENT, body);
            mContext.startService(mIntent);
        //}

    }
}
