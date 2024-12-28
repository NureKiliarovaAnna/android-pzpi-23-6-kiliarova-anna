package com.example.android_pzpi_23_6_kiliarova_anna_lab_task4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes; // Поточний список нотаток
    private final List<Note> notesFull; // Оригінальний список нотаток
    private final Context context;

    public NoteAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
        this.notesFull = new ArrayList<>(notes); // Копія для оригінального списку
    }

    public interface OnItemClickListener {
        void onItemClick(Note note, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvDateTime.setText(note.getDateTime());
        holder.ivPriority.setImageResource(getPriorityIcon(note.getPriority()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(notes.get(currentPosition), currentPosition);
                }
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                ((MainActivity) context).selectedNotePosition = currentPosition;
            }
            return false;
        });
    }

    private int getPriorityIcon(int priority) {
        switch (priority) {
            case 1:
                return R.drawable.ic_priority_low;
            case 2:
                return R.drawable.ic_priority_medium;
            case 3:
                return R.drawable.ic_priority_high;
            default:
                return R.drawable.ic_priority_low;
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Метод фільтрації
    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            notes.clear();
            notes.addAll(notesFull); // Відновлюємо оригінальний список
        } else {
            String lowerCaseQuery = query.toLowerCase();
            List<Note> filteredList = new ArrayList<>();
            for (Note note : notesFull) {
                if (note.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        note.getDescription().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(note);
                }
            }
            notes.clear();
            notes.addAll(filteredList); // Оновлюємо видимий список
        }
        notifyDataSetChanged(); // Оновлюємо RecyclerView
    }

    // Метод для додавання нової нотатки
    public void addNote(Note note) {
        notes.add(note);
        notesFull.add(note); // Додаємо в обидва списки
        notifyItemInserted(notes.size() - 1);
    }

    // Метод для оновлення нотатки
    public void updateNote(Note note, int position) {
        notes.set(position, note);
        int indexInFullList = notesFull.indexOf(notesFull.get(position));
        if (indexInFullList != -1) {
            notesFull.set(indexInFullList, note); // Оновлюємо в обох списках
        }
        notifyItemChanged(position);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDateTime;
        ImageView ivPriority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            ivPriority = itemView.findViewById(R.id.ivPriority);
        }
    }
}