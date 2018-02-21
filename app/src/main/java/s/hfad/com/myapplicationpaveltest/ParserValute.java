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
import java.util.List;



public class ParserValute {
    private HashMap<String,Double> value=new HashMap<>();
    private double USD;
    private double EUR;
    private double CHF;

    ParserValute() throws IOException, ParseException {
        parser();
        value.put("USD",USD);
        value.put("EUR",EUR);
        value.put("CHF",CHF);
    }

    public void parser() throws IOException, ParseException {

        URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder stringBuilder=new StringBuilder();
        String inputLine;


        while ((inputLine = in.readLine()) != null) {

            stringBuilder.append(inputLine);

        }

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(String.valueOf(stringBuilder));
        JSONObject jsonObj = (JSONObject) obj;



        JSONObject jsonObj2= (JSONObject) jsonObj.get("Valute");

        JSONObject jsonObjUSD= (JSONObject)jsonObj2.get("USD");//Парсим доллор
        this.USD= (double) jsonObjUSD.get("Value");

        JSONObject jsonObjEUR= (JSONObject)jsonObj2.get("EUR");//Парсим евро
        this.EUR= (double) jsonObjEUR.get("Value");

        JSONObject jsonObjCHF=(JSONObject)jsonObj2.get("CHF");
        this.CHF=(double)jsonObjCHF.get("Value");






    }

    public HashMap<String,Double> getVlue(){
        return value;
    }


}














