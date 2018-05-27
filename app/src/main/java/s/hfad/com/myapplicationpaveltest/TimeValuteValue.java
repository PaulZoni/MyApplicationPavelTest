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
import io.reactivex.Observable;

public class TimeValuteValue {

    private HashMap<String,Double> value;
    private Context context;

    public TimeValuteValue(Context context, HashMap<String, Double> value) {
        this.context = context;
        this.value = value;
    }

    private ArrayList<Double> USDList;
    private ArrayList<Double> EURList;
    private ArrayList<Double> CHFList;
    private ArrayList<Double> AUDList;
    private ArrayList<Double> AZNList;
    private ArrayList<Double> GBPList;
    private ArrayList<Double> AMDList;
    private ArrayList<Double> BYNList;
    private ArrayList<Double> BGNList;
    private ArrayList<Double> BRLList;
    private ArrayList<Double> HUFList;
    private ArrayList<Double> HKDList;
    private ArrayList<Double> DKKList;
    private ArrayList<Double> INRList;
    private ArrayList<Double> KZTList;
    private HashMap<String,ArrayList<Double>>timeValue;


    public HashMap<String, ArrayList<Double>> timeVValue(){

        //value=MainPresenter.getValue();
        USDList=new ArrayList<>();
        EURList=new ArrayList<>();
        CHFList=new ArrayList<>();
        AUDList=new ArrayList<>();
        AZNList=new ArrayList<>();
        GBPList=new ArrayList<>();
        AMDList=new ArrayList<>();
        BYNList=new ArrayList<>();
        BGNList=new ArrayList<>();
        BRLList=new ArrayList<>();
        HUFList=new ArrayList<>();
        HKDList=new ArrayList<>();
        DKKList=new ArrayList<>();
        INRList=new ArrayList<>();
        KZTList=new ArrayList<>();
        timeValue=new HashMap<>();

        if (MainActivityTest.STAT_TIME){
            timeValue=input(context,timeValue);

            USDList=timeValue.get("USD");
            EURList=timeValue.get("EUR");
            CHFList=timeValue.get("CHF");
            AUDList=timeValue.get("AUD");
            AZNList=timeValue.get("AZN");
            GBPList=timeValue.get("GBP");
            AMDList=timeValue.get("AMD");
            BYNList=timeValue.get("BYN");
            BGNList=timeValue.get("BGN");
            BRLList=timeValue.get("BRL");
            HUFList=timeValue.get("HUF");
            HKDList=timeValue.get("HKD");
            DKKList=timeValue.get("DKK");
            INRList=timeValue.get("INR");
            KZTList=timeValue.get("KZT");
        }
        MainActivityTest.STAT_TIME=true;

        try {

            String[] key={"USD","EUR","CHF",
                    "AUD",
                    "AZN",
                    "GBP",
                    "AMD",
                    "BYN",
                    "BGN",
                    "BRL",
                    "HUF",
                    "HKD",
                    "DKK",
                    "INR",
                    "KZT"};


            ArrayList<ArrayList<Double>> list=new ArrayList<>();
            list.add(USDList);
            list.add(EURList);
            list.add(CHFList);
            list.add(AUDList);
            list.add(AZNList);
            list.add(GBPList);
            list.add(AMDList);
            list.add(BYNList);
            list.add(BGNList);
            list.add(BRLList);
            list.add(HUFList);
            list.add(HKDList);
            list.add(DKKList);
            list.add(INRList);
            list.add(KZTList);

            ArrayList<ArrayList<Double>>lists2=new ArrayList<>();
            for (int i = 0; i <list.size() ; i++) {
                lists2.add(i,classification(list.get(i),value,key[i]));
            }

            timeValue.clear();
            timeValue.put("USD",lists2.get(0));
            timeValue.put("EUR",lists2.get(1));
            timeValue.put("CHF",lists2.get(2));
            timeValue.put("AUD",lists2.get(3));
            timeValue.put("AZN",lists2.get(4));
            timeValue.put("GBP",lists2.get(5));
            timeValue.put("AMD",lists2.get(6));
            timeValue.put("BYN",lists2.get(7));
            timeValue.put("BGN",lists2.get(8));
            timeValue.put("BRL",lists2.get(9));
            timeValue.put("HUF",lists2.get(10));
            timeValue.put("HKD",lists2.get(11));
            timeValue.put("DKK",lists2.get(12));
            timeValue.put("INR",lists2.get(13));
            timeValue.put("KZT",lists2.get(14));

            output(context,timeValue);

        }catch (Exception e){
            e.printStackTrace();
        }
        return timeValue;
    }


    private ArrayList<Double>  classification(ArrayList<Double> list, HashMap<String, Double> value, String key){

        if (list.isEmpty()){
            list.add(value.get(key));
        }else {
            int size=list.size()-1;

            if (list.get(size)>value.get(key) | list.get(size)<value.get(key)){
                list.add(value.get(key));
            }
        }
        return list;
    }


    @SuppressWarnings("unchecked")
    private HashMap<String,ArrayList<Double>> input(Context context, HashMap<String, ArrayList<Double>> list) {

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



    private void output(Context context, HashMap<String, ArrayList<Double>> list) {

        try {
            FileOutputStream fileOutputStream = context.openFileOutput("Mani.ser", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Observable <HashMap<String,ArrayList<Double>>> getTimeValte() {
        return Observable.create(observableEmitter ->{

            observableEmitter.onNext(timeVValue());
        });
    }
}
