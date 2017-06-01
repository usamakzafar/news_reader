package com.usamakzafar.newsreader.helpers.Objects;

import org.json.JSONArray;

import java.util.Calendar;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class Comment {

    private String author;
    private int id;
    private JSONArray kids;
    private int parentID;
    private String text;
    private Calendar time;
    private String type;

    public Comment(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONArray getKids() {
        return kids;
    }

    public void setKids(JSONArray kids) {
        this.kids = kids;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
