package com.matejhacin.roomies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.matejhacin.roomies.R;
import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.clients.UserClient;
import com.matejhacin.roomies.utils.GeneralUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private boolean isRegisterMode;

    // region Views
    @BindView(R.id.login_toolbar)
    Toolbar toolbar;

    @BindView(R.id.login_username_edit_text)
    EditText usernameEditText;

    @BindView(R.id.login_password_edit_text)
    EditText passwordEditText;

    @BindView(R.id.login_room_name_edit_text)
    EditText roomNameEditText;

    @BindView(R.id.login_fab)
    FloatingActionButton registerFAButton;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.login_new_room_checkbox)
    CheckBox newRoomCheckbox;

    @BindView(R.id.login_message_text)
    TextView messageTextView;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        toolbar.setSubtitle(R.string.login);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.login_button)
    protected void onLoginClicked() {
        if (GeneralUtil.isEmpty(usernameEditText)
                || GeneralUtil.isEmpty(passwordEditText)
                || GeneralUtil.isEmpty(roomNameEditText)) {
            Snackbar.make(loginButton, R.string.error_missing_data, Snackbar.LENGTH_SHORT).show();
        } else {
            if (isRegisterMode) {
                register();
            } else {
                login();
            }
        }
    }

    @OnClick(R.id.login_fab)
    protected void onRegisterFabClicked() {
        changeMode(true);
    }

    private void register() {
        final MaterialDialog loadingDialog = GeneralUtil.getLoadingDialog(this);
        loadingDialog.show();
        new UserClient().registerNewUser(GeneralUtil.getText(usernameEditText), GeneralUtil.getText(passwordEditText), GeneralUtil.getText(roomNameEditText), newRoomCheckbox.isChecked(), new UserClient.UserListener() {
            @Override
            public void onSuccess(User user) {
                // TODO Save user
                loadingDialog.dismiss();
                onAuthorizationSuccess();
            }

            @Override
            public void onFailure() {
                loadingDialog.dismiss();
                Snackbar.make(usernameEditText, R.string.error_registration, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void login() {
        final MaterialDialog loadingDialog = GeneralUtil.getLoadingDialog(this);
        loadingDialog.show();
        new UserClient().loginUser(GeneralUtil.getText(usernameEditText), GeneralUtil.getText(passwordEditText), GeneralUtil.getText(roomNameEditText), new UserClient.UserListener() {
            @Override
            public void onSuccess(User user) {
                // TODO Save user
                loadingDialog.dismiss();
                onAuthorizationSuccess();
            }

            @Override
            public void onFailure() {
                loadingDialog.dismiss();
                Snackbar.make(usernameEditText, R.string.error_login, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void onAuthorizationSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Adjusts the visuals for login or registration.
     *
     * @param isRegisterMode
     */
    private void changeMode(boolean isRegisterMode) {
        this.isRegisterMode = isRegisterMode;
        registerFAButton.setVisibility(isRegisterMode ? View.GONE : View.VISIBLE);
        loginButton.setText(isRegisterMode ? R.string.register : R.string.login);
        newRoomCheckbox.setVisibility(isRegisterMode ? View.VISIBLE : View.GONE);
        messageTextView.setText(isRegisterMode ? R.string.register_message : R.string.login_message);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isRegisterMode);
            getSupportActionBar().setDisplayShowHomeEnabled(isRegisterMode);
            getSupportActionBar().setSubtitle(isRegisterMode ? R.string.register : R.string.login);
        }
    }

    @Override
    public void onBackPressed() {
        if (isRegisterMode) {
            changeMode(false);
        } else {
            super.onBackPressed();
        }
    }
}