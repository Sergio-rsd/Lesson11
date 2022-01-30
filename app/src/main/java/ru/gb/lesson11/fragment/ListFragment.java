package ru.gb.lesson11.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.lesson11.R;
import ru.gb.lesson11.data.InMemoryRepoImpl;
import ru.gb.lesson11.data.Note;
import ru.gb.lesson11.data.PopupMenuClick;
import ru.gb.lesson11.data.Repo;
import ru.gb.lesson11.dialog.NoteDialog;
import ru.gb.lesson11.recycler.NoteHolder;
import ru.gb.lesson11.recycler.NotesAdapter;
import ru.gb.lesson11.ui.MainActivity;

public class ListFragment extends Fragment
        implements
////        PopupMenuClick,
        NoteDialog.NoteDialogController {

    private Repo repository = InMemoryRepoImpl.getInstance();
    private NotesAdapter adapter = new NotesAdapter();
    RecyclerView listAdapter;
    private Note note;
    public static final String NOTE = "NOTE";

    public interface RecyclerController {
        void connect();
    }

    private RecyclerController recyclerController;

    @Override
    public void onAttach(@NonNull Context context) {
        this.recyclerController = (RecyclerController) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recycle_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        adapter.setOnPopupMenuClick((PopupMenuClick) requireContext());
        listAdapter = view.findViewById(R.id.list_notes);
        listAdapter.setAdapter(adapter);
        listAdapter.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter.setNotes(repository.getAll());

        // Helper
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlag);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                NoteHolder holder = (NoteHolder) viewHolder;
                Note note = holder.getNote();
                repository.delete(note.getId());
                adapter.delete(repository.getAll(), position);

            }
        });
        helper.attachToRecyclerView(listAdapter);
    }
/*

    @Override
    public void click(int command, Note note, int position) {
        switch (command) {
            case R.id.context_delete:
                repository.delete(note.getId());
                adapter.delete(repository.getAll(), position);
                return;
            case R.id.context_modify:
                NoteDialog.getInstance(note).show(
//                        ((NoteDialog.NoteDialogController)requireContext()).getSupportFragmentManager(),
//                        getParentFragmentManager(),
//                        getSupportFragmentManager(),
                        getChildFragmentManager(),
                        NoteDialog.NOTE
                );

                return;
        }
    }
*/

    @Override
    public void update(Note note) {
        repository.update(note);
        adapter.setNotes(repository.getAll());
    }

    @Override
    public void create(String title, String description, String interest, String dataPerformance) {
        Note note = new Note(title, description, interest, dataPerformance);
        repository.create(note);
        adapter.setNotes(repository.getAll());
    }

    /*        @Override
        public void create(Note note) {
    //        Note note = new Note(title, description, interest, dataPerformance);
            repository.create(note);
            adapter.setNotes(repository.getAll());
        }

        */
    public Note passData(Note note) {
        this.note = note;
        return note;
    }

    public void delete(Note note, int position) {
        repository.delete(note.getId());
        adapter.delete(repository.getAll(), position);
    }
}
