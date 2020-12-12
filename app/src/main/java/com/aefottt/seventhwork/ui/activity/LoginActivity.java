package com.aefottt.seventhwork.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.aefottt.seventhwork.R;
import com.aefottt.seventhwork.util.HttpCallbackListener;
import com.aefottt.seventhwork.util.HttpUtil;
import com.aefottt.seventhwork.util.StatusBarColorUtil;
import com.aefottt.seventhwork.util.Utility;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginAccount;
    private EditText etLoginPassword;
    private Button btnLogin;
    private TextView tvNoAccount;
    private ProgressDialog progressDialog;

    private static final int LOGIN_HANDLE = 1;

    private final Handler loginHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (LOGIN_HANDLE == msg.what) {
                boolean result = Utility.handleLoginResponse(msg.obj.toString());
                closeProgressDialog();
                if (result) {
                    Toast.makeText(getApplicationContext(), "登录成功，欢迎回来", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarColorUtil.setStatusBarColor(this);
        setContentView(R.layout.activity_login);
        initView();
        btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etLoginAccount.getText()) || TextUtils.isEmpty(etLoginPassword.getText())){
                Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
            }else{
                showProgressDialog();
                loginCheck();
            }
        });
        tvNoAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginCheck() {
        String account = etLoginAccount.getText().toString();
        String password = etLoginPassword.getText().toString();
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("username", account);
        loginMap.put("password", password);
        String loginAddress = "https://www.wanandroid.com/user/login";
        HttpUtil.sendHttpPostRequest(loginAddress, loginMap, new HttpCallbackListener() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = LOGIN_HANDLE;
                message.obj = response;
                loginHandler.sendMessage(message);
            }

            @Override
            public void onFailure(Exception e) {
                closeProgressDialog();
                Toast.makeText(getApplicationContext(), "登录请求发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        etLoginAccount = findViewById(R.id.et_login_account);
        etLoginPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvNoAccount = findViewById(R.id.tv_login_no_account);
    }
    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("登录中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
