package com.example.android_pzpi_23_6_kiliarova_anna_lab_task4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioGroup fontSizeGroup = findViewById(R.id.radioGroupFontSize);

        SharedPreferences preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        String currentFontSize = preferences.getString("font_size", "medium");

        // Встановлюємо поточний вибір
        if (currentFontSize.equals("small")) {
            fontSizeGroup.check(R.id.radioSmall);
        } else if (currentFontSize.equals("large")) {
            fontSizeGroup.check(R.id.radioLarge);
        } else {
            fontSizeGroup.check(R.id.radioMedium);
        }

        // Обробка вибору
        fontSizeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = preferences.edit();

            if (checkedId == R.id.radioSmall) {
                editor.putString("font_size", "small");
            } else if (checkedId == R.id.radioLarge) {
                editor.putString("font_size", "large");
            } else {
                editor.putString("font_size", "medium");
            }

            editor.apply();
        });
    }
}