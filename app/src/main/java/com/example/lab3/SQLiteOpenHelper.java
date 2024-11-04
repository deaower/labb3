package com.example.lab3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "groupmates.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Groupmates";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "FIO";
    private static final String COLUMN_TIME = "AddTime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создаем таблицу Groupmates
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(createTable);

        // Добавляем 5 записей
        for (int i = 1; i <= 5; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "Студент " + i);
            db.insert(TABLE_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Вставка новой записи
    public void insertGroupmate(String fio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, fio);
        db.insert(TABLE_NAME, null, values);
    }

    // Обновление последней записи
    public void updateLastRecord(String fio) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + " = ? WHERE " + COLUMN_ID + " = (SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_NAME + ")";
        db.execSQL(query, new String[]{fio});
    }

    // Получение всех записей
    public Cursor getAllGroupmates() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
