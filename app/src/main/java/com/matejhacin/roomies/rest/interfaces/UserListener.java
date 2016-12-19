package com.matejhacin.roomies.rest.interfaces;

import com.matejhacin.roomies.models.User;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */

public interface UserListener extends FailureListener {
    void onSuccess(User user);
}
