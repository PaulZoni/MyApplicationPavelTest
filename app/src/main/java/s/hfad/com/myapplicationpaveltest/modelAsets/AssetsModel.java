package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.Date;

public class AssetsModel {

    private SQLiteDatabase database;
    private DBHelperAssets dbHelperAssets;
    private ContentValues contentValues;
    private Cursor cursor;
    private StringBuilder stringBuilder;


    private int comentID;
    private int sumID;
    private int dataID;
    private static String string;
    private Context context;

    public AssetsModel(Context context){
        dbHelperAssets=new DBHelperAssets(context);
        contentValues = new ContentValues();
        this.context=context;
    }



    public void buttonOk(String sum,String coment){

        database=dbHelperAssets.getWritableDatabase();
        contentValues.put(DBHelperAssets.KEY_COMMENT,coment);
        contentValues.put(DBHelperAssets.KEY_VALUE,sum);
        contentValues.put(DBHelperAssets.KEY_DATA,String.valueOf(new Date().toString()));
        database.insert(DBHelperAssets.TABLE_ASSETS,null,contentValues);

        dbHelperAssets.close();


    }



    public String buttonAll(){


            database=dbHelperAssets.getWritableDatabase();
            cursor=database.query(DBHelperAssets.TABLE_ASSETS,null,null,null,null,null,null);

            if (cursor.moveToFirst()) {



                comentID = cursor.getColumnIndex(DBHelperAssets.KEY_COMMENT);
                sumID = cursor.getColumnIndex(DBHelperAssets.KEY_VALUE);
                dataID = cursor.getColumnIndex(DBHelperAssets.KEY_DATA);

                stringBuilder = new StringBuilder();

                do {
                    stringBuilder.append("ПОТРАЧЕНО=" + cursor.getString(sumID)
                            + " КОМЕНТАРИЙ=" + cursor.getString(comentID)
                            + "ДАТА=" + cursor.getString(dataID)+ "\n");

                     string = String.valueOf(stringBuilder);




                } while (cursor.moveToNext());

                return string;

            }


        cursor.close();
        dbHelperAssets.close();

        return string;

    }


}




















