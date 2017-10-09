package com.mainali.expensetrack.Model;

/**
 * Created by sagarmainali on 8/10/17.
 */

public class Reminder {

    public String id;
    public String title;
    public String date;
    public String time;
    public String desc;

    public Reminder(String id, String title, String date, String time, String desc) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }
}
