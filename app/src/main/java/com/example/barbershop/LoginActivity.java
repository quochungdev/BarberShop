package com.example.barbershop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.DatabaseHelper;
//import com.example.barbershop.Module.RoleDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText txtUsername;
    TextInputEditText txtPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        AccountDataSource accountDataSource = new AccountDataSource(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheckAccount = accountDataSource.checkAccount(txtUsername.getText().toString(),txtPassword.getText().toString());
                if(!isCheckAccount){
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();

                    //Lưu thông tin của người dùng vừa nhập vào SharedPreferences để hiển thị lên giao diện của HomeFragment
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", txtUsername.getText().toString()); // Lưu tên người dùng
                    editor.apply();

                    loginSuccess();
                }
            }
        });
    }

    public void loginSuccess() {
        // Tạo Intent để chuyển đến MainActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        // Chạy Intent để chuyển đến SecondActivity
        startActivity(intent);

        // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
        finish();
    }
}