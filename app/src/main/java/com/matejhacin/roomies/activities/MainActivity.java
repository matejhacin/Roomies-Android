package com.matejhacin.roomies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matejhacin.roomies.R;
import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.clients.UserClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Remove, this is just for testing
        new UserClient().registerNewUser("test1", "test1", "test2", true, new UserClient.RegistrationListener() {
            @Override
            public void onRegistrationSuccess(User user) {
                int i = 0;
            }

            @Override
            public void onRegistrationFailure() {
                int i = 0;
            }
        });
    }
}
