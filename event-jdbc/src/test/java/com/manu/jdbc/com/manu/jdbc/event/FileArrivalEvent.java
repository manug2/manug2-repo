package com.manu.jdbc.com.manu.jdbc.event;

import java.util.Date;

/**
 * Created by ManuGarg on 19/9/14.
 */
public class FileArrivalEvent {
    private int id;
    private Date timestamp;
    private String name;


    public FileArrivalEvent(int id, Date timestamp, String name) {
        this.id = id;
        this.timestamp = timestamp;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }
}
