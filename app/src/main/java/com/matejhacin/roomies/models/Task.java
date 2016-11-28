package com.matejhacin.roomies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matejhacin on 22/11/2016.
 */

public class Task {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("taskName")
    @Expose
    private String title;
    @SerializedName("additionalDescription")
    @Expose
    private String description;
    @SerializedName("awardPoints")
    @Expose
    private Integer value;

    public Task(String title, String description, Integer value) {
        this.title = title;
        this.description = description;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getValue() {
        return value;
    }
}