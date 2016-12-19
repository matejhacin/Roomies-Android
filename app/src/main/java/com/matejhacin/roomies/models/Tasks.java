package com.matejhacin.roomies.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */
public class Tasks {
    @Expose
    List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }
}
