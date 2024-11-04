package com.example.lab3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowRecordsActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        dbHelper = new DBHelper(this);

        TextView recordsView = findViewById(R.id.records_view);

        Cursor cursor = dbHelper.getAllGroupmates();
        StringBuilder builder = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("FIO"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("AddTime"));
                builder.append(name).append(" - ").append(time).append("\n");
            } while (cursor.moveToNext());
        }
        recordsView.setText(builder.toString());
    }
}

