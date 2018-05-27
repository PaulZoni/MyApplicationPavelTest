package s.hfad.com.myapplicationpaveltest.fragment;


import android.support.annotation.Nullable;
import android.os.Bundle;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.AssetsModel;


public class MonetaryAssets extends Monetary {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void initializationComponent() {
        buttonOk= view.findViewById(R.id.actionButtonAdd);
        buttonAll= view.findViewById(R.id.actionButtonAll);
        toolbar=view.findViewById(R.id.toolbarAssets);
        pieView = view.findViewById(R.id.bar_view);
        mTextViewInformation=view.findViewById(R.id.text_asset_information);
    }

    @Override
    public String KEY_VIEW() {
        return AssetsModel.KEY_ASSETS;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_monetary_assets;
    }
}










