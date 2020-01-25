package com.example.productivity.notes;

import java.io.Serializable;

public class Todo extends Note implements Serializable {
    private boolean done;


    public Todo(String title, String body) {
        this(title, body, true);
        //super(title, body);
    }

    public Todo(String title, String body, boolean done) {
        super(title, body);

        this.done = done;
    }
}
