package appexpress.com.matebeto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "CARS";


    // Table columns
    public static final String _ID = "_id";
    public static final String CAR = "car";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String TOWN= "town";

    // Database Information
    static final String DB_NAME = "CAR_DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAR + " TEXT NOT NULL, " + PRICE + " TEXT, "
            + DESCRIPTION + " TEXT, " + IMAGE + " TEXT, " + PHONE + " TEXT, " +  EMAIL + " TEXT, " + TOWN + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

} 