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

        JSONObject jsonObjCHF= (JSONObject)jsonObj2.get("CHF");//франк швецарии
        value.put ("CHF",(double) jsonObjCHF.get("Value"));


        JSONObject jsonObjAUD= (JSONObject)jsonObj2.get("AUD");//австралия
        value.put ("AUD",(double) jsonObjAUD.get("Value"));

        JSONObject jsonObjAZN= (JSONObject)jsonObj2.get("AZN");//азербаджан манат
        value.put ("AZN",(double) jsonObjAZN.get("Value"));

        JSONObject jsonObjGBP= (JSONObject)jsonObj2.get("GBP");//фунт королевства
        value.put ("GBP",(double) jsonObjGBP.get("Value"));

        JSONObject jsonObjAMD= (JSONObject)jsonObj2.get("AMD");//драм армении
        value.put ("AMD",(double) jsonObjAMD.get("Value"));


        JSONObject jsonObjBYN= (JSONObject)jsonObj2.get("BYN");//беларуский
        value.put ("BYN",(double) jsonObjBYN.get("Value"));

        JSONObject jsonObjBGN= (JSONObject)jsonObj2.get("BGN");//блдгария
        value.put ("BGN",(double) jsonObjBGN.get("Value"));

        JSONObject jsonObjBRL= (JSONObject)jsonObj2.get("BRL");//бразилия
        value.put ("BRL",(double) jsonObjBRL.get("Value"));


        JSONObject jsonObjHUF= (JSONObject)jsonObj2.get("HUF");//венгрия
        value.put ("HUF",(double) jsonObjHUF.get("Value"));


        JSONObject jsonObjHKD= (JSONObject)jsonObj2.get("HKD");//гонконг
        value.put ("HKD",(double) jsonObjHKD.get("Value"));

        JSONObject jsonObjDKK= (JSONObject)jsonObj2.get("DKK");//дания
        value.put ("DKK",(double) jsonObjDKK.get("Value"));

        JSONObject jsonObjINR= (JSONObject)jsonObj2.get("INR");//индия
        value.put ("INR",(double) jsonObjINR.get("Value"));

        JSONObject jsonObjKZT= (JSONObject)jsonObj2.get("KZT");
        value.put ("KZT",(double) jsonObjKZT.get("Value"));

        return value;
    }


    public Observable<HashMap<String,Double>> getParser() {
        return Observable.create(observableEmitter ->{


            observableEmitter.onNext(parser(inputParser()));
        });
    }
}














