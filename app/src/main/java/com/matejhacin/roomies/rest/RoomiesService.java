package com.matejhacin.roomies.rest;

import com.matejhacin.roomies.models.Effort;
import com.matejhacin.roomies.models.Task;
import com.matejhacin.roomies.models.Tasks;
import com.matejhacin.roomies.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by matejhacin on 22/11/2016.
 */
public interface RoomiesService {

    @POST("register")
    Call<User> registerUser(@Body User user, @Query("newRoom") boolean isNewRoom);

    @POST("login")
    Call<User> loginUser(@Body User user);

    @GET("tasks/{roomId}")
    Call<Tasks> getTasks(@Path("roomId") String roomId);

    @POST("task/add")
    Call<Task> createTask(@Body Task task);

    @PUT("task/{taskId}")
    Call<Void> updateTask(@Path("taskId") String taskId, @Body Task task);

    @DELETE("task/{taskId}")
    Call<Void> deleteTask(@Path("taskId") String taskId);

    @PUT("task/{taskId}/complete/{userId}")
    Call<Effort> completeAndRemoveTask(@Path("taskId") String taskId, @Path("userId") String userId);

    @PUT("task/{taskId}/accomplish/{userId}")
    Call<Effort> completeTask(@Path("taskId") String taskId, @Path("userId") String userId);

    @GET("getUsers/{roomId}")
    Call<List<User>> getUsers(@Path("roomId") String roomId);
}
