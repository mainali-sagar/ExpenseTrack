package com.mainali.expensetrack.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mainali.expensetrack.R;
import com.mainali.expensetrack.TestActivity;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText mName;
    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmpassword;
    private Button Signupbtn;
    private TextView tvSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Signupbtn = (Button) findViewById(R.id.btn_signup_signup);
        tvSignin = (TextView) findViewById(R.id.textView_signin);
        Signupbtn.setOnClickListener(this);
        tvSignin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup_signup:
                Intent intent1 = new Intent(SignupActivity.this, TestActivity.class);
                startActivity(intent1);
                //userSignup();
                break;

            case R.id.textView_signin:
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userSignup() {

        mName = (EditText) findViewById(R.id.editText_signup_name);
        mEmail = (EditText) findViewById(R.id.editText_signup_email);
        mUsername = (EditText) findViewById(R.id.editText_signup_username);
        mPassword = (EditText) findViewById(R.id.editText_signup_password);
        mConfirmpassword = (EditText) findViewById(R.id.editText_signup_confirm_password);

        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String confirmpassword = mConfirmpassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "User Registration Successfull",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignupActivity.this, "User Registration Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


}