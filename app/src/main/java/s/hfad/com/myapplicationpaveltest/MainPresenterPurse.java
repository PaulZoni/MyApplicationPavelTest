package s.hfad.com.myapplicationpaveltest;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;


import s.hfad.com.myapplicationpaveltest.modelAsets.AssetsModel;


public class MainPresenterPurse {

    Handler handler;
    private IViewPurse view;
    AssetsModel assetsModel;

    Context context;


    MainPresenterPurse(IViewPurse view, Context context){
        this.view=view;
        this.context=context;
        assetsModel=new AssetsModel(context);
    }

    @SuppressLint("HandlerLeak")
    public void buttonOnClick(View v){



        switch (v.getId()){

            case R.id.actionButtonAdd:

                int sum= Integer.parseInt(view.getEditTextNumber().getText().toString());
                String value= String.valueOf(sum);
                String comment=view.getEditTextTx().getText().toString();
                assetsModel.buttonOk(value,comment);

                break;

            case R.id.actionButtonAll:

                Thread thread=new Thread(() -> {

                    String s=assetsModel.buttonAll();

                    Message message=handler.obtainMessage(1,s);
                    handler.sendMessage(message);
                });
                   thread.start();

                   handler =new Handler(){
                       @Override
                       public void handleMessage(android.os.Message msg) {
                           view.getEditTextTx().setText(String.valueOf(msg.obj));
                       }
                   };

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
}



