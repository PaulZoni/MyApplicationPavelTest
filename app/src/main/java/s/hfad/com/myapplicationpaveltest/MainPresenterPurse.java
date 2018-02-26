package s.hfad.com.myapplicationpaveltest;


import android.content.Context;
import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainPresenterPurse {
    private ModelPurseTop model;
    private IViewPurse view;


    public static String fileName = "Assets.ser";

    MainPresenterPurse(IViewPurse view, Context context){
        this.view=view;
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

}



