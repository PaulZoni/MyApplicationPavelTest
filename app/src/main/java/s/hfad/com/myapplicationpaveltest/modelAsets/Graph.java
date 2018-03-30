package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import io.reactivex.Observable;
import s.hfad.com.myapplicationpaveltest.modelAsets.DBHelperAssets;

public class Graph {

    private int sumID;
    DBHelperAssets dbHelper;
    Cursor cursor;
    SQLiteDatabase database;

    Context context;
    public Graph(Context context){
        this.context=context;
        dbHelper=new DBHelperAssets(context);
    }

    public ArrayList<Integer> graf(){


        ArrayList<Integer>listX=new ArrayList<>();
        database=dbHelper.getWritableDatabase();
        cursor=database.query(DBHelperAssets.TABLE_ASSETS,null,null,null,null,null,null);
        if (cursor.moveToFirst()){

            sumID=cursor.getColumnIndex(DBHelperAssets.KEY_VALUE);
            do {
                listX.add(Integer.valueOf(cursor.getString(sumID)));

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
