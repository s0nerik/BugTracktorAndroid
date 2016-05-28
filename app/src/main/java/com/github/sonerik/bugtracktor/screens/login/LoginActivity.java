package com.github.sonerik.bugtracktor.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.models.Token;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.utils.Rx;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.val;

/**
 * Created by sonerik on 5/28/16.
 */
public class LoginActivity extends BaseActivity {
    public static final int REQUEST_LOGIN = 1865;
    public static final String EXTRA_TOKEN = "token";

    @Inject
    BugTracktorApi api;

    @BindView(R.id.loginEmail)
    AppCompatEditText loginEmail;
    @BindView(R.id.loginPassword)
    AppCompatEditText loginPassword;
    @BindView(R.id.loginPasswordInputLayout)
    TextInputLayout loginPasswordInputLayout;
    @BindView(R.id.loginForm)
    LinearLayout loginForm;
    @BindView(R.id.registerEmail)
    AppCompatEditText registerEmail;
    @BindView(R.id.registerEmailInputLayout)
    TextInputLayout registerEmailInputLayout;
    @BindView(R.id.registerPassword)
    AppCompatEditText registerPassword;
    @BindView(R.id.registerPasswordInputLayout)
    TextInputLayout registerPasswordInputLayout;
    @BindView(R.id.nickname)
    AppCompatEditText nickname;
    @BindView(R.id.nicknameInputLayout)
    TextInputLayout nicknameInputLayout;
    @BindView(R.id.realName)
    AppCompatEditText realName;
    @BindView(R.id.realNameInputLayout)
    TextInputLayout realNameInputLayout;
    @BindView(R.id.registerForm)
    LinearLayout registerForm;
    @BindView(R.id.forms)
    FrameLayout forms;
    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;
    @BindView(R.id.newUserSwitch)
    SwitchCompat newUserSwitch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @OnClick(R.id.newUserSwitch)
    public void onSwitchForm() {
        loginForm.setVisibility(loginForm.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        registerForm.setVisibility(registerForm.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        btnLogin.setText(loginForm.getVisibility() == View.VISIBLE ? "Login" : "Register");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        if (loginForm.getVisibility() == View.VISIBLE) {
            sub.add(
                    api.login(loginEmail.getText().toString(), loginPassword.getText().toString())
                       .compose(Rx.applySchedulers())
                       .subscribe(this::onLoggedIn, this::onLogInError)
            );
        } else {
            val user = new User();
            user.setEmail(registerEmail.getText().toString());
            user.setPassword(registerPassword.getText().toString());
            user.setNickname(nickname.getText().toString().isEmpty() ? null : nickname.getText().toString());
            user.setRealName(realName.getText().toString().isEmpty() ? null : realName.getText().toString());
            sub.add(
                    api.register(user)
                       .concatMap(createdUser -> api.login(createdUser.getEmail(), createdUser.getPassword()))
                       .compose(Rx.applySchedulers())
                       .subscribe(this::onLoggedIn, this::onLogInError)
            );
        }
    }

    private void onLoggedIn(Token result) {
        val intent = new Intent();
        intent.putExtra(EXTRA_TOKEN, result.getToken());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void onLogInError(Throwable error) {
        Toast.makeText(this, "Wrong login credentials!", Toast.LENGTH_LONG).show();
    }
}
