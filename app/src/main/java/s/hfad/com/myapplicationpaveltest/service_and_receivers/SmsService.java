package s.hfad.com.myapplicationpaveltest.service_and_receivers;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import s.hfad.com.myapplicationpaveltest.HomePage;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.loadOutIn.SerializableFile;

public class SmsService extends IntentService {
    private SerializableFile mSerializableFile;
    private  String senderSMS;
    public SmsService() {
        super("SMS Service");
        mSerializableFile = new SerializableFile(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        senderSMS = mSerializableFile.inPut();
        if (senderSMS==null)senderSMS = "";
        String[] messages  = null;
        if (intent != null) {
            messages = (String[]) intent.getExtras().get(SMSMonitor.KEY_SMS_INTENT);
            checkMS(messages);
        }
    }

    private void checkMS(String[] messages) {
        if (messages[0].equals(senderSMS)){
            showNotification(messages[1]);
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(String text){
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomePage.class), 0);
        Context context = getApplicationContext();
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("$")
                .setContentText(text)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.getNotification();
        if (notificationManager != null) {
            notificationManager.notify(R.drawable.common_full_open_on_phone, notification);
        }
    }
}
