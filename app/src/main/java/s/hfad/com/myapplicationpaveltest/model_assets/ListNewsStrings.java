package s.hfad.com.myapplicationpaveltest.model_assets;


import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import s.hfad.com.myapplicationpaveltest.R;

public class ListNewsStrings {

    private  List<String>mStringList;
    private Context context;

    public ListNewsStrings(Context context) {
        mStringList=new ArrayList<>();
        this.context=context;
        initializationListStrings();
    }


    public List<String> getStringList() {
        return mStringList;
    }

    private  void initializationListStrings(){
        mStringList.add(context.getString(R.string.Canada));
        mStringList.add(context.getString(R.string.France));
        mStringList.add(context.getString(R.string.Australia));
        mStringList.add(context.getString(R.string.Russia));
        mStringList.add(context.getString(R.string.ABCNews));
        mStringList.add(context.getString(R.string.ArsTechnica));
        mStringList.add(context.getString(R.string.AssociatedPress));
        mStringList.add(context.getString(R.string.BBCNews));
        mStringList.add(context.getString(R.string.Bloomberg));
        mStringList.add(context.getString(R.string.InfoMoney));
    }
}
