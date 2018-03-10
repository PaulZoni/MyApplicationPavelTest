package s.hfad.com.myapplicationpaveltest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import io.reactivex.Observable;


public class ParserValute {


    public String inputParser() throws IOException {





        URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder stringBuilder=new StringBuilder();
        String inputLine;


        while ((inputLine = in.readLine()) != null) {

            stringBuilder.append(inputLine);

        }

        return String.valueOf(stringBuilder);
    }

    public HashMap<String,Double> parser(String string) throws ParseException {
        HashMap<String,Double> value=new HashMap<>();

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(string);
        JSONObject jsonObj = (JSONObject) obj;



        JSONObject jsonObj2= (JSONObject) jsonObj.get("Valute");

        JSONObject jsonObjUSD= (JSONObject)jsonObj2.get("USD");//Парсим доллор
        value.put ("USD",(double) jsonObjUSD.get("Value"));


        JSONObject jsonObjEUR= (JSONObject)jsonObj2.get("EUR");//Парсим евро
        value.put ("EUR",(double) jsonObjEUR.get("Value"));

        JSONObject jsonObjCHF= (JSONObject)jsonObj2.get("CHF");
        value.put ("CHF",(double) jsonObjCHF.get("Value"));




        return value;
    }


    public Observable<HashMap<String,Double>> getParser() {
        return Observable.create(observableEmitter ->{


            observableEmitter.onNext(parser(inputParser()));
        });
    }
}














