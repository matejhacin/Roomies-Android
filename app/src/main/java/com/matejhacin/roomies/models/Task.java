package com.matejhacin.roomies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by matejhacin on 22/11/2016.
 */

public class Task implements Serializable {

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
    @SerializedName("room")
    @Expose
    private String roomId;
    @SerializedName("assignedUser")
    @Expose
    private String assignedUser;

    public Task(String title, String description, Integer value) {
        this.title = title;
        this.description = description;
        this.value = value;
    }

    public Task(String title, String description, Integer value, String roomId) {
        this.title = title;
        this.description = description;
        this.value = value;
        this.roomId = roomId;
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

    public String getAssignedUser() {
        return assignedUser;
    }
}
