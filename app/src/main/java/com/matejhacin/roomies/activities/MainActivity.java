package com.matejhacin.roomies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.matejhacin.roomies.R;
import com.matejhacin.roomies.adapters.TasksRecyclerViewAdapter;
import com.matejhacin.roomies.interfaces.TaskCardClickListener;
import com.matejhacin.roomies.models.Task;
import com.matejhacin.roomies.models.Tasks;
import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.clients.TaskClient;
import com.matejhacin.roomies.rest.interfaces.ResponseListener;
import com.matejhacin.roomies.rest.interfaces.TasksListener;
import com.matejhacin.roomies.utils.Constants;
import com.matejhacin.roomies.utils.GeneralUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements TaskCardClickListener, SwipeRefreshLayout.OnRefreshListener {

    // region Views
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_tasks_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.main_empty_message)
    TextView emptyMessageTextView;

    @BindView(R.id.main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    // endregion

    private TaskClient taskClient = new TaskClient();
    private User user;
    private boolean isFirstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupRecyclerView();

        user = Paper.book().read(Constants.KEY_USER);
        loadTasksList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Not the nicest way to do this, but we need to refresh whenever we get back to this activity
        // In case we added or edited something
        if (!isFirstLaunch) {
            onRefresh();
        }
        isFirstLaunch = false;
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDoneClicked(Task task, int position) {
        completeTask(task);
    }

    @Override
    public void onEditClicked(Task task, int position) {
        Intent intent = TaskActivity.getIntent(MainActivity.this, task);
        startActivity(intent);
    }

    @Override
    public void onDoneAndDeleteClicked(Task task, int position) {
        completeAndRemoveTask(task);
    }

    @OnClick(R.id.main_add_task_fab)
    protected void onAddTaskClicked() {
        Intent intent = TaskActivity.getIntent(MainActivity.this, null);
        startActivity(intent);
    }

    /**
     * Loads the initial list of tasks.
     */
    private void loadTasksList() {
        final MaterialDialog loadingDialog = GeneralUtil.getLoadingDialog(this);
        loadingDialog.show();
        taskClient.getTasks(user.getRoom().getId(), new TasksListener() {
            @Override
            public void onSuccess(Tasks tasks) {
                showTasks(tasks);
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure() {
                showError();
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * Called when Swipe-to-Refresh is activated.
     */
    @Override
    public void onRefresh() {
        taskClient.getTasks(user.getRoom().getId(), new TasksListener() {
            @Override
            public void onSuccess(Tasks tasks) {
                showTasks(tasks);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                showError();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Displays a list of task cards or a message if the list is empty.
     *
     * @param tasks
     */
    private void showTasks(Tasks tasks) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (task.getAssignedUser() != null && (task.getAssignedUser().isEmpty() || task.getAssignedUser().equals(user.getId()))) {
                filteredTasks.add(task);
            }
        }

        if (filteredTasks.isEmpty()) {
            emptyMessageTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyMessageTextView.setVisibility(View.GONE);
            recyclerView.setAdapter(new TasksRecyclerViewAdapter(filteredTasks, MainActivity.this));
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Displays an error message.
     */
    private void showError() {
        recyclerView.setVisibility(View.GONE);
        emptyMessageTextView.setText(R.string.tasks_error_message);
        emptyMessageTextView.setVisibility(View.VISIBLE);
    }

    private void completeAndRemoveTask(Task task) {
        User user = Paper.book().read(Constants.KEY_USER);

        taskClient.completeAndRemoveTask(task.getId(), user.getId(), new ResponseListener() {
            @Override
            public void onSuccess() {
                onRefresh();
            }

            @Override
            public void onFailure() {
                Snackbar.make(recyclerView, R.string.unknown_error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void completeTask(Task task) {
        taskClient.completeTask(task.getId(), user.getId(), new ResponseListener() {
            @Override
            public void onSuccess() {
                onRefresh();
            }

            @Override
            public void onFailure() {
                Snackbar.make(recyclerView, R.string.unknown_error, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
