package s.hfad.com.myapplicationpaveltest;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainPresenterPurse {
    private ModelPurseTop model;
    private IViewPurse view;
    Context context;

    public static String fileName = "Assets.ser";

    MainPresenterPurse(IViewPurse view, Context context){
        this.view=view;
        this.context=context;
        model=new ModelPurseTopping(context);
    }

    public void buttonOnClick(View v){



        switch (v.getId()){

            case R.id.buttonOkAssets:

                int sum= Integer.parseInt(view.getEditTextNumber().getText().toString());
                String comment=view.getEditTextTx().getText().toString();
                model.buttonOk(sum,comment);
                break;

            case R.id.buttonAllTrAssets:

                view.getEditTextTx().setText(model.buttonAll());
                //view.getEditTextTx().setText("w");

                  break;
        }
    }


    public void newTime(){
        //File file=new File(fileName);
        ModelPurse purse=new AssetsPurse();
        ArrayList list=new ArrayList();
        try {
            //file.delete();
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



