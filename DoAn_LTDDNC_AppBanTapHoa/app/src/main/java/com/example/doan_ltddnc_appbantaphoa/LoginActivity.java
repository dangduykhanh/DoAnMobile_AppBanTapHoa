package com.example.doan_ltddnc_appbantaphoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_ltddnc_appbantaphoa.RegisterFragment.ForgotPasswordFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button btnSignUp ,btnSignIn ,btnGuest ;
    TextInputEditText edtemail ,edtpassword;
    TextView tvForgetpass ;
    public int REQUEST_CODE_LOGIN =123 ;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignUp =findViewById(R.id.btnSignUp);
        edtemail =findViewById(R.id.edtEmail);
        edtpassword =findViewById(R.id.edtPassword);
        btnSignIn =findViewById(R.id.btnSignIn);
        btnGuest =findViewById(R.id.btnGuest);
        tvForgetpass=findViewById(R.id.tvforgotPassword);
        fauth =FirebaseAuth.getInstance();
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this ,MainActivity.class));
            }
        });
        tvForgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordFragment forgotPasswordFragment =new ForgotPasswordFragment();
                forgotPasswordFragment.show(getSupportFragmentManager() ,"dialog fogot password");
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtemail.getText().equals("") || edtpassword.getText().equals("")){
                    Toast.makeText(LoginActivity.this, "Thông tin rỗng", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoadingDialog loadingDialog =new LoadingDialog(LoginActivity.this);
                    loadingDialog.ShowDialog("Đăng nhập...");
                    String email =edtemail.getText().toString();
                    String password =edtpassword.getText().toString();
                    fauth.signInWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isComplete()){
                                loadingDialog.HideDialog();
                                Toast.makeText(LoginActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this ,MainActivity.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this ,RegisterActivity.class),REQUEST_CODE_LOGIN);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==REQUEST_CODE_LOGIN && resultCode == RESULT_OK && data!=null){
            edtemail.setText(data.getStringExtra("email"));
            edtpassword.setText(data.getStringExtra("password"));
            Log.d("ABC", "onActivityResult: "+data.getStringExtra("email"));
        }
    }
}