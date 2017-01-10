package com.matejhacin.roomies.rest.interfaces;

import com.matejhacin.roomies.models.User;

import java.util.ArrayList;

public interface UsersListener extends FailureListener {
    void onSuccess(ArrayList<User> users);
}
