package appexpress.com.matebeto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }




    public void insert(String car, String price, String description, String image, String phone, String email, String town ) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.CAR, car);
        contentValue.put(DatabaseHelper.PRICE, price);
        contentValue.put(DatabaseHelper.DESCRIPTION, description);
        contentValue.put(DatabaseHelper.IMAGE, image);
        contentValue.put(DatabaseHelper.PHONE, phone);
        contentValue.put(DatabaseHelper.EMAIL, email);
        contentValue.put(DatabaseHelper.TOWN, town);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.CAR, DatabaseHelper.PRICE,
                DatabaseHelper.DESCRIPTION, DatabaseHelper.IMAGE, DatabaseHelper.PHONE, DatabaseHelper.EMAIL, DatabaseHelper.TOWN};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String car, String price, String description, String image, String phone, String email, String town) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CAR, car);
        contentValues.put(DatabaseHelper.PRICE, price);
        contentValues.put(DatabaseHelper.DESCRIPTION, description);
        contentValues.put(DatabaseHelper.IMAGE, image);
        contentValues.put(DatabaseHelper.PHONE, phone);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.TOWN, town);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
} 