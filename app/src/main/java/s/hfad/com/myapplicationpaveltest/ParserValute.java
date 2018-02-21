package s.hfad.com.myapplicationpaveltest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;


public class ParserValute {


    public static final String URL = "https://www.cbr-xml-daily.ru/daily_json.js";

    private HashMap<String, Double> value = new HashMap<>();
    private double USD;
    private double EUR;
    private double CHF;

    ParserValute() throws IOException, ParseException {

    }

    public String parser(String url) {
        StringBuilder stringBuilder = null;
        URL urlPars = null;
        try {
            urlPars = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlPars.openStream()));
            stringBuilder = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(stringBuilder);
    }

    public HashMap<String, Double> resultQuery(String result) {
        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = (JSONObject) obj;


        JSONObject jsonObj2 = (JSONObject) jsonObj.get("Valute");

        JSONObject jsonObjUSD = (JSONObject) jsonObj2.get("USD");//Парсим доллор
        this.USD = (double) jsonObjUSD.get("Value");

        JSONObject jsonObjEUR = (JSONObject) jsonObj2.get("EUR");//Парсим евро
        this.EUR = (double) jsonObjEUR.get("Value");

        JSONObject jsonObjCHF = (JSONObject) jsonObj2.get("CHF");
        this.CHF = (double) jsonObjCHF.get("Value");

        value.put("USD", USD);
        value.put("EUR", EUR);
        value.put("CHF", CHF);

        return value;
    }


    public Observable<HashMap<String, Double>> rxparser() {
        return Observable.create(new Observable.OnSubscribe<HashMap<String, Double>>() {

            @Override
            public void call(Subscriber<? super HashMap<String, Double>> subscriber) {
                subscriber.onNext(resultQuery(parser(URL)));
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation());
    }


    public HashMap<String, Double> getVlue() {
        return value;
    }


}














