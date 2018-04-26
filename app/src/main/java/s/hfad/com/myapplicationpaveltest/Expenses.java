package s.hfad.com.myapplicationpaveltest;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.IViewPurse;
import s.hfad.com.myapplicationpaveltest.MainPresenterPurse;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.Graph;

import static android.content.Context.MODE_PRIVATE;

public class Expenses extends Fragment implements IViewPurse,View.OnClickListener {


    private MainPresenterPurse presenter;

    private FloatingActionButton buttonOk;
    private FloatingActionButton buttonAll;
    protected EditText editTextNumber;
    protected EditText editTextTx;
    private View view;

    private static final String START_KEY="START_KEY";
    SharedPreferences sPref;
    static boolean STAT=false;
    static private final String KEY_EXPENSES="expenses_key";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.activity_expenses,container,false);

        if (presenter==null){
            presenter=new MainPresenterPurse(this,getContext(),KEY_EXPENSES);
        }
        buttonOk=(FloatingActionButton)view.findViewById(R.id.actionButtonAdd);
        buttonOk.setOnClickListener(this);
        buttonAll=(FloatingActionButton)view.findViewById(R.id.actionButtonAll);
        buttonAll.setOnClickListener(this);

        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbarAssets_expenses);
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


        new Graph(getContext(),KEY_EXPENSES).getGraph()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::graphBac);

    }

    public void graphBac(HashMap<String,Float> map){


        PieView pieView = view.findViewById(R.id.graph);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        pieHelperArrayList.add(new PieHelper(map.get("P assets")));
        pieHelperArrayList.add(new PieHelper(map.get("P expenses")));

        pieView.setDate(pieHelperArrayList);


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





















