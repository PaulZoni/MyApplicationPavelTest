package s.hfad.com.myapplicationpaveltest;



import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import s.hfad.com.myapplicationpaveltest.Activity.MainActivityList;
import s.hfad.com.myapplicationpaveltest.modelAsets.AssetsModel;
import s.hfad.com.myapplicationpaveltest.modelAsets.Sound;


public class MainPresenterPurse {

    private final Handler handler;
    private IViewPurse view;
    private AssetsModel assetsModel;
    private Sound mSound;

    Context context;


    public MainPresenterPurse(IViewPurse view, Context context,String key){
        this.view=view;
        this.context=context;
        assetsModel=new AssetsModel(context,key);
        handler=new MayHandler(this);
        mSound=new Sound(context);

    }



    public void buttonOnClick(View v){

        switch (v.getId()){

            case R.id.actionButtonAdd:

                int sum= Integer.parseInt(view.getEditTextNumber().getText().toString());
                String value= String.valueOf(sum);
                String comment=view.getEditTextTx().getText().toString();
                assetsModel.buttonOk(value,comment);
                mSound.play();

                break;

            case R.id.actionButtonAll:

                Thread thread=new Thread(() -> {

                    List<String>s=new ArrayList<>();
                     s=assetsModel.buttonAll();

                    Message message=handler.obtainMessage(1,s);
                    handler.sendMessage(message);
                });
                thread.start();

                  break;
        }
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


    static class MayHandler extends Handler{

        private final WeakReference<MainPresenterPurse> mActiviti;

        public MayHandler(MainPresenterPurse activity){
            mActiviti=new WeakReference<MainPresenterPurse>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            MainPresenterPurse presenterPurse=mActiviti.get();
            List<String> list=(List<String>)msg.obj;
            MainActivityList.setList(list);
            Intent intent=new Intent(presenterPurse.context,MainActivityList.class);
            presenterPurse.context.startActivity(intent);

        }
    }

}




















