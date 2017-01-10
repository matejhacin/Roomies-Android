package com.matejhacin.roomies.rest.clients;

import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.RCallback;
import com.matejhacin.roomies.rest.RestClient;
import com.matejhacin.roomies.rest.interfaces.UserListListener;
import com.matejhacin.roomies.rest.interfaces.UsersListener;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by matejhacin on 10/01/2017.
 */

public class StatisticsClient {

    public void getStatistics(String roomId, final UserListListener usersListener) {
        Call<ArrayList<User>> statisticsCall = RestClient.getInstance().roomiesService.getStatistics(roomId);
        statisticsCall.enqueue(new RCallback<ArrayList<User>>() {
            @Override
            public void onSuccess(ArrayList<User> responseObject) {
                if (usersListener != null)
                    usersListener.onSuccess(responseObject);
            }

            @Override
            public void onFailure(Throwable t) {
                if (usersListener != null)
                    usersListener.onFailure();
            }
        });
    }
}
