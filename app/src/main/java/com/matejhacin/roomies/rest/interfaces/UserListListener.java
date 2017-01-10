package com.matejhacin.roomies.rest.interfaces;

import com.matejhacin.roomies.models.User;

import java.util.ArrayList;

/**
 * Created by matejhacin on 11/01/2017.
 */

public interface UserListListener extends FailureListener {
    void onSuccess(ArrayList<User> users);
}
