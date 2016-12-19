package com.matejhacin.roomies.rest.clients;

import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.RCallback;
import com.matejhacin.roomies.rest.RestClient;
import com.matejhacin.roomies.rest.interfaces.UserListener;

import retrofit2.Call;

/**
 * Created by matejhacin on 22/11/2016.
 */

public class UserClient {

    // MARK: Public

    public void registerNewUser(String username, String password, String roomName, boolean isNewRoom, final UserListener userListener) {
        User newUser = new User(username, password, roomName);

        Call<User> call = RestClient.getInstance().roomiesService.registerUser(newUser, isNewRoom);
        call.enqueue(new RCallback<User>() {
            @Override
            public void onSuccess(User responseObject) {
                if (userListener != null)
                    userListener.onSuccess(responseObject);
            }

            @Override
            public void onFailure(Throwable t) {
                if (userListener != null)
                    userListener.onFailure();
            }
        });
    }

    public void loginUser(String username, String password, String roomName, final UserListener userListener) {
        User newUser = new User(username, password, roomName);

        Call<User> call = RestClient.getInstance().roomiesService.loginUser(newUser);
        call.enqueue(new RCallback<User>() {
            @Override
            public void onSuccess(User responseObject) {
                if (userListener != null)
                    userListener.onSuccess(responseObject);
            }

            @Override
            public void onFailure(Throwable t) {
                if (userListener != null)
                    userListener.onFailure();
            }
        });
    }
}
