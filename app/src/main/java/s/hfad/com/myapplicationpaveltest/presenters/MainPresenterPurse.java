package s.hfad.com.myapplicationpaveltest.presenters;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import s.hfad.com.myapplicationpaveltest.Interface.IViewPurse;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.view.MainActivityList;
import s.hfad.com.myapplicationpaveltest.model_assets.AssetsModel;
import s.hfad.com.myapplicationpaveltest.model_assets.Sound;
import s.hfad.com.myapplicationpaveltest.model_assets.Transaction;
import s.hfad.com.myapplicationpaveltest.model_assets.load_outIn.SerializableFile;


public class MainPresenterPurse {

    private final String STRING_WRONGS = "fill in the field";
    private final Handler handler;
    private IViewPurse view;
    private AssetsModel assetsModel;
    private Context context;


    public MainPresenterPurse(IViewPurse view, Context context, String key) {
        this.view = view;
        this.context = context;
        assetsModel = new AssetsModel(context, key);
        handler = new MyHandler(this);
    }


    public void buttonOnClick(View v){
        switch (v.getId()){
            case R.id.actionButtonAdd:
                view.dialogAdd();
                break;
            case R.id.actionButtonAll:
                Thread thread=new Thread(() -> {
                    List<Transaction>listTR=new ArrayList<>();
                    listTR=assetsModel.buttonAll();
                    Message message=handler.obtainMessage(1,listTR);
                    handler.sendMessage(message);
                });
                thread.start();
                  break;
        }
    }


    public void addInDialog(){
        if (view.getEditTextTx().getText().toString().equals("")){
            view.getEditTextTx().setText(STRING_WRONGS);

        }else if (view.getEditTextNumber().getText().toString().equals("")){
            view.getEditTextNumber().setText(STRING_WRONGS);

        }else {
            int sum= Integer.parseInt(view.getEditTextNumber().getText().toString());
            String value= String.valueOf(sum);
            String comment=view.getEditTextTx().getText().toString();
            threadAdd(comment, value);
            view.getEditTextNumber().setText(null);
            view.getEditTextTx().setText(null);
        }
    }

    private void threadAdd(String comment, String value){
        Thread thread = new Thread(()->{
            assetsModel.buttonOk(value,comment);
            Sound.play();
        });
        thread.start();
    }

    public void newTime(){
        try {
            assetsModel.dbClear();
            Toast toastTexst=Toast.makeText(context,"Все удаленно",Toast.LENGTH_LONG);
            toastTexst.show();
        }catch (Exception e){
            Toast toastTexst=Toast.makeText(context,"Неудача",Toast.LENGTH_LONG);
            toastTexst.show();
        }
    }


    static class MyHandler extends Handler{

        private final WeakReference<MainPresenterPurse> mActiviti;

        public MyHandler(MainPresenterPurse activity){
            mActiviti= new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainPresenterPurse presenterPurse=mActiviti.get();
            List<Transaction> list=(List<Transaction>)msg.obj;
            MainActivityList.setList(list);
            Intent intent=new Intent(presenterPurse.context,MainActivityList.class);
            presenterPurse.context.startActivity(intent);
        }
    }

    public void writeSMSAddress(String address){
        SerializableFile serializableFile = new SerializableFile(context);
        Thread thread = new Thread(()->{
            serializableFile.outPut(address);
        });
        thread.start();
    }
}
