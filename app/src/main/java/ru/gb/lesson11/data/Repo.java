package ru.gb.lesson11.data;

import java.util.List;

public interface Repo {
    // CRUD
    // ------
    // Create
    // Read
    // Update
    // Delete
    int  create(Note note);
    Note read(int id);
    void update(Note note);
    void delete(int id);

    List<Note> getAll();
}
