package s.hfad.com.myapplicationpaveltest;


import android.view.View;

public class MainPresenterPurse {
    private ModelPurseTop model;
    private IViewPurse view;

    MainPresenterPurse(IViewPurse view){
        this.view=view;
        model=new ModelPurseTopping();
    }

    public void buttonOnClick(View v){

        int sum= Integer.parseInt(view.getEditTextNumber().getText().toString());
        String comment=view.getEditTextTx().getText().toString();

        switch (v.getId()){

            case R.id.buttonOkAssets:
                model.buttonOk(sum,comment);
                break;

            case R.id.buttonAllTrAssets:
                  view.getEditTextTx().setText(model.buttonAll());
                  break;
        }
    }

}



