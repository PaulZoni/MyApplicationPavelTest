package s.hfad.com.myapplicationpaveltest;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import android.os.Handler;


import s.hfad.com.myapplicationpaveltest.modelAsets.AssetsModel;


public class MainPresenterPurse {
    private ModelPurseTop model;
    Handler handler;
    private IViewPurse view;
    AssetsModel assetsModel;

    Context context;

    public static String fileName = "Assets.ser";

    MainPresenterPurse(IViewPurse view, Context context){
        this.view=view;
        this.context=context;
        model=new ModelPurseTopping(context);
        assetsModel=new AssetsModel(context);
    }

    @SuppressLint("HandlerLeak")
    public void buttonOnClick(View v){



        switch (v.getId()){

            case R.id.buttonOkAssets:

                int sum= Integer.parseInt(view.getEditTextNumber().getText().toString());
                String value= String.valueOf(sum);
                String comment=view.getEditTextTx().getText().toString();
                //model.buttonOk(sum,comment);
                assetsModel.buttonOk(value,comment);


                break;

            case R.id.buttonAllTrAssets:

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

        ModelPurse purse=new AssetsPurse();
        ArrayList list=new ArrayList();
        try {

            purse.output(context,list);







            Toast toastTexst=Toast.makeText(context,"Все удаленно",Toast.LENGTH_LONG);
            toastTexst.show();
            MonetaryAssets.STAT=false;
        }catch (Exception e){
            Toast toastTexst=Toast.makeText(context,"Неудача",Toast.LENGTH_LONG);
            toastTexst.show();
        }

    }
}



