package s.hfad.com.myapplicationpaveltest.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import com.getbase.floatingactionbutton.FloatingActionButton;
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

public abstract class Monetary extends Fragment implements IViewPurse,View.OnClickListener {

    private final String textSumExpenses="От всей суммы что было потрачено";
    private final String textSumAssets="От всей суммы было приобретено";
    private static final String START_KEY="START_KEY";
    private MainPresenterPurse presenter;
    private SharedPreferences sPref;
    static boolean STAT=false;
    protected View view;
    protected FloatingActionButton buttonOk;
    protected FloatingActionButton buttonAll;
    protected PieView pieView;
    protected TextView mTextViewInformation;
    protected TextView mTextViewAllSum;
    protected EditText editTextNumber;
    protected EditText editTextTx;
    protected Toolbar toolbar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(getLayoutID(),container,false);

        if (presenter==null){
            presenter=new MainPresenterPurse(this,getContext(),KEY_VIEW());
        }

        initializationComponent();
        buttonOk.setOnClickListener(this);

        buttonAll.setOnClickListener(this);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        graphV();
        settings();
        setHasOptionsMenu(true);

        return view;
    }

    public abstract void initializationComponent();

    public abstract String KEY_VIEW();

    public abstract int getLayoutID();


    private void settings(){
        sPref=getActivity().getPreferences(MODE_PRIVATE);
        STAT=sPref.getBoolean(START_KEY,false );
    }


    private void graphV(){

        new Graph(getContext(),KEY_VIEW()).getGraph()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::graphBac);
    }


    private void graphBac(HashMap<String,Float> map){
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        pieHelperArrayList.add(new PieHelper(map.get(Graph.P_KEY_ASSETS)));
        pieHelperArrayList.add(new PieHelper(map.get(Graph.P_KEY_EXPENSES)));
        pieView.setDate(pieHelperArrayList);
        initialisationListenerPie(map);
    }


    private void initialisationListenerPie(HashMap<String,Float> map){
        pieView.setOnPieClickListener(index -> {
            StringBuilder stringBuilder=new StringBuilder();

            if (index==1){

                mTextViewInformation.setTextColor(getResources().getColor(R.color.Purple));
                stringBuilder.append(map.get(Graph.P_KEY_EXPENSES)+"%"+" "
                        +textSumExpenses+"("+map.get(Graph.KEY_ALL_EXPENSES)+")");
                mTextViewInformation.setText(stringBuilder);

            }else if (index==0){
                mTextViewInformation.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                stringBuilder.append(map.get(Graph.P_KEY_ASSETS)+"%"+" "
                        +textSumAssets+"("+map.get(Graph.KEY_ALL_ASSETS)+")");
                mTextViewInformation.setText(stringBuilder);
            }
            mTextViewAllSum.setText(String.valueOf(map.get(Graph.KEY_EXIST_ASSETS)));
        });
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











