package s.hfad.com.myapplicationpaveltest;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;

public class TimeValuteValue {

    Context context;

    public TimeValuteValue(Context context) {
        this.context = context;
    }

    public ArrayList<Double> timeVValue(){

        HashMap<String,Double> value=MainPresenter.getValue();
        ArrayList<Double> USDList=new ArrayList<>();


        if (MainActivityTest.STAT_TIME){
            USDList=input(context,USDList);
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

        }catch (Exception e){

        }

        output(context,USDList);

        return USDList;
    }


    public ArrayList input(Context context, ArrayList<Double> list) {


        try {
            FileInputStream fileInputStream = context.openFileInput("Mani.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list = (ArrayList<Double>) objectInputStream.readObject();
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




    public ArrayList output(Context context,ArrayList<Double> list) {


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

    public Observable<ArrayList<Double>> getTimeValte() {
        return Observable.create(observableEmitter ->{


            observableEmitter.onNext(timeVValue());
        });
    }



}
