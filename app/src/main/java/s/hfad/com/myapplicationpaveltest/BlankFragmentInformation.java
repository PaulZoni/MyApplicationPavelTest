package s.hfad.com.myapplicationpaveltest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



public class BlankFragmentInformation extends Fragment {

    private DataPoint[] points;
    private static int STAT;

    private final String KEY="index";
    private boolean INDEX=false;
    private ArrayList<Double>listX=new ArrayList<>();
    private GraphView graphView;
    private static final String ARG_ID = "frag_id";
    View view;

    public BlankFragmentInformation() {
        // Required empty public constructor
    }

    public static BlankFragmentInformation newInstance(int position){
        Bundle build=new Bundle();
        build.putSerializable(ARG_ID,position);
        BlankFragmentInformation fragment=new BlankFragmentInformation();
        fragment.setArguments(build);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_blank_fragment_information, container, false);


        if (savedInstanceState!=null){
            INDEX=savedInstanceState.getBoolean(KEY,false);
            listX=(ArrayList<Double>) savedInstanceState.getSerializable("listX");

            graf();
        }

        if (!INDEX){
            //TimeValuteCheck(STAT);
            TimeValuteCheck(STAT);
            INDEX=true;
        }


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        if (getArguments().getSerializable(ARG_ID)!=null){

            try {
                STAT=(int)getArguments().getSerializable(ARG_ID);

            }catch (Exception e){
                Log.d("war","STAT=(int)getArguments().getSerializable(ARG_ID)");
            }

        }else {

            try {

                Bundle bundle=getArguments();
                STAT=bundle.getInt("stat");

            }catch (Exception e){
                Log.d("war","STAT=bundle.getInt()");
            }

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY,INDEX);
        outState.putSerializable("listX",listX);
    }


    @Override
    public void onStart() {
        super.onStart();

    }


    public void graf(){


        points=new DataPoint[listX.size()];
        for (int i = 0; i <points.length ; i++) {
            points[i]=new DataPoint(i,listX.get(i));
        }


        graphView=(GraphView)view.findViewById(R.id.graphInformation);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(-150);
        graphView.getViewport().setMaxY(150);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(4);
        graphView.getViewport().setMaxX(80);


        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        graphView.addSeries(series);
    }


    public void TimeValuteCheck(int stat){
        Observable.just(new TimeValuteValue(getContext()).timeVValue()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayListHashMap -> {


            new Thread(() -> {

                if (stat==0){
                    listX=stringArrayListHashMap.get("USD");
                }else if (stat==1){
                    listX=stringArrayListHashMap.get("EUR");
                }else if (stat==2) {
                    listX = stringArrayListHashMap.get("CHF");
                }else if (stat==3) {
                    listX = stringArrayListHashMap.get("AUD");
                }else if (stat==4) {
                    listX = stringArrayListHashMap.get("AZN");
                }else if (stat==5) {
                    listX = stringArrayListHashMap.get("GBP");
                }else if (stat==6) {
                    listX = stringArrayListHashMap.get("AMD");
                }else if (stat==7) {
                    listX = stringArrayListHashMap.get("BYN");
                }else if (stat==8) {
                    listX = stringArrayListHashMap.get("BGN");
                }else if (stat==9) {
                    listX = stringArrayListHashMap.get("BRL");
                }else if (stat==10) {
                    listX = stringArrayListHashMap.get("HUF");
                }else if (stat==11) {
                    listX = stringArrayListHashMap.get("HKD");
                }else if (stat==12) {
                    listX = stringArrayListHashMap.get("DKK");
                }else if (stat==13) {
                    listX = stringArrayListHashMap.get("INR");
                }else if (stat==14) {
                    listX = stringArrayListHashMap.get("KZT");
                }



                graf();
            }).start();


        });


    }

}




















