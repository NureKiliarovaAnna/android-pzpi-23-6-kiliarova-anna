package nure.kiliarova.anna.android_pzpi_23_6_kiliarova_anna_pract_task2_part1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView labelText;
    private boolean isTextLarge = false;
    private boolean isTextRed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelText = findViewById(R.id.labelText);
    }

    public void onTextUpdateClick(View view) {
        labelText.setText("Text has been updated!");
    }

    public void onToastClick(View view) {
        Toast.makeText(this, "This is a toast message!", Toast.LENGTH_SHORT).show();
    }

    public void onAction1Click(View view) {
        labelText.setTextSize(isTextLarge ? 16 : 22);
        isTextLarge = !isTextLarge;
    }

    public void onAction2Click(View view) {
        labelText.setTextColor(isTextRed ? Color.BLACK : Color.RED);
        isTextRed = !isTextRed;
    }
}