package s.hfad.com.myapplicationpaveltest.model_assets;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperAssets extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="monetaryAssetsDb";
    public static final String TABLE_ASSETS="assets";

    public static final String KEY_ID="_id";
    public static final String KEY_COMMENT="comment";
    public static final String KEY_VALUE="value";
    public static final String KEY_DATA="data";
    public static final String KEY_LOCATION = "key_locations";

    public DBHelperAssets(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ TABLE_ASSETS+ " ("+ KEY_ID
                + " integer primary key,"
                + KEY_COMMENT +" text,"
                + KEY_VALUE + " text,"
                + KEY_DATA + " text,"
                + KEY_LOCATION + " text" + " )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_ASSETS);
        onCreate(db);
    }

}

