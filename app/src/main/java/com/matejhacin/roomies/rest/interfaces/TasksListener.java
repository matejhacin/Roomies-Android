package com.matejhacin.roomies.rest.interfaces;

import com.matejhacin.roomies.models.Tasks;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */

public interface TasksListener extends FailureListener {
    void onSuccess(Tasks tasks);
}
