package com.matejhacin.roomies.rest.interfaces;

import com.matejhacin.roomies.models.User;

import java.util.List;

/**
 * Created by Domen Lanišnik on 10/01/2017.
 * domen.lanisnik@gmail.com
 */
public interface UsersListener extends FailureListener {
    void onSuccess(List<User> users);
}
