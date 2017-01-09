package com.matejhacin.roomies.interfaces;

import com.matejhacin.roomies.models.Task;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */
public interface TaskCardClickListener {
    void onDoneClicked(Task task, int position);

    void onEditClicked(Task task, int position);
}
