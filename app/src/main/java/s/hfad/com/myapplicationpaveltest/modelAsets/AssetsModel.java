package s.hfad.com.myapplicationpaveltest.modelAsets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import s.hfad.com.myapplicationpaveltest.R;

public class AssetsModel {

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private ContentValues contentValues;
    private Cursor cursor;
    public static  final String KEY_ASSETS="assets_key";
    public  static final String KEY_EXPENSES="expenses_key";
    private int comentID;
    private int sumID;
    private int dataID;
    private int locationID;
    private Context context;
    private static String KeyDB;
    private LoadingLocation locatr;
    private Transaction mTransaction;


    public AssetsModel(Context context,String key){
        KeyDB=key;
        locatr = new LoadingLocation(context);
        if (key.equals(KEY_ASSETS)){
            dbHelper=new DBHelperAssets(context);

        }else if (key.equals(KEY_EXPENSES)){
            dbHelper=new DBHelperExpenses(context);
        }
        contentValues = new ContentValues();
        this.context=context;
    }


    public void buttonOk(String sum,String comment){
        database=dbHelper.getWritableDatabase();
        int i = 0;
        while (locatr.getLocation() == null | i<3000 )i++;

        if (KeyDB.equals(KEY_ASSETS)) {
            contentValues.put(DBHelperAssets.KEY_LOCATION, String.valueOf(locatr.getLocation()));
            contentValues.put(DBHelperAssets.KEY_COMMENT, comment);
            contentValues.put(DBHelperAssets.KEY_VALUE, sum);
            contentValues.put(DBHelperAssets.KEY_DATA, String.valueOf(new Date().toString()));
            database.insert(DBHelperAssets.TABLE_ASSETS, null, contentValues);

        } else if (KeyDB.equals(KEY_EXPENSES)) {
            contentValues.put(DBHelperExpenses.KEY_LOCATION, String.valueOf(locatr.getLocation()));
            contentValues.put(DBHelperExpenses.KEY_COMMENT_EXPENSES, comment);
            contentValues.put(DBHelperExpenses.KEY_VALUE_EXPENSES, sum);
            contentValues.put(DBHelperExpenses.KEY_DATA_EXPENSES, String.valueOf(new Date().toString()));
            database.insert(DBHelperExpenses.TABLE_EXPENSES, null, contentValues);
        }
        dbHelper.close();
    }


    public List<Transaction> buttonAll(){
            List<Transaction>list=new ArrayList<>();
            database=dbHelper.getWritableDatabase();

            if (KeyDB.equals(KEY_ASSETS)){
                cursor=database.query(DBHelperAssets.TABLE_ASSETS,null,null,null,null,null,null);

            }else if (KeyDB.equals(KEY_EXPENSES)){
                cursor=database.query(DBHelperExpenses.TABLE_EXPENSES,null,null,null,null,null,null);
            }

            if (cursor.moveToFirst()) {

                if (KeyDB.equals(KEY_ASSETS)){
                    comentID = cursor.getColumnIndex(DBHelperAssets.KEY_COMMENT);
                    sumID = cursor.getColumnIndex(DBHelperAssets.KEY_VALUE);
                    dataID = cursor.getColumnIndex(DBHelperAssets.KEY_DATA);
                    locationID = cursor.getColumnIndex(DBHelperAssets.KEY_LOCATION);

                }else if (KeyDB.equals(KEY_EXPENSES)){
                    comentID = cursor.getColumnIndex(DBHelperExpenses.KEY_COMMENT_EXPENSES);
                    sumID = cursor.getColumnIndex(DBHelperExpenses.KEY_VALUE_EXPENSES);
                    dataID = cursor.getColumnIndex(DBHelperExpenses.KEY_DATA_EXPENSES);
                    locationID = cursor.getColumnIndex(DBHelperExpenses.KEY_LOCATION);
                }

                    do next: {
                        int i=0;
                        String sum = " ПОТРАЧЕНО: " + cursor.getString(sumID);
                        String comment = " КОМЕНТАРИЙ: " + cursor.getString(comentID);
                        String data = " ДАТА: " + cursor.getString(dataID);
                        String location = cursor.getString(locationID);
                        mTransaction = new Transaction(sum, data, comment, location);

                        list.add(i,mTransaction);

                        i++;
                    } while (cursor.moveToNext());
                return list;
            }

        cursor.close();
        dbHelper.close();

        if (list.isEmpty()) {
            String voidList = context.getString(R.string.voidList);
            mTransaction = new Transaction(voidList, voidList, voidList, voidList);
            list.add(mTransaction);
        }
        return list;
    }

    public void dbClear(){
        SQLiteOpenHelper helper=new DBHelperAssets(context);
        database=helper.getWritableDatabase();
        database.delete(DBHelperAssets.TABLE_ASSETS,null,null);

        helper=new DBHelperExpenses(context);
        database=helper.getWritableDatabase();
        database.delete(DBHelperExpenses.TABLE_EXPENSES,null,null);

        database.close();
        helper.close();
    }
}




















