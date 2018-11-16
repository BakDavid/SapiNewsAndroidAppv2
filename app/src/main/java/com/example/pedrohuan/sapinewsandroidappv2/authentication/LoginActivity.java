package com.example.pedrohuan.sapinewsandroidappv2.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.example.pedrohuan.sapinewsandroidappv2.application.ListNewsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    public static String phoneNumberPrefix = "+4";
    public EditText phoneText;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        phoneText = (EditText) findViewById(R.id.editText_phone_number);

//        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mEmail = editEmail.getText().toString();
//                mPassword = editPassword.getText().toString();
//
//                loginToApp(mEmail,mPassword);
//            }
//        });

//        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(registerIntent);
//                finish();
//            }
//        });
    }


//    private void loginToApp(String email,String password)
//    {
//        if(validateData(email,password))
//        {
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                //FirebaseUser user = mAuth.getCurrentUser();
//                                //updateUI(user);
//
//                                Intent listIntent = new Intent(LoginActivity.this,ListNewsActivity.class);
//                                startActivity(listIntent);
//                                finish();
//
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                //updateUI(null);
//                            }
//
//                            // ...
//                        }
//                    });
//        }
//
//
//    }
//
//    private boolean validateData(String email, String password)
//    {
//        Pattern pattern = Patterns.EMAIL_ADDRESS;
//
//        if(email.trim().isEmpty())
//        {
//            Toast.makeText(LoginActivity.this,"No email entered!",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(password.trim().isEmpty())
//        {
//            Toast.makeText(LoginActivity.this,"No password entered!",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(!pattern.matcher(email).matches())
//        {
//            Toast.makeText(LoginActivity.this,"Email typed wrong!",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        return true;
//    }

}
