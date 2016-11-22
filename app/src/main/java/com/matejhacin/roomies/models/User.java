package com.matejhacin.roomies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matejhacin on 22/11/2016.
 */

public class User {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("token")
    @Expose
    private String token;

    public User(String username, String password, String roomName) {
        this.username = username;
        this.password = password;
        this.roomName = roomName;
    }

    // MARK: Public

    public String getToken() {
        return token;
    }
}
