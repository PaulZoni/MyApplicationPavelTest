package s.hfad.com.myapplicationpaveltest.presenters;


import android.content.Context;
import java.util.HashMap;
import s.hfad.com.myapplicationpaveltest.Interface.IView;
import s.hfad.com.myapplicationpaveltest.Interface.PresenterConverterInterface;
import s.hfad.com.myapplicationpaveltest.model_assets.ModelSUM;
import s.hfad.com.myapplicationpaveltest.model_assets.LoadingGraf;
import s.hfad.com.myapplicationpaveltest.model_assets.ModelInterface;

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


    public MainPresenter(IView vive, Context context){
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

