package com.example.android_pzpi_23_6_kiliarova_anna_pract_task1;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button changeTextButton;
    private TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Activity initialized");

        changeTextButton = findViewById(R.id.changeTextButton);
        displayText = findViewById(R.id.displayText);

        if (changeTextButton != null && displayText != null) {
            changeTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayText.setText("The text has been updated!");
                    Toast.makeText(MainActivity.this, "Text Updated", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Button clicked: Text updated");
                }
            });
        } else {
            Log.w(TAG, "onCreate: Button or TextView not found in layout");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity is becoming visible");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity is no longer visible");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity is being paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity has resumed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Cleaning up activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Activity is restarting");
    }
}