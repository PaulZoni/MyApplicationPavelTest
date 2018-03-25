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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class BlankFragmentInformation extends Fragment {

    private static int STAT;


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


        TimeValuteCheck(STAT);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (true){

            try {
                STAT=(int)getArguments().getSerializable(ARG_ID);

            }catch (Exception e){
                Log.d("war","STAT=(int)getArguments().getSerializable(ARG_ID)");
            }

        }else if (true){

            try {

                Bundle bundle=getArguments();
                STAT=bundle.getInt("stat");

            }catch (Exception e){
                Log.d("war","STAT=bundle.getInt()");
            }

        }

    }


    @Override
    public void onStart() {
        super.onStart();

    }


    public void TimeValuteCheck(int stat){


        new TimeValuteValue(getContext()).getTimeValte().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list->{

                    Log.d("w", String.valueOf(Thread.currentThread()));
                    if (stat==0){
                        listX=list.get("USD");

                    }else if (stat==1){
                        listX=list.get("EUR");
                    }

                    DataPoint[] points=new DataPoint[listX.size()];

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

                });
    }


}




















