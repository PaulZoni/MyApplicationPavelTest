package s.hfad.com.myapplicationpaveltest.service_and_receivers;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import s.hfad.com.myapplicationpaveltest.activity.HomePage;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.model_assets.AssetsModel;
import s.hfad.com.myapplicationpaveltest.model_assets.Graph;
import s.hfad.com.myapplicationpaveltest.model_assets.load_outIn.SerializableFile;

public class SmsService extends IntentService {
    private SerializableFile mSerializableFile;
    private  String senderSMS;
    private AssetsModel mAssetsModel;

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
            checkSMS(messages);
        }
    }

    private void checkSMS(String[] messages) {
        if (messages[0].equals(senderSMS)){
            showNotification(messages[1]);
            try {
                checkExpensesOrAsserts(messages[1]);
            }catch (Exception e){
                Log.e("checkExpensesOrAsserts","Error in the function checkExpensesOrAsserts");
            }
        }
    }

    private void checkExpensesOrAsserts(String senderSMS){

        float existBalance = existBalanceCheck(senderSMS);
        Graph graph = new Graph(this,"");
        graph.getGraph().subscribe(stringFloatHashMap -> {
            String key = null;
            float sum = 0;
            String comment = null;
            if (stringFloatHashMap.get(Graph.KEY_EXIST_ASSETS) < existBalance){
                sum = existBalance - stringFloatHashMap.get(Graph.KEY_EXIST_ASSETS);
                 key = AssetsModel.KEY_ASSETS;
                comment = "Пришло";
            }else if (stringFloatHashMap.get(Graph.KEY_EXIST_ASSETS) > existBalance){
                sum = stringFloatHashMap.get(Graph.KEY_EXIST_ASSETS) - existBalance;
                key = AssetsModel.KEY_EXPENSES;
                comment = "Списано";
            }

            mAssetsModel = new AssetsModel(this, key);
            Integer sumCastInt = (int) sum;
            mAssetsModel.buttonOk(String.valueOf(sumCastInt), comment);
        });
    }

    private float existBalanceCheck(String statSms){
        Pattern pattern = Pattern.compile("Баланс:");
        Matcher matcher = pattern.matcher(statSms);
        if (matcher.find()) {
            String[] smsWord = statSms.split(" ");
            String sum = smsWord[smsWord.length - 1];
            String sum$ = sum.substring(0, sum.length() - 1);
            return Float.parseFloat(sum$);
        }
        return 0;
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
