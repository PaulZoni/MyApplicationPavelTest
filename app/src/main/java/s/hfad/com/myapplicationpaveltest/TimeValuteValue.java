package s.hfad.com.myapplicationpaveltest;


import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class TimeValuteValue {

    Context context;

    public TimeValuteValue(Context context) {
        this.context = context;
    }

    public HashMap<String, ArrayList<Double>> timeVValue(){

        HashMap<String,Double> value=MainPresenter.getValue();
        ArrayList<Double> USDList=new ArrayList<>();
        ArrayList<Double> EURList=new ArrayList<>();
        HashMap<String,ArrayList<Double>>timeValue=new HashMap<>();



        if (MainActivityTest.STAT_TIME){
            timeValue=input(context,timeValue);

            USDList=timeValue.get("USD");
            EURList=timeValue.get("EUR");
        }
        MainActivityTest.STAT_TIME=true;



        try {

            if (USDList.isEmpty()){
                USDList.add(value.get("USD"));
            }else {
                int size=USDList.size()-1;

                if (USDList.get(size)>value.get("USD") | USDList.get(size)<value.get("USD")){
                    USDList.add(value.get("USD"));
                }
            }

            if (EURList.isEmpty()){
                EURList.add(value.get("EUR"));
            }else {
                int size=EURList.size()-1;

                if (EURList.get(size)>value.get("EUR") | EURList.get(size)<value.get("EUR")){
                    EURList.add(value.get("EUR"));
                }
            }



        }catch (Exception e){

        }

        timeValue.clear();
        timeValue.put("USD",USDList);
        timeValue.put("EUR",EURList);
        output(context,timeValue);

        return timeValue;
    }


    public HashMap input(Context context, HashMap<String,ArrayList<Double>> list) {


        try {
            FileInputStream fileInputStream = context.openFileInput("Mani.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list = (HashMap<String,ArrayList<Double>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }




    public HashMap output(Context context,HashMap<String,ArrayList<Double>> list) {


        try {
            FileOutputStream fileOutputStream = context.openFileOutput("Mani.ser", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public Observable <HashMap<String,ArrayList<Double>>> getTimeValte() {
        return Observable.create(observableEmitter ->{


            observableEmitter.onNext(timeVValue());
        });
    }



}
