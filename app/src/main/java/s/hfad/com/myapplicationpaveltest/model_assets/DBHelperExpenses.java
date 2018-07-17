package s.hfad.com.myapplicationpaveltest.model_assets;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperExpenses extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION_EXPENSES=1;
    public static final String DATABASE_NAME_EXPENSES="expensesDb";
    public static final String TABLE_EXPENSES="assets";

    public static final String KEY_COMMENT_EXPENSES="commentExpenses";
    public static final String KEY_VALUE_EXPENSES="valueExpenses";
    public static final String KEY_DATA_EXPENSES="dataExpenses";
    public static final String KEY_ID="_id";
    public static final String KEY_LOCATION = "location_expenses";

    public DBHelperExpenses(Context context) {
        super(context, DATABASE_NAME_EXPENSES, null, DATABASE_VERSION_EXPENSES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_EXPENSES+ " ("+ KEY_ID
                + " integer primary key,"
                + KEY_COMMENT_EXPENSES + " text,"
                + KEY_VALUE_EXPENSES +" text,"
                + KEY_DATA_EXPENSES +" text,"
                + KEY_LOCATION + " text" + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
