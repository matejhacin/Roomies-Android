package com.matejhacin.roomies.rest;

import com.matejhacin.roomies.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by matejhacin on 22/11/2016.
 */

public interface RoomiesService {

    @POST("register")
    Call<User> registerUser(@Body User user, @Query("newRoom") boolean isNewRoom);

    @POST("login")
    Call<User> loginUser(@Body User user);

}
