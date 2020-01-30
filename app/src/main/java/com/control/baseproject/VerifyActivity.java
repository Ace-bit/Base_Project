package com.control.baseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText name, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        name = findViewById(R.id.verify_yourname);
        phone = findViewById(R.id.verify_phone);
        
        findViewById(R.id.verify_button).setOnClickListener(this);
        findViewById(R.id.verify_finish).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void sendVerificationEmail() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        if (task.isSuccessful()) {
                            findViewById(R.id.verify_finish).setEnabled(false);
                            Toast.makeText(VerifyActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Re-enable button
                            findViewById(R.id.verify_button).setEnabled(true);
                            Toast.makeText(VerifyActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
    }

    private boolean emptyBox(){
        if(TextUtils.isEmpty(name.getText().toString().trim())){
            name.setError("Tidak boleh tidak ada!");
            return false;
        }else if(TextUtils.isEmpty(phone.getText().toString().trim())){
            phone.setError("Tidak boleh tidak ada!");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.verify_button){
            if(emptyBox()) {
                sendVerificationEmail();
            }
        }else if(i == R.id.verify_finish){

        }
    }
}
