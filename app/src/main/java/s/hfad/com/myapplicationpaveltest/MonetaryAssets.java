package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import s.hfad.com.myapplicationpaveltest.modelAsets.Graph;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;


public class MonetaryAssets extends Fragment implements IViewPurse,View.OnClickListener {

    private MainPresenterPurse presenter;

    private FloatingActionButton buttonOk;
    private FloatingActionButton buttonAll;
    protected EditText editTextNumber;
    protected EditText editTextTx;
    private View view;

    private static final String START_KEY="START_KEY";
    SharedPreferences sPref;
    static boolean STAT=false;
    static private final String KEY_ASSETS="assets_key";
    Observable s;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.activity_monetary_assets,container,false);

        if (presenter==null){
            presenter=new MainPresenterPurse(this,getContext(),KEY_ASSETS);
        }

        buttonOk=(FloatingActionButton)view.findViewById(R.id.actionButtonAdd);
        buttonOk.setOnClickListener(this);
        buttonAll=(FloatingActionButton)view.findViewById(R.id.actionButtonAll);
        buttonAll.setOnClickListener(this);

        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbarAssets);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        graphV();
        settings();
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void settings(){
        sPref=getActivity().getPreferences(MODE_PRIVATE);
        STAT=sPref.getBoolean(START_KEY,false );
    }


    public void graphV(){

        new Graph(getContext(),KEY_ASSETS).getGraph()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::graphBac);

    }


    public void graphBac(ArrayList<Integer> list){

        new Thread(() -> {

            GraphView graph = (GraphView)getActivity().findViewById(R.id.graph);
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0,list.get(0))
                    //new DataPoint(1,list.get(1))

            });

            series.setValueDependentColor(data -> Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100));

            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
            graph.addSeries(series);

        }).start();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_purse,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.action_settings:
                presenter.newTime();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        presenter.buttonOnClick(view);
    }

    @Override
    public EditText getEditTextNumber(){
        return editTextNumber=(EditText)view.findViewById(R.id.editTextAssets_sum);
    }

    @Override
    public EditText getEditTextTx() {
        return editTextTx=(EditText)view.findViewById(R.id.editTextAssets_text);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        sPref=getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=sPref.edit();
        ed.putBoolean(START_KEY,STAT);
        ed.apply();
    }


}










