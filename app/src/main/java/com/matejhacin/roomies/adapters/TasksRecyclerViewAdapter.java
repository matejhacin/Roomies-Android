package com.matejhacin.roomies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.matejhacin.roomies.R;
import com.matejhacin.roomies.interfaces.TaskCardClickListener;
import com.matejhacin.roomies.models.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Domen Lanišnik on 19. 12. 2016.
 */
public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private List<Task> taskList;
    private TaskCardClickListener taskCardClickListener;

    public TasksRecyclerViewAdapter(List<Task> taskList, TaskCardClickListener taskCardClickListener) {
        this.taskList = taskList;
        this.taskCardClickListener = taskCardClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Task task = taskList.get(position);
        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());
        holder.awardPointTextView.setText(String.valueOf(task.getValue()));
        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskCardClickListener != null) {
                    taskCardClickListener.onDoneClicked(task.getId(), holder.getAdapterPosition());
                }
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskCardClickListener != null) {
                    taskCardClickListener.onEditClicked(task.getId(), holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_task_title)
        TextView titleTextView;

        @BindView(R.id.card_task_description)
        TextView descriptionTextView;

        @BindView(R.id.card_task_award_points)
        TextView awardPointTextView;

        @BindView(R.id.card_task_done_button)
        Button doneButton;

        @BindView(R.id.card_task_edit_button)
        Button editButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
