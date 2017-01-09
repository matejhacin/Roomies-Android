package com.matejhacin.roomies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matejhacin on 09/01/2017.
 */

public class Effort {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("roomId")
    @Expose
    private String roomId;
    @SerializedName("taskId")
    @Expose
    private String taskId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;

}
