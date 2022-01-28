package ru.gb.lesson11.data;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImpl implements Repo {

    private ArrayList<Note> notes = new ArrayList<>();
    private int counter = 0;

    // singleton для базы
    private static InMemoryRepoImpl repo;

    public static Repo getInstance() {
        if (repo == null) {
            repo = new InMemoryRepoImpl();
        }
        return repo;
    }


    private InMemoryRepoImpl() {
        create(new Note("Title 1", "Description 1"));
        create(new Note("Title 2", "Description 2"));
        create(new Note("Title 3", "Description 3"));
        create(new Note("Title 4", "Description 4"));
        create(new Note("Title 5", "Description 5"));
        create(new Note("Title 6", "Description 6"));
        create(new Note("Title 7", "Description 7"));
        create(new Note("Title 8", "Description 8"));
        create(new Note("Title 9", "Description 9"));
        create(new Note("Title 10", "Description 10"));
        create(new Note("Title 11", "Description 11"));
        create(new Note("Title 12", "Description 12"));
        create(new Note("Title 13", "Description 13"));
        create(new Note("Title 14", "Description 14"));
        create(new Note("Title 15", "Description 15"));
        create(new Note("Title 16", "Description 16"));
    }




    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
//        getAll();

        return id;
    }

    @Override
    public Note read(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id)
                return notes.get(i);
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == note.getId()) {
                notes.set(i, note);
                break;
            }
        }
//        getAll();
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                notes.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notes;
    }
}
