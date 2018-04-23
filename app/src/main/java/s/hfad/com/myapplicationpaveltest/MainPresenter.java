package s.hfad.com.myapplicationpaveltest;


import java.util.HashMap;

import s.hfad.com.myapplicationpaveltest.modelAsets.ModelInterface;

public class MainPresenter {

    double SUM;
    private IView vive;
    private ModelInterface model;
    private static HashMap<String,Double> value=new HashMap<>();

    public static void setValue(HashMap<String, Double> value) {
        MainPresenter.value = value;
    }

    public static HashMap<String, Double> getValue() {
        return value;
    }

    MainPresenter(IView vive){
        this.vive=vive;
        model=new ModelSUM();
    }


    public void onGetButtonClicked(){

        String sumText=vive.getTextEdit().getText().toString();
        String SPLeft=String.valueOf(vive.getLeftSpiner().getSelectedItem());
        String SPRight=String.valueOf(vive.getRightSpiner().getSelectedItem());

        if (value.isEmpty()){
            vive.getTextView().setText("Нет соединения с интернетом");
        }else {

            SUM=model.sumTest(SPLeft,SPRight,value,sumText);
            vive.getTextView().setText(String.valueOf(SUM));
        }

    }
}




