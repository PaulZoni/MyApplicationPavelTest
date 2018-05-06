package s.hfad.com.myapplicationpaveltest;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;
import io.reactivex.Observable;
import s.hfad.com.myapplicationpaveltest.modelAsets.Graph;
import com.getbase.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;


public class MonetaryAssets extends Fragment implements IViewPurse,View.OnClickListener {

    static private final String KEY_ASSETS="assets_key";
    private static final String START_KEY="START_KEY";
    private final String textSumExpenses="От всей суммы что было потрачено";
    private final String textSumAssets="От всей суммы было приобретено";
    private FloatingActionButton buttonOk;
    private FloatingActionButton buttonAll;
    protected EditText editTextNumber;
    protected EditText editTextTx;
    private View view;
    private MainPresenterPurse presenter;
    private TextView mTextViewInformation;
    private TextView mTextViewAllSum;
    private SharedPreferences sPref;
    static boolean STAT=false;
    private PieView pieView;

    Observable s;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.activity_monetary_assets,container,false);

        if (presenter==null){
            presenter=new MainPresenterPurse(this,getContext(),KEY_ASSETS);
        }

        buttonOk= view.findViewById(R.id.actionButtonAdd);
        buttonOk.setOnClickListener(this);
        buttonAll= view.findViewById(R.id.actionButtonAll);
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


    private void settings(){
        sPref=getActivity().getPreferences(MODE_PRIVATE);
        STAT=sPref.getBoolean(START_KEY,false );
    }


    private void graphV(){

        new Graph(getContext(),KEY_ASSETS).getGraph()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::graphBac);
    }


    private void graphBac(HashMap<String,Float> map){

        pieView = view.findViewById(R.id.bar_view);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        pieHelperArrayList.add(new PieHelper(map.get(Graph.P_KEY_ASSETS)));
        pieHelperArrayList.add(new PieHelper(map.get(Graph.P_KEY_EXPENSES)));
        pieView.setDate(pieHelperArrayList);

        initialisationListenerPie(map);
    }


    private void initialisationListenerPie(HashMap<String,Float> map){
        mTextViewInformation=view.findViewById(R.id.text_asset_information);
        mTextViewAllSum=view.findViewById(R.id.text_asset_allSum);
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










