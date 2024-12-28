package com.example.android_pzpi_23_6_kiliarova_anna_lab_task4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    private EditText etTitle, etDescription;
    private Spinner spPriority;
    private ImageView ivImage;
    private TextView tvDateTime;
    private String imagePath = "";
    private Calendar appointmentCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        spPriority = findViewById(R.id.spPriority);
        ivImage = findViewById(R.id.ivImage);
        tvDateTime = findViewById(R.id.tvDateTime);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnSetDateTime = findViewById(R.id.btnSetDateTime);

        // Отримання переданих даних (редагування)
        Intent intent = getIntent();
        Note note = (Note) intent.getSerializableExtra("note");
        int position = intent.getIntExtra("position", -1);

        if (note != null) {
            etTitle.setText(note.getTitle());
            etDescription.setText(note.getDescription());
            spPriority.setSelection(note.getPriority() - 1);
            imagePath = note.getImagePath();
            tvDateTime.setText(note.getDateTime());

            if (imagePath != null && !imagePath.isEmpty()) {
                ivImage.setImageURI(Uri.parse(imagePath));
            }
        }

        // Вибір дати і часу
        btnSetDateTime.setOnClickListener(v -> showDateTimePicker());

        // Вибір зображення з галереї
        ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imagePath = uri.toString();
                        ivImage.setImageURI(uri);
                    }
                }
        );

        ivImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        // Збереження нотатки
        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String description = etDescription.getText().toString();
            int priority = spPriority.getSelectedItemPosition() + 1;
            String dateTime = tvDateTime.getText().toString();

            Note newNote = new Note(title, description, priority, dateTime, imagePath);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("note", newNote);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void showDateTimePicker() {
        // Вибір дати
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            appointmentCalendar.set(Calendar.YEAR, year);
            appointmentCalendar.set(Calendar.MONTH, month);
            appointmentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Після вибору дати — вибір часу
            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                appointmentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                appointmentCalendar.set(Calendar.MINUTE, minute);

                // Форматування дати та часу
                String formattedDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        .format(appointmentCalendar.getTime());
                tvDateTime.setText(formattedDateTime);
            }, appointmentCalendar.get(Calendar.HOUR_OF_DAY), appointmentCalendar.get(Calendar.MINUTE), true).show();

        }, appointmentCalendar.get(Calendar.YEAR), appointmentCalendar.get(Calendar.MONTH),
                appointmentCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}