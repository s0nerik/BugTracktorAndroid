package com.github.sonerik.bugtracktor.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.models.Token;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.utils.Rx;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lombok.val;

/**
 * Created by sonerik on 5/28/16.
 */
public class LoginActivity extends BaseActivity {
    public static final int REQUEST_LOGIN = 1865;
    public static final String EXTRA_TOKEN = "token";

    @BindView(R.id.email)
    AppCompatEditText email;
    @BindView(R.id.emailInputLayout)
    TextInputLayout emailInputLayout;
    @BindView(R.id.password)
    AppCompatEditText password;
    @BindView(R.id.passwordInputLayout)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.loginForm)
    LinearLayout loginForm;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Inject
    BugTracktorApi api;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        sub.add(
                api.login(email.getText().toString(), password.getText().toString())
                   .compose(Rx.applySchedulers())
                   .subscribe(this::onLoggedIn, this::onLogInError)
        );
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
