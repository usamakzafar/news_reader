package com.usamakzafar.newsreader.models;

import org.json.JSONArray;

import java.util.Calendar;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class NewsStory {

    private String author;
    private int descendants;
    private int id;
    private JSONArray kids;
    private int score;
    private Calendar time;
    private String title;
    private String url;

    public NewsStory(){

    }
    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String type;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
