package com.matejhacin.roomies.rest.clients;

import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.RCallback;
import com.matejhacin.roomies.rest.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matejhacin on 22/11/2016.
 */

public class UserClient {

    // MARK: Public

    public void registerNewUser(String username, String password, String roomName, boolean isNewRoom, final RegistrationListener registrationListener) {
        User newUser = new User(username, password, roomName);

        Call<User> call = RestClient.getInstance().roomiesService.registerUser(newUser, isNewRoom);
        call.enqueue(new RCallback<User>() {
            @Override
            public void onSuccess(User responseObject) {
                if (registrationListener != null)
                    registrationListener.onRegistrationSuccess(responseObject);
            }

            @Override
            public void onFailure(Throwable t) {
                if (registrationListener != null)
                    registrationListener.onRegistrationFailure();
            }
        });
    }

    // MARK: Interfaces

    public interface RegistrationListener {
        void onRegistrationSuccess(User user);
        void onRegistrationFailure();
    }

}
