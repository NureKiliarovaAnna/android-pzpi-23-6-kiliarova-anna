package com.example.android_pzpi_23_6_kiliarova_anna_lab_task4;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String description;
    private int priority; // 1 - Low, 2 - Medium, 3 - High
    private String dateTime;
    private String imagePath;

    public Note(String title, String description, int priority, String dateTime, String imagePath) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dateTime = dateTime;
        this.imagePath = imagePath;
    }

    // Getters and setters
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}