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

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegisterAccount;
    private EditText etRegisterPassword;
    private EditText etRegisterRepassword;
    private Button btnRegister;
    private ProgressDialog progressDialog;

    private static final int REGISTER_HANDLE = 2;

    private final Handler registerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (REGISTER_HANDLE == msg.what){
                boolean result = Utility.handleRegisterResponse(msg.obj.toString());
                if (result){
                    closeProgressDialog();
                    Toast.makeText(getApplicationContext(), "注册成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "注册失败",Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_register);
        initView();
        btnRegister.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etRegisterAccount.getText()) || TextUtils.isEmpty(etRegisterPassword.getText()) ||TextUtils.isEmpty(etRegisterRepassword.getText())){
                Toast.makeText(getApplicationContext(), "输入不能为空",Toast.LENGTH_SHORT).show();
            }else if (!etRegisterPassword.getText().toString().equals(etRegisterRepassword.getText().toString())){
                Toast.makeText(getApplicationContext(), "两次密码输入不同",Toast.LENGTH_SHORT).show();
            }else {
                showProgressDialog();
                goRegister();
            }
        });
    }

    private void goRegister() {
        String account = etRegisterAccount.getText().toString();
        String password = etRegisterPassword.getText().toString();
        String repassword = etRegisterRepassword.getText().toString();
        Map<String, String> registerMap = new HashMap<>();
        registerMap.put("username", account);
        registerMap.put("password", password);
        registerMap.put("repassword", repassword);
        HttpUtil.sendHttpPostRequest("https://www.wanandroid.com/user/register", registerMap, new HttpCallbackListener() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = REGISTER_HANDLE;
                message.obj = response;
                registerHandler.sendMessage(message);
            }
            @Override
            public void onFailure(Exception e) {
                closeProgressDialog();
                Toast.makeText(getApplicationContext(), "注册请求发送失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        etRegisterAccount = findViewById(R.id.et_register_account);
        etRegisterPassword = findViewById(R.id.et_register_password);
        etRegisterRepassword = findViewById(R.id.et_register_repassword);
        btnRegister = findViewById(R.id.btn_register);
    }
    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("注册中...");
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
