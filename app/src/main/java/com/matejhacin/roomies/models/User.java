package com.matejhacin.roomies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matejhacin on 22/11/2016.
 */

public class User {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("room")
    @Expose
    private Room room;
    @SerializedName("token")
    @Expose
    private String accessToken;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("tasksNumber")
    @Expose
    private Integer tasksNumber;

    public User(String username, String password, String roomName) {
        this(username, password, new Room(roomName));
    }

    private User(String username, String password, Room room) {
        this.username = username;
        this.password = password;
        this.room = room;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Room getRoom() {
        return room;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getTasksNumber() {
        return tasksNumber;
    }
}
