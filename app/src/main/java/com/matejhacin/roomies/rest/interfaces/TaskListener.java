package com.matejhacin.roomies.rest.interfaces;

import com.matejhacin.roomies.models.Task;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */

public interface TaskListener extends FailureListener {
    void onSuccess(Task tasks);
}
