package s.hfad.com.myapplicationpaveltest;


import android.content.Context;
import java.util.HashMap;
import s.hfad.com.myapplicationpaveltest.Interface.PresenterConverterInterface;
import s.hfad.com.myapplicationpaveltest.modelAsets.LoadingGraf;
import s.hfad.com.myapplicationpaveltest.modelAsets.ModelInterface;

public class MainPresenter implements PresenterConverterInterface {

    double SUM;
    private IView vive;
    private ModelInterface model;
    private Context mContext;
    private  HashMap<String,Double> value=new HashMap<>();

    @Override
    public  void setValue(HashMap<String, Double> value) {
        this.value = value;
        loadGraf(value);
    }


    MainPresenter(IView vive, Context context){
        this.vive=vive;
        mContext = context;
        model=new ModelSUM();

    }

    @Override
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

    private void loadGraf(HashMap<String, Double> value){

        LoadingGraf loadingGraf = new LoadingGraf(mContext,value);
        loadingGraf.timeValueLoading();
    }
}




