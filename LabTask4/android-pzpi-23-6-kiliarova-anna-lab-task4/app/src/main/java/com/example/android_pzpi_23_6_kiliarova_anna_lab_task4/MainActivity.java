package com.example.android_pzpi_23_6_kiliarova_anna_lab_task4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public int selectedNotePosition = -1; // Для вибраної нотатки
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        // Ініціалізація адаптера
        noteAdapter = new NoteAdapter(notes, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        // Реєстрація RecyclerView для контекстного меню
        registerForContextMenu(recyclerView);

        // Додавання нової нотатки
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivityForResult(intent, 100); // Код 100 для створення
        });

        // Редагування існуючої нотатки
        noteAdapter.setOnItemClickListener((note, position) -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra("note", note);
            intent.putExtra("position", position);
            startActivityForResult(intent, 200);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Note note = (Note) data.getSerializableExtra("note");
            noteAdapter.addNote(note); // Додавання нової нотатки
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            Note note = (Note) data.getSerializableExtra("note");
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                noteAdapter.updateNote(note, position); // Оновлення нотатки
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showDeleteConfirmationDialog(); // Видалення нотатки
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete)
                .setMessage(R.string.confirm_delete_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    notes.remove(selectedNotePosition);
                    noteAdapter.notifyItemRemoved(selectedNotePosition); // Оновлення списку
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                noteAdapter.filter(query); // Пошук
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteAdapter.filter(newText); // Пошук у реальному часі
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_priority) {
            sortNotesByPriority(); // Сортування за пріоритетом
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortNotesByPriority() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(notes, Comparator.comparingInt(Note::getPriority).reversed());
        }
        noteAdapter.notifyDataSetChanged();
    }
}