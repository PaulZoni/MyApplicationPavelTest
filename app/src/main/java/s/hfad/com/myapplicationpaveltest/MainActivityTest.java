package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;




public class MainActivityTest extends FragmentActivity implements IView,View.OnClickListener{

    protected  static   HashMap<String,Double> v=new HashMap<>();

    private static final String START_KEY_TIME="START_KEY";
    static boolean STAT_TIME=false;
    private SharedPreferences preferences;
    private MainPresenter presenter;
    private RVAdapter adapter;

    protected TextView textView;
    protected EditText editText;
    protected Spinner spinnerLeft;
    protected Spinner spinnerRight;

     Button buttonSum;
     //TextView textViewEUR,textViewCHF,textViewUSD;
    private List<ValutaModel> persons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);


        parserValute();
        if (presenter==null){
            presenter=new MainPresenter(this);
        }
        buttonSum=(Button)findViewById(R.id.button_Sum);
        buttonSum.setOnClickListener(this);

        activateToast();
        settings();

    }





    public void lisnerAdapterPerson(){

        adapter.setListener(new RVAdapter.Listener() {
            @Override
            public void onClick(int position) {



                if (position==0){
                    android.support.v4.app.FragmentManager manager=getSupportFragmentManager();

                    android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();


                    transaction.replace(R.id.fragmentContainer,new BlankFragmentInformation());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
        });
    }


    public void parserValute(){

        try {

            new ParserValute().getParser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(strings -> {

                        MainPresenter.setValue(strings);
                        persons = new ArrayList<>();
                        persons.add(new ValutaModel("Доллар США", String.valueOf(strings.get("USD")), R.mipmap.usd));
                        persons.add(new ValutaModel("Валюта еврозоны Евро", String.valueOf(strings.get("EUR")), R.mipmap.euro));
                        persons.add(new ValutaModel("Швейцарский Франк ", String.valueOf(strings.get("CHF")), R.mipmap.chf));
                        persons.add(new ValutaModel("Английский Фунт", "  ", R.mipmap.pound));


                        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
                        LinearLayoutManager llm = new LinearLayoutManager(this);
                        rv.setLayoutManager(llm);
                        adapter = new RVAdapter(this,persons);
                        rv.setAdapter(adapter);

                        lisnerAdapterPerson();

                    });



        }catch (Exception e){

            persons.add(new ValutaModel("Нет соединения", "Нет соединения", R.mipmap.usd));

        }
    }




    public void activateToast(){
        CharSequence text="Вы активировали конвертер!";
        int duration=Toast.LENGTH_SHORT;
        Toast toastTexst=Toast.makeText(this,text,duration);
        toastTexst.show();
    }




    @Override
    public void onClick(View view) {
        presenter.onGetButtonClicked();
    }


    @Override
    public TextView getTextView() {
        return textView=(TextView)findViewById(R.id.textView);
    }

    @Override
    public EditText getTextEdit() {
        return editText=(EditText)findViewById(R.id.edit_number1);
    }

    @Override
    public Spinner getLeftSpiner() {
        return spinnerLeft=(Spinner)findViewById(R.id.spinner_left);
    }

    @Override
    public Spinner getRightSpiner() {
        return spinnerRight=(Spinner)findViewById(R.id.spinner_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        preferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=preferences.edit();
        ed.putBoolean(START_KEY_TIME,STAT_TIME);
        ed.apply();
    }

    public void settings(){
        preferences=getPreferences(MODE_PRIVATE);
        STAT_TIME=preferences.getBoolean(START_KEY_TIME,false);
    }
}












