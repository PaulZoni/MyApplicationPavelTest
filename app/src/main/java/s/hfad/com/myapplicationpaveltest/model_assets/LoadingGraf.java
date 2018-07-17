package s.hfad.com.myapplicationpaveltest.model_assets;


import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public  class LoadingGraf {

    private HashMap<String, Double> value;
    private Context mContext;
    static private HashMap<String, ArrayList<Double>> valuta;

    public LoadingGraf(Context context,HashMap<String, Double> value) {
        mContext = context;
        this.value = value;
        valuta = new HashMap<>();
    }

    public  void timeValueLoading() {
        Observable.just(new TimeValuteValue(mContext,value).timeVValue()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayListHashMap -> valuta = stringArrayListHashMap);
    }

    public static HashMap<String, ArrayList<Double>> getValuta() {
        return valuta;
    }
}
