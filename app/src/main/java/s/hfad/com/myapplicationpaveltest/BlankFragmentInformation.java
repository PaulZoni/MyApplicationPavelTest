package s.hfad.com.myapplicationpaveltest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public BlankFragmentInformation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment_information, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle=getArguments();
        STAT=bundle.getInt("stat");
        TimeValuteCheck(STAT);

    }


    public void TimeValuteCheck(int stat){


        new TimeValuteValue(getContext()).getTimeValte().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list->{

                    if (stat==0){
                        listX=list.get("USD");

                    }else if (stat==1){
                        listX=list.get("EUR");
                    }

                    DataPoint[] points=new DataPoint[listX.size()];

                    for (int i = 0; i <points.length ; i++) {
                        points[i]=new DataPoint(listX.get(i),i);
                    }


                    graphView=(GraphView)getActivity().findViewById(R.id.graphInformation);

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




















