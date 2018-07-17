package s.hfad.com.myapplicationpaveltest.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.model_assets.AssetsModel;


public class Expenses extends Monetary {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void initializationComponent() {
        buttonOk = view.findViewById(R.id.actionButtonAdd);
        buttonAll = view.findViewById(R.id.actionButtonAll);
        toolbar = view.findViewById(R.id.toolbarAssets_expenses);
        pieView = view.findViewById(R.id.graph);
        mTextViewInformation=view.findViewById(R.id.text_expenses_information);
        allSumMoneyInTheMoment = view.findViewById(R.id.allExpensesInTheMoment);
    }

    @Override
    public String KEY_VIEW() {
        return AssetsModel.KEY_EXPENSES;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_expenses;
    }

}

