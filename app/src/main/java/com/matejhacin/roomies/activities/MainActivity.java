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
        /*new UserClient().registerNewUser("matejtest1", "matejtest1", "matejtest1", true, new UserClient.UserListener() {
            @Override
            public void onSuccess(User user) {
                int i = 0;
            }

            @Override
            public void onFailure() {
                int i = 0;
            }
        });*/

        /*new UserClient().loginUser("matejtest1", "matejtest1", "matejtest1", new UserClient.UserListener() {
            @Override
            public void onSuccess(User user) {
                int i = 0;
            }

            @Override
            public void onFailure() {
                int i = 0;
            }
        });*/
    }
}
