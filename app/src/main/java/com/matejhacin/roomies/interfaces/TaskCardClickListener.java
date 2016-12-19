package com.matejhacin.roomies.interfaces;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */
public interface TaskCardClickListener {
    void onDoneClicked(String taskId, int position);

    void onEditClicked(String taskId, int position);
}
