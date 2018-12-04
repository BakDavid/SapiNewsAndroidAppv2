package com.example.pedrohuan.sapinewsandroidappv2.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText editEmail;
    EditText editPassword;
    EditText editPasswordConfirmation;

    Button registerButton;
    Button loginButton;

    String mEmail;
    String mPassword;
    String mPasswordConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editEmail = (EditText) findViewById(R.id.email_input);
        editPassword = (EditText) findViewById(R.id.password_input);
        editPasswordConfirmation = (EditText) findViewById(R.id.password_confirmation_input);

        registerButton = (Button) findViewById(R.id.register_button);
        loginButton = (Button) findViewById(R.id.login_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = editEmail.getText().toString();
                mPassword = editPassword.getText().toString();
                mPasswordConfirmation = editPasswordConfirmation.getText().toString();

                registerUser(mEmail,mPassword,mPasswordConfirmation);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        });

    }

    private void registerUser(String email,String password,String passwordConfirmation)
    {
        if(validateUser(email,password,passwordConfirmation))
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);

                                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(loginIntent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                //Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }

    }

    private boolean validateUser(String email,String password,String passwordConfirmation)
    {
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if(email.trim().isEmpty())
        {
            Toast.makeText(RegisterActivity.this,"No email entered!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.trim().isEmpty())
        {
            Toast.makeText(RegisterActivity.this,"No password entered!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordConfirmation.trim().isEmpty())
        {
            Toast.makeText(RegisterActivity.this,"No password confirmation entered!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6)
        {
            Toast.makeText(RegisterActivity.this,"Password must be atleast 6 letters long!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.matches(passwordConfirmation))
        {
            Toast.makeText(RegisterActivity.this,"Password does not match confirmation password!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pattern.matcher(email).matches())
        {
            Toast.makeText(RegisterActivity.this,"Email typed wrong!",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
