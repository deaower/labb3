package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        Button showRecords = findViewById(R.id.show_records);
        Button addRecord = findViewById(R.id.add_record);
        Button updateRecord = findViewById(R.id.update_record);

        // Показать записи
        showRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowRecordsActivity.class);
            startActivity(intent);
        });

        // Добавить новую запись
        addRecord.setOnClickListener(v -> dbHelper.insertGroupmate("Новый студент"));

        // Обновить последнюю запись
        updateRecord.setOnClickListener(v -> dbHelper.updateLastRecord("Иванов Иван Иванович"));
    }
}
