package com.control.baseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText l_email, l_pass, r_email, r_pass;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Find ID
        l_email = findViewById(R.id.login_email);
        l_pass = findViewById(R.id.login_password);
        r_email = findViewById(R.id.register_email);
        r_pass = findViewById(R.id.register_password);

        //Button Listeners
        findViewById(R.id.button_create).setOnClickListener(this);
        findViewById(R.id.button_have).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void buttonCreate() {
        findViewById(R.id.button_create).setVisibility(View.GONE);
        findViewById(R.id.linear_login).setVisibility(View.GONE);
        findViewById(R.id.button_have).setVisibility(View.VISIBLE);
        findViewById(R.id.linear_register).setVisibility(View.VISIBLE);
    }

    private void buttonHave() {
        findViewById(R.id.button_create).setVisibility(View.VISIBLE);
        findViewById(R.id.linear_login).setVisibility(View.VISIBLE);
        findViewById(R.id.button_have).setVisibility(View.GONE);
        findViewById(R.id.linear_register).setVisibility(View.GONE);
    }

    private void buttonLogin() {
        String email = l_email.getText().toString().trim();
        String password = l_pass.getText().toString().trim();
        if(emptyLogin()){
            showLoading("Login to the app.","Be patient...");
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if(mUser.isEmailVerified()){

                                }else{
                                    startActivity(new Intent(LoginActivity.this, VerifyActivity.class));
                                    finish();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            hideLoading();

                        }
                    });
        }
    }

    private void buttonRegister() {
        String email = r_email.getText().toString().trim();
        String password = r_pass.getText().toString().trim();
        if(emptyRegister()){
            showLoading("Register yours.","Be patient...");
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, VerifyActivity.class));
                                finish();
                            } else {
                                // If create fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Creation failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            hideLoading();

                        }
                    });
        }
    }

    private boolean emptyLogin() {
        if(TextUtils.isEmpty(l_email.getText().toString().trim())){
            l_email.setError("Tidak boleh tidak ada!");
            return false;
        }else if(TextUtils.isEmpty(l_pass.getText().toString().trim())){
            l_pass.setError("Tidak boleh tidak ada!");
            return false;
        }
        return true;
    }

    private boolean emptyRegister() {
        if(TextUtils.isEmpty(r_email.getText().toString().trim())){
            r_email.setError("Tidak boleh tidak ada!");
            return false;
        }else if(TextUtils.isEmpty(r_pass.getText().toString().trim())){
            r_pass.setError("Tidak boleh tidak ada!");
            return false;
        }
        return true;
    }

    private void showLoading(String...texts) {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle(texts[0]);
        mDialog.setMessage(texts[1]);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void hideLoading() {
        mDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button_create){
            buttonCreate();
        }else if(id == R.id.button_have){
            buttonHave();
        }else if(id == R.id.button_login){
            buttonLogin();
        }else if(id == R.id.button_register){
            buttonRegister();
        }
    }
}
