package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;




public class MainActivityTest extends Activity implements IView,View.OnClickListener{

    protected     HashMap<String,Double> v=new HashMap<>();

    private MainPresenter presenter;

    protected TextView textView;
    protected EditText editText;
    protected Spinner spinnerLeft;
    protected Spinner spinnerRight;

     Button buttonSum;
     TextView textViewEUR,textViewCHF,textViewUSD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        textViewUSD=(TextView)findViewById(R.id.usdTextView);
        textViewEUR=(TextView)findViewById(R.id.euroTextView);
        textViewCHF=(TextView)findViewById(R.id.CHFTextView);



        parserValute();
        if (presenter==null){
            presenter=new MainPresenter(this,v);
        }
        buttonSum=(Button)findViewById(R.id.button_Sum);
        buttonSum.setOnClickListener(this);

        activateToast();


    }

    public void parserValute(){

        try {

            new ParserValute().getParser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(strings -> {

                        for (HashMap.Entry<String,Double>map:strings.entrySet()){
                            v.put(map.getKey(),map.getValue());
                        }

                        textViewUSD.setText(String.valueOf(strings.get("USD")));
                        textViewEUR.setText(String.valueOf(strings.get("EUR")));
                        textViewCHF.setText(String.valueOf(strings.get("CHF")));
                    });



        }catch (Exception e){
            textViewUSD.setText("Нет соединения");
            textViewEUR.setText("Нет соединения");
            textViewCHF.setText("Нет соединения");
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

}












