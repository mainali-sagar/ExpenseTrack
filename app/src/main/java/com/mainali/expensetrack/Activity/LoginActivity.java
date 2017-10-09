package com.mainali.expensetrack.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mainali.expensetrack.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button btnSignup;
    private Button Loginbtn;
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        Loginbtn = (Button) findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(this);
        Loginbtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


        // To remove keyboard pop up on app launch

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLogin:

                //Todo: Add validiation
                Intent intent1 = new Intent(LoginActivity.this, NavigationActivity.class);
                startActivity(intent1);
                break;


            case R.id.btnSignup:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
        }


    }

    public void emailLogin() {

        mEmail = (EditText) findViewById(R.id.editText_login_email);
        mPassword = (EditText) findViewById(R.id.editText_login_password);
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.e("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (task.isSuccessful()) {


                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                            startActivity(intent);

                            // Todo: remove the toast later

                            Toast.makeText(LoginActivity.this, "Pass", Toast.LENGTH_SHORT).show();


                        } else {
                            Log.e("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Incorrect email and password, please try again",
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                });

    }

}
