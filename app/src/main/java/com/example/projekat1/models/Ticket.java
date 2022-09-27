package com.example.projekat1.models;

import com.example.projekat1.R;

import java.io.Serializable;
import java.util.Random;

public class Ticket implements Serializable {
    private int id;
    private Type type;
    private Priority priority;
    private int est;
    private String title;
    private String description;
    private String picture;
    private Stage stage;
    private int vector_image;

    public Ticket(int id, Type type, Priority priority, int est, String title, String description) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.est = est;
        this.title = title;
        this.description = description;
        this.picture = "https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg";
        this.stage = Stage.TODO;
        if(type.equals(Type.BUG))
            vector_image = R.drawable.ic_baseline_bug_report_24;
        else
            vector_image = R.drawable.ic_baseline_airplane_ticket_24;
    }

    public int getVector_image() {
        return vector_image;
    }

    public void setVector_image(int vector_image) {
        this.vector_image = vector_image;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
