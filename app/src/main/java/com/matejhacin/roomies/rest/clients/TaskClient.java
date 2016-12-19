package com.matejhacin.roomies.rest.clients;

import com.matejhacin.roomies.models.Tasks;
import com.matejhacin.roomies.rest.RestClient;
import com.matejhacin.roomies.rest.interfaces.TasksListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */

public class TaskClient {

    public void getTasks(String roomId, final TasksListener tasksListener) {
        Call<Tasks> tasksCall = RestClient.getInstance().roomiesService.getTasks(roomId);
        tasksCall.enqueue(new Callback<Tasks>() {
            @Override
            public void onResponse(Call<Tasks> call, Response<Tasks> response) {
                if (response != null && response.body() != null && tasksListener != null) {
                    tasksListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Tasks> call, Throwable t) {
                if (tasksListener != null) {
                    tasksListener.onFailure();
                }
            }
        });
    }
}
