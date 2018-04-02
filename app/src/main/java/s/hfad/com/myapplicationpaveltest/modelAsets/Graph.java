package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

import io.reactivex.Observable;


public class Graph {

    private int sumID;
    SQLiteOpenHelper dbHelper;
    Cursor cursor;
    SQLiteDatabase database;
    static private final String KEY_ASSETS="assets_key";
    static private final String KEY_EXPENSES="expenses_key";
    private String key;


    Context context;
    public Graph(Context context,String key){
        this.key=key;
        this.context=context;
        if (key.equals(KEY_ASSETS)){
            dbHelper=new DBHelperAssets(context);

        }else if (key.equals(KEY_EXPENSES)){
            dbHelper=new DBHelperExpenses(context);
        }

    }

    public ArrayList<Integer> graf(){


        ArrayList<Integer>listX=new ArrayList<>();
        database=dbHelper.getWritableDatabase();

        if (key.equals(KEY_ASSETS)){
            cursor=database.query(DBHelperAssets.TABLE_ASSETS,null,null,null,null,null,null);

        }else if (key.equals(KEY_EXPENSES)){
            cursor=database.query(DBHelperExpenses.TABLE_EXPENSES,null,null,null,null,null,null);
        }

        if (cursor.moveToFirst()){

            if (key.equals(KEY_ASSETS)){
                sumID=cursor.getColumnIndex(DBHelperAssets.KEY_VALUE);

            }else if (key.equals(KEY_EXPENSES)){
                sumID=cursor.getColumnIndex(DBHelperExpenses.KEY_VALUE_EXPENSES);
            }

            do {
                String sum=cursor.getString(sumID);

                if (sum!=null){
                    listX.add(Integer.valueOf(sum));
                }

            }while (cursor.moveToNext());

        }

        cursor.close();
        dbHelper.close();

        return listX;
    }


    public Observable<ArrayList<Integer>> getGraph(){
        return Observable.create(observableEmitter->{


            observableEmitter.onNext(graf());
        });
    }

}
