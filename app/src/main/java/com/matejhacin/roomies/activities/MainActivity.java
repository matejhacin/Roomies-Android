package com.matejhacin.roomies.activities;

import android.os.Bundle;
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
import com.matejhacin.roomies.models.Tasks;
import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.clients.TaskClient;
import com.matejhacin.roomies.rest.interfaces.TasksListener;
import com.matejhacin.roomies.utils.Constants;
import com.matejhacin.roomies.utils.GeneralUtil;

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

    private User user;
    private Tasks tasks;

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

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDoneClicked(String taskId, int position) {
        // TODO Task done
    }

    @Override
    public void onEditClicked(String taskId, int position) {
        // TODO Task edit
    }

    @OnClick(R.id.main_add_task_fab)
    protected void onAddTaskClicked() {
        // TODO Task add
    }

    /**
     * Loads the initial list of tasks.
     */
    private void loadTasksList() {
        final MaterialDialog loadingDialog = GeneralUtil.getLoadingDialog(this);
        loadingDialog.show();
        new TaskClient().getTasks(user.getRoom().getId(), new TasksListener() {
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
        new TaskClient().getTasks(user.getRoom().getId(), new TasksListener() {
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
        MainActivity.this.tasks = tasks;
        if (tasks.getTasks().isEmpty()) {
            emptyMessageTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyMessageTextView.setVisibility(View.GONE);
            recyclerView.setAdapter(new TasksRecyclerViewAdapter(tasks.getTasks(), MainActivity.this));
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
}
