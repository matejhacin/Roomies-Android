package com.matejhacin.roomies.rest.clients;

import com.matejhacin.roomies.models.Effort;
import com.matejhacin.roomies.models.Task;
import com.matejhacin.roomies.models.Tasks;
import com.matejhacin.roomies.rest.RCallback;
import com.matejhacin.roomies.rest.RestClient;
import com.matejhacin.roomies.rest.interfaces.ResponseListener;
import com.matejhacin.roomies.rest.interfaces.TaskListener;
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
                if (response != null && response.body() != null && response.body().getTasks() != null && tasksListener != null) {
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

    public void createTask(String taskName, String additionalDescription, int awardPoints, String room, final TaskListener taskListener) {
        Task newTask = new Task(taskName, additionalDescription, awardPoints, room);

        Call<Task> newTaskCall = RestClient.getInstance().roomiesService.createTask(newTask);
        newTaskCall.enqueue(new RCallback<Task>() {
            @Override
            public void onSuccess(Task responseObject) {
                if (taskListener != null)
                    taskListener.onSuccess(responseObject);
            }

            @Override
            public void onFailure(Throwable t) {
                if (taskListener != null)
                    taskListener.onFailure();
            }
        });
    }

    public void updateTask(String taskId, String taskName, String additionalDescription, int awardPoints, final TaskListener taskListener) {
        Task updatedTask = new Task(taskName, additionalDescription, awardPoints);

        Call<Void> updateTaskCall = RestClient.getInstance().roomiesService.updateTask(taskId, updatedTask);
        updateTaskCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (taskListener != null)
                    taskListener.onSuccess(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (taskListener != null)
                    taskListener.onFailure();
            }
        });
    }

    public void completeAndRemoveTask(String taskId, String userId, final ResponseListener responseListener) {
        Call<Effort> completeCall = RestClient.getInstance().roomiesService.completeAndRemoveTask(taskId, userId);
        completeCall.enqueue(new RCallback<Effort>() {
            @Override
            public void onSuccess(Effort responseObject) {
                if (responseListener != null)
                    responseListener.onSuccess();
            }

            @Override
            public void onFailure(Throwable t) {
                if (responseListener != null)
                    responseListener.onFailure();
            }
        });
    }

    public void completeTask(String taskId, String userId, final ResponseListener responseListener) {
        Call<Effort> completeCall = RestClient.getInstance().roomiesService.completeTask(taskId, userId);
        completeCall.enqueue(new RCallback<Effort>() {
            @Override
            public void onSuccess(Effort responseObject) {
                if (responseListener != null)
                    responseListener.onSuccess();
            }

            @Override
            public void onFailure(Throwable t) {
                if (responseListener != null)
                    responseListener.onFailure();
            }
        });
    }

}
