package ru.gb.lesson11.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

import ru.gb.lesson11.R;
import ru.gb.lesson11.data.InMemoryRepoImpl;
import ru.gb.lesson11.data.Note;
import ru.gb.lesson11.data.PopupMenuClick;
import ru.gb.lesson11.data.Repo;
import ru.gb.lesson11.data.YesNoDialogController;
import ru.gb.lesson11.dialog.NoteDialog;
import ru.gb.lesson11.dialog.YesNoDialog;
import ru.gb.lesson11.fragment.AboutFragment;
import ru.gb.lesson11.fragment.ListFragment;
import ru.gb.lesson11.recycler.NoteHolder;
import ru.gb.lesson11.recycler.NotesAdapter;

//import ru.gb.lesson11.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
//        implements PopupMenuClick, NoteDialog.NoteDialogController, YesNoDialogController {
        implements YesNoDialogController,
        PopupMenuClick,
        ListFragment.RecyclerController,
        NoteDialog.NoteDialogController {
//ListFragment.RecyclerController{

// TODO убрать 1

    private Repo repository = InMemoryRepoImpl.getInstance();
    public static final String LIST_FRAGMENT = "LIST_FRAGMENT";
    public static final String NOTE_STATE = "NOTE_STATE";
    public static final String ABOUT = "ABOUT";

    ListFragment listNotes;

    private Note note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbarAndDrawer();
        ListFragment listNoteFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);


        if (listNoteFragment == null) {
            listNotes = new ListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, listNotes, LIST_FRAGMENT)
                    .commit();

        } else {
            listNotes = (ListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);
        }

    }

    private void initToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view_drawer);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_drawer_about:
                    openAboutFragment();
                    drawer.close();
                    return true;
                case R.id.action_drawer_exit:
//                    finish();
                    showYesNoDialogFragment();
                    return true;
            }
            return false;
        });
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.fragment_container, new AboutFragment(), ABOUT)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_create:
                NoteDialog.getInstance(null).show(
                        getSupportFragmentManager(),
                        NoteDialog.NOTE
                );
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void click(int command, Note note, int position) {
        switch (command) {
            case R.id.context_delete:
                listNotes.delete(note, position);


                return;
            case R.id.context_modify:
                NoteDialog.getInstance(note).show(
                        getSupportFragmentManager(),
                        NoteDialog.NOTE
                );

                return;
        }
    }

    @Override
    public void update(Note note) {
        listNotes.update(note);

    }

    @Override
    public void create(String title, String description, String interest, String dataPerformance) {
        Note note = new Note(title, description, interest, dataPerformance);
        listNotes.create(note);

    }


    @Override
    public void onBackPressed() {
        AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentByTag(ABOUT);

        if (aboutFragment != null) {
            getSupportFragmentManager().popBackStack();
        } else {
            showYesNoDialogFragment();
        }
    }

    private void showYesNoDialogFragment() {
        new YesNoDialog().show(getSupportFragmentManager(), null);
    }

    @Override
    public void createAnswer() {
        finish();
    }

    @Override
    public void connect() {
        listNotes.passData(note);

    }


}
