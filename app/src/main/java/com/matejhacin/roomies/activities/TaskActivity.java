package com.matejhacin.roomies.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.matejhacin.roomies.R;
import com.matejhacin.roomies.models.Task;
import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.clients.TaskClient;
import com.matejhacin.roomies.rest.interfaces.ResponseListener;
import com.matejhacin.roomies.rest.interfaces.TaskListener;
import com.matejhacin.roomies.utils.Constants;
import com.matejhacin.roomies.utils.GeneralUtil;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class TaskActivity extends AppCompatActivity implements TaskListener {

    private static final String EXTRA_TASK = "taskToEdit";

    @BindView(R.id.task_name_edit_text) EditText taskNameEditText;
    @BindView(R.id.task_description_edit_text) EditText taskDescriptionEditText;
    @BindView(R.id.task_award_points_edit_text) EditText taskAwardPointsEditText;
    @BindView(R.id.done_button) Button doneButton;

    private MaterialDialog loadingDialog;
    private TaskClient taskClient = new TaskClient();
    private Task taskToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ButterKnife.bind(this);

        prepareForEditIfNecessary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(taskToEdit != null) {
            getMenuInflater().inflate(R.menu.menu_task, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_task_delete){
            new MaterialDialog.Builder(this)
                    .title("Confirm")
                    .content("Do you really want to delete this task?")
                    .positiveText("Yes")
                    .negativeText("No")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            deleteTask();
                        }
                    })
                    .show();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTask(){
        new TaskClient().deleteTask(taskToEdit.getId(), new ResponseListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure() {
                Snackbar.make(doneButton, R.string.unknown_error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    // TaskListener

    @Override
    public void onSuccess(Task tasks) {
        dismissLoadingDialog();
        finish();
    }

    @Override
    public void onFailure() {
        dismissLoadingDialog();
        Snackbar.make(doneButton, R.string.unknown_error, Snackbar.LENGTH_LONG).show();
    }

    // Private

    private boolean areAllFieldsFull() {
        return !GeneralUtil.isEmpty(taskNameEditText)
                && !GeneralUtil.isEmpty(taskDescriptionEditText)
                && !GeneralUtil.isEmpty(taskAwardPointsEditText);
    }

    private void showLoadingDialog() {
        loadingDialog = GeneralUtil.getLoadingDialog(this);
        loadingDialog.show();
    }

    private void dismissLoadingDialog() {
        loadingDialog.dismiss();
        loadingDialog = null;
    }

    private Task getExtraTask() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (Task) extras.get(EXTRA_TASK);
        }
        return null;
    }

    private void prepareForEditIfNecessary() {
        taskToEdit = getExtraTask();
        if (taskToEdit == null) return;

        taskNameEditText.setText(taskToEdit.getTitle());
        taskDescriptionEditText.setText(taskToEdit.getDescription());
        taskAwardPointsEditText.setText(String.valueOf(taskToEdit.getValue()));
    }

    private boolean isEditingTask() {
        return taskToEdit != null;
    }

    private void performTaskAction() {
        final String taskName = taskNameEditText.getText().toString();
        final String additionalDescription = taskDescriptionEditText.getText().toString();
        final int awardPoints = Integer.valueOf(taskAwardPointsEditText.getText().toString());
        final User user = Paper.book().read(Constants.KEY_USER);

        if (isEditingTask()) {
            taskClient.updateTask(
                    taskToEdit.getId(),
                    taskName,
                    additionalDescription,
                    awardPoints,
                    this);
        } else {
            taskClient.createTask(
                    taskName,
                    additionalDescription,
                    awardPoints,
                    user.getRoom().getId(),
                    this
            );
        }
    }

    // Public

    @OnClick(R.id.done_button)
    protected void onDoneClicked() {
        if (!areAllFieldsFull()) {
            Snackbar.make(doneButton, R.string.error_missing_data, Snackbar.LENGTH_LONG).show();
            return;
        }

        performTaskAction();
        showLoadingDialog();
    }

    /**
     * Returns an intent with required extra for this Activity.
     * @param activity Calling activity.
     * @param taskToEdit Task that should be edited. Should be null when creating a new task.
     * @return Intent.
     */
    public static Intent getIntent(Activity activity, @Nullable Task taskToEdit) {
        Intent intent = new Intent(activity, TaskActivity.class);
        intent.putExtra(EXTRA_TASK, taskToEdit);
        return intent;
    }
}
