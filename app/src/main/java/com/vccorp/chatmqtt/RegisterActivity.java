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
import com.vccorp.chatmqtt.databinding.ActivityRegisterBinding;

/**
 * Created by Linh Nguyen Thuy on 10/14/2019.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        binding.dangki.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dangki :
                String userName = binding.username.getText().toString();
                String password = binding.pasword.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(this, "Password nhập lại không đúng!", Toast.LENGTH_SHORT).show();
                    binding.confirmPassword.setText("");
                    return;
                } else {
                    mAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });
                }
                break;
            case R.id.thoat:
                finish();
                break;
        }
    }
}
