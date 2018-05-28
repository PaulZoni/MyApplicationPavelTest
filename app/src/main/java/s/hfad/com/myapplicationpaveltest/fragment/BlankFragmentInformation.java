package s.hfad.com.myapplicationpaveltest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import s.hfad.com.myapplicationpaveltest.Interface.MVPViewFragmentInformation;
import s.hfad.com.myapplicationpaveltest.Interface.PresenterFragmentInformationInterface;
import s.hfad.com.myapplicationpaveltest.Presenter.PresenterFragmentInformation;
import s.hfad.com.myapplicationpaveltest.R;



public class BlankFragmentInformation extends Fragment implements MVPViewFragmentInformation {


    private DataPoint[] points;
    private static int STAT;
    private final String KEY="index";
    private boolean INDEX=false;
    private ArrayList<Double>listX=new ArrayList<>();
    private GraphView graphView;
    private static final String ARG_ID = "frag_id";
    private View view;
    private TextView mTextViewInformation;
    private PresenterFragmentInformationInterface<MVPViewFragmentInformation>  presenter;

    public BlankFragmentInformation() {
        // Required empty public constructor
    }

    public static BlankFragmentInformation newInstance(ArrayList<Double> position){
        Bundle build=new Bundle();
        build.putSerializable(ARG_ID, position);
        BlankFragmentInformation fragment=new BlankFragmentInformation();
        fragment.setArguments(build);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blank_fragment_information, container, false);
        initializationComponent();
        createPresenter();
        if (savedInstanceState!=null){
            INDEX=savedInstanceState.getBoolean(KEY,false);
            listX=(ArrayList<Double>) savedInstanceState.getSerializable("listX");
            graf();
        }
        mTextViewInformation.setText(String.valueOf(listX.get(listX.size()-1)));
        if (!INDEX){
            graf();
            INDEX=true;
        }
        return view;
    }

    private void createPresenter(){
        presenter = new PresenterFragmentInformation(getContext());
        presenter.attachView(this);
    }

    private void initializationComponent(){
        graphView = view.findViewById(R.id.graphInformation);
        mTextViewInformation = view.findViewById(R.id.text_information_currency);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //setRetainInstance(true);
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable(ARG_ID)!=null){
            try {
                listX = (ArrayList<Double>) getArguments().getSerializable(ARG_ID);
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


    @Override
    public void graf(){
        points=new DataPoint[ listX.size()];
        for (int i = 0; i <points.length ; i++) {
            points[i]=new DataPoint(i,listX.get(i));
        }
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public TextView getTextViewInformation(){
        return mTextViewInformation.findViewById(R.id.text_information_currency);
    }
}




















