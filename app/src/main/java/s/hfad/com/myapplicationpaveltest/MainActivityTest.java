package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import rx.schedulers.Schedulers;


public class MainActivityTest extends Activity implements IView, View.OnClickListener {

    protected HashMap<String, Double> value = new HashMap<>();

    private MainPresenter presenter;

    protected TextView textView;
    protected EditText editText;
    protected Spinner spinnerLeft;
    protected Spinner spinnerRight;

    Button buttonSum;
    TextView textViewEUR, textViewCHF, textViewUSD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        textViewUSD = (TextView) findViewById(R.id.usdTextView);
        textViewEUR = (TextView) findViewById(R.id.euroTextView);
        textViewCHF = (TextView) findViewById(R.id.CHFTextView);
        activateToast();

        if (presenter == null) {
            presenter = new MainPresenter(this, value);
        }

        buttonSum = (Button) findViewById(R.id.button_Sum);
        buttonSum.setOnClickListener(this);

        parserValute();

    }

    public void parserValute() {

        try {
            new ParserValute().rxparser()
                    .subscribe(map -> {
                        for (HashMap.Entry<String, Double> m : map.entrySet()) {
//                            value.put(m.getKey(), m.getValue());
                            textViewUSD.setText(String.valueOf(m.getValue()));
//                            textViewEUR.setText(String.valueOf(value.get("EUR")));
//                            textViewCHF.setText(String.valueOf(value.get("CHF")));
                        }
                    });

        } catch (Exception e) {
            textViewUSD.setText("нет подключения");
            textViewEUR.setText("нет подключения");
            textViewCHF.setText("нет подключения");

        }

        /*if (value.isEmpty()){
            textViewUSD.setText("нет подключенияя");
            textViewEUR.setText("нет подключенияя");
            textViewCHF.setText("нет подключенияя");
        }else {
            textViewUSD.setText(String.valueOf(value.get("USD")));
            textViewEUR.setText(String.valueOf(value.get("EUR")));
            textViewCHF.setText(String.valueOf(value.get("CHF")));

        }*/


    }

    public void activateToast() {
        CharSequence text = "Вы активировали конвертер!";
        int duration = Toast.LENGTH_SHORT;
        Toast toastTexst = Toast.makeText(this, text, duration);
        toastTexst.show();
    }


    @Override
    public void onClick(View view) {
        presenter.onGetButtonClicked();
    }


    @Override
    public TextView getTextView() {
        return textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public EditText getTextEdit() {
        return editText = (EditText) findViewById(R.id.edit_number1);
    }

    @Override
    public Spinner getLeftSpiner() {
        return spinnerLeft = (Spinner) findViewById(R.id.spinner_left);
    }

    @Override
    public Spinner getRightSpiner() {
        return spinnerRight = (Spinner) findViewById(R.id.spinner_right);
    }


//    class NewThred extends AsyncTask<Void,Void,String>{
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            textViewUSD.setText("Нет сигнала");
//        }
//
//        @Override
//        protected String doInBackground(Void... args) {
//            HashMap<String,Double> v=new HashMap<>();
//            try {
//                URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
//                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//
//                StringBuilder stringBuilder=new StringBuilder();
//                String inputLine;
//
//
//                while ((inputLine = in.readLine()) != null) {
//
//                    stringBuilder.append(inputLine);
//
//                }
//
//                JSONParser parser = new JSONParser();
//
//                Object obj = parser.parse(String.valueOf(stringBuilder));
//                JSONObject jsonObj = (JSONObject) obj;
//
//
//
//                JSONObject jsonObj2= (JSONObject) jsonObj.get("Valute");
//
//                JSONObject jsonObjUSD= (JSONObject)jsonObj2.get("USD");//Парсим доллор
//                //this.USD= (double) jsonObjUSD.get("Value");
//                v.put("USD",(double)jsonObjUSD.get("Value"));
//
//
//                //JSONObject jsonObjEUR= (JSONObject)jsonObj2.get("EUR");//Парсим евро
//                //this.EUR= (double) jsonObjEUR.get("Value");
//
//
//                //JSONObject jsonObjCHF=(JSONObject)jsonObj2.get("CHF");
//                //this.CHF=(double)jsonObjCHF.get("Value");
//
//            }catch (Exception e){
//                textViewUSD.setText("нет подключения");
//            }
//
//            String s= String.valueOf(v.get("USD"));
//            return s;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            textViewUSD.setText(String.valueOf(s));
//        }


//    }


}












