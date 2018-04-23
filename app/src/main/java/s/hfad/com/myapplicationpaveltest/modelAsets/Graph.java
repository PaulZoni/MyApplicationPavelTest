package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

import io.reactivex.Observable;


public class Graph {

    private int sumID;
    private SQLiteOpenHelper dbHelperExp;
    private SQLiteOpenHelper dbHelperAss;
    private Cursor cursor;
    private SQLiteDatabase databaseExp;
    private SQLiteDatabase databaseAss;
    static private final String KEY_ASSETS="assets_key";
    static private final String KEY_EXPENSES="expenses_key";
    private String key;


    Context context;
    public Graph(Context context,String key){
        this.key=key;
        this.context=context;
        dbHelperAss=new DBHelperAssets(context);
        dbHelperExp=new DBHelperExpenses(context);
    }

    public ArrayList<Integer> graf(){

        ArrayList<Integer>listX=new ArrayList<>();
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


        listAss=sum2(listAss);
        listExp=sum2(listExp);

        listX.add(0,listAss.get(0)-listExp.get(0));
        //listX.add(0,listAss.get(0));
        listX.add(1,listExp.get(0)-listAss.get(0));


        cursor.close();
        dbHelperExp.close();
        dbHelperAss.close();

        return listX;
    }

    private ArrayList<Integer>sum2(ArrayList<Integer>list){
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

    /*private ArrayList<Integer>sum(ArrayList<Integer>list){
        ArrayList<Integer>listX=new ArrayList<>();
        if (!list.isEmpty()){
            int z=0;
            for (int i = 0; i <list.size() ; i++) {
                int y=list.get(i);
                if (!listX.isEmpty()){
                    z=listX.get(i-1);
                }


                listX.add(y+z);
            }
        }
        return listX;
    }*/

    public Observable<ArrayList<Integer>> getGraph(){
        return Observable.create(observableEmitter->{


            observableEmitter.onNext(graf());
        });
    }

}
