package ru.gb.lesson11.data;

import java.io.Serializable;

public class Note implements Serializable {
    private Integer id;
    private String title;
    private String description;

    private String interest = "";
    private String dataPerformance;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Note(Integer id, String title, String description, String interest, String dataPerformance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.interest = interest;
        this.dataPerformance = dataPerformance;
    }

    public Note(String title, String description, String interest, String dataPerformance) {
        this.title = title;
        this.description = description;
        this.interest = interest;
        this.dataPerformance = dataPerformance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getDataPerformance() {
        return dataPerformance;
    }

    public void setDataPerformance(String dataPerformance) {
        this.dataPerformance = dataPerformance;
    }
}
