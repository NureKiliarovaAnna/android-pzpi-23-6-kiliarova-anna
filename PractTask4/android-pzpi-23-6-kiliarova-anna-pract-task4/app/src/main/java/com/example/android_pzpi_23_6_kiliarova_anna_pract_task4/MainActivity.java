package com.example.android_pzpi_23_6_kiliarova_anna_pract_task4;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText inputName, inputAge;
    private Button btnSaveData, btnViewUsers, btnSaveToFile, btnLoadFromFile;
    private TextView displayTextView;
    private SharedPreferences userPreferences;
    private DataManager databaseManager;

    private static final String PREFERENCES_NAME = "UserDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        userPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        databaseManager = new DataManager(this);

        loadPreferences();

        btnSaveData.setOnClickListener(v -> {
            savePreferences();
            saveToDatabase();
        });

        btnViewUsers.setOnClickListener(v -> showUsersFromDatabase());

        btnSaveToFile.setOnClickListener(v -> saveToFile());

        btnLoadFromFile.setOnClickListener(v -> loadFromFile());
    }

    private void initializeViews() {
        inputName = findViewById(R.id.nameInput);
        inputAge = findViewById(R.id.ageInput);
        btnSaveData = findViewById(R.id.saveDataButton);
        btnViewUsers = findViewById(R.id.viewUsersButton);
        btnSaveToFile = findViewById(R.id.saveFileButton);
        btnLoadFromFile = findViewById(R.id.loadFileButton);
        displayTextView = findViewById(R.id.displayTextView);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("userName", inputName.getText().toString());
        editor.putInt("userAge", Integer.parseInt(inputAge.getText().toString()));
        editor.apply();
        loadPreferences();
    }

    private void loadPreferences() {
        String name = userPreferences.getString("userName", "No Data");
        int age = userPreferences.getInt("userAge", 0);
        displayTextView.setText("Name: " + name + ", Age: " + age);
    }

    private void saveToDatabase() {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", inputName.getText().toString());
        values.put("age", Integer.parseInt(inputAge.getText().toString()));

        long result = db.insert("users", null, values);
        if (result != -1) {
            Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save data!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void showUsersFromDatabase() {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        Cursor cursor = db.query("users", null, null, null, null, null, null);

        StringBuilder data = new StringBuilder();
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            do {
                String name = cursor.getString(nameIndex);
                int age = cursor.getInt(ageIndex);
                data.append("Name: ").append(name).append(", Age: ").append(age).append("\n");
            } while (cursor.moveToNext());
        } else {
            data.append("No data available.");
        }

        displayTextView.setText(data.toString());
        cursor.close();
        db.close();
    }

    private void saveToFile() {
        String data = inputName.getText().toString();
        try (FileOutputStream fos = openFileOutput("userdata.txt", MODE_PRIVATE)) {
            fos.write(data.getBytes());
            Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput("userdata.txt")) {
            int c;
            StringBuilder fileData = new StringBuilder();
            while ((c = fis.read()) != -1) {
                fileData.append((char) c);
            }
            displayTextView.setText(fileData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
