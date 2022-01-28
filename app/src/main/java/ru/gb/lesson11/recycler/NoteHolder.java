package ru.gb.lesson11.recycler;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.lesson11.R;
import ru.gb.lesson11.data.Note;
import ru.gb.lesson11.data.PopupMenuClick;

public class NoteHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private TextView title;
    private TextView description;
    private TextView interest;
    private TextView dataPerformance;
    private Note note;
    private ImageView noteMenu;
    private PopupMenu popupMenu;
    private PopupMenuClick listener;


    public NoteHolder(@NonNull View itemView, PopupMenuClick listener) {
        super(itemView);
        this.listener = listener;

        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        interest = itemView.findViewById(R.id.interest_currency);
        dataPerformance = itemView.findViewById(R.id.data_stamp);

        noteMenu = itemView.findViewById(R.id.note_menu);
        popupMenu = new PopupMenu(itemView.getContext(), noteMenu);
        popupMenu.inflate(R.menu.context);

        noteMenu.setOnClickListener(v -> popupMenu.show());
        popupMenu.setOnMenuItemClickListener(this);

    }

    void bind(Note note) {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        interest.setText(note.getInterest());
        dataPerformance.setText(note.getDataPerformance());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_modify:
                listener.click(R.id.context_modify, note, getAdapterPosition());
                return true;
            case R.id.context_delete:
                listener.click(R.id.context_delete, note, getAdapterPosition());
                return true;
            default:
                return false;
        }
    }

    public Note getNote() {
        return note;
    }
}
