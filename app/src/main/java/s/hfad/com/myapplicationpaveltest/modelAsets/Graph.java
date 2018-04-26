package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;


public class Graph {

    private int sumID;
    private SQLiteOpenHelper dbHelperExp;
    private SQLiteOpenHelper dbHelperAss;
    private Cursor cursor;
    private SQLiteDatabase databaseExp;
    private SQLiteDatabase databaseAss;
    static  final String P_KEY_ASSETS="P assets";
    static  final String P_KEY_EXPENSES="P expenses";
    private String key;
    private HashMap<String,Float> listX;

    Context context;
    public Graph(Context context,String key){
        this.key=key;
        this.context=context;
        dbHelperAss=new DBHelperAssets(context);
        dbHelperExp=new DBHelperExpenses(context);
        listX=new HashMap();
    }

    public HashMap<String,Float> graf(){


        ArrayList<Integer>listAss=new ArrayList<>();
        ArrayList<Integer>listExp=new ArrayList<>();

        databaseAss=dbHelperAss.getWritableDatabase();
        databaseExp=dbHelperExp.getWritableDatabase();


        cursor=databaseAss.query(DBHelperAssets.TABLE_ASSETS,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            sumID=cursor.getColumnIndex(DBHelperAssets.KEY_VALUE);
            do {
                String sum=cursor.getString(sumID);

                if (sum!=null){
                    listAss.add(Integer.valueOf(sum));
                }

            }while (cursor.moveToNext());
        }

        cursor=databaseExp.query(DBHelperExpenses.TABLE_EXPENSES,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            sumID=cursor.getColumnIndex(DBHelperExpenses.KEY_VALUE_EXPENSES);
            do {
                String sum=cursor.getString(sumID);

                if (sum!=null){
                    listExp.add(Integer.valueOf(sum));
                }

            }while (cursor.moveToNext());
        }


        listAss=sum(listAss);
        listExp=sum(listExp);

        listX=P(listAss,listExp);





        cursor.close();
        dbHelperExp.close();
        dbHelperAss.close();

        return listX;
    }

    private ArrayList<Integer>sum(ArrayList<Integer>list){
        ArrayList<Integer>listX=new ArrayList<>();
        if (!list.isEmpty()){
            int sum=0 ;
            for (int i = 0; i <list.size() ; i++) {

                 sum=sum+list.get(i);

            }
             listX.add(sum);
        }else{
            listX.add(0);
        }


        return listX;
    }


    private HashMap<String, Float> P(ArrayList<Integer>listAss, ArrayList<Integer>listExp){
        HashMap<String,Float> list=new HashMap<>();
        float sum=listAss.get(0);
        float min=listExp.get(0);
        float P_expenses=(min/sum)*100;
        float P_assets=(100-P_expenses);
        list.put(P_KEY_EXPENSES,P_expenses);
        list.put(P_KEY_ASSETS,P_assets);
        return list;
    }

    public Observable<HashMap<String,Float>> getGraph(){
        return Observable.create(observableEmitter->{


            observableEmitter.onNext(graf());
        });
    }

}
