package s.hfad.com.myapplicationpaveltest.modelAsets;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssetsModel {

    private SQLiteDatabase database;
    private DBHelperAssets dbHelperAssets;
    private ContentValues contentValues;
    private Cursor cursor;



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



    public List<String> buttonAll(){

            List<String>list=null;
            database=dbHelperAssets.getWritableDatabase();
            cursor=database.query(DBHelperAssets.TABLE_ASSETS,null,null,null,null,null,null);

            if (cursor.moveToFirst()) {



                comentID = cursor.getColumnIndex(DBHelperAssets.KEY_COMMENT);
                sumID = cursor.getColumnIndex(DBHelperAssets.KEY_VALUE);
                dataID = cursor.getColumnIndex(DBHelperAssets.KEY_DATA);

                //stringBuilder = new StringBuilder();
                list=new ArrayList<>();

                do {
                    int i=0;

                        String s="ПОТРАЧЕНО=" + cursor.getString(sumID)
                                + " КОМЕНТАРИЙ=" + cursor.getString(comentID)
                                + "ДАТА=" + cursor.getString(dataID)+ "\n";
                        list.add(i,s);

                        i++;
                } while (cursor.moveToNext());




                return list;

            }


        cursor.close();
        dbHelperAssets.close();

        return list;

    }

    public void dbClear(){
        database=dbHelperAssets.getWritableDatabase();
        database.delete(DBHelperAssets.TABLE_ASSETS,null,null);
        dbHelperAssets.close();
    }

}




















