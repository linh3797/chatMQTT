package com.vccorp.chatmqtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vccorp.chatmqtt.databinding.ActivityLoginBinding;

/**
 * Created by Linh Nguyen Thuy on 10/14/2019.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        binding.dangki1.setOnClickListener(this);
        binding.dangnhap.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dangnhap:
                final String userName = binding.username.getText().toString();
                String password = binding.pasword.getText().toString();

                if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Không được để trống các trường!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("USERNAME", binding.username.getText().toString());
                            intent.putExtra("PASSWORD", binding.pasword.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            break;
            case R.id.dangki1:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }
}
