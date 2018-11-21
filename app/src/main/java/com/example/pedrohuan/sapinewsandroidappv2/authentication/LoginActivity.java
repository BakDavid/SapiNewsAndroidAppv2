package com.example.pedrohuan.sapinewsandroidappv2.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.example.pedrohuan.sapinewsandroidappv2.application.Main2Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "PhoneAuth";

    private EditText phoneText;
    private EditText codeText;
    private Button verifyButton;
    private Button sendButton;

    public static String phoneNumberPrefix = "+4";

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        phoneText = (EditText) findViewById(R.id.editText_phone_number);
        codeText = (EditText) findViewById(R.id.codeText);
        verifyButton = (Button) findViewById(R.id.verifyButton);
        sendButton = (Button) findViewById(R.id.sendButton);

        verifyButton.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();

    }

    public void sendCode(View view) {
        String phoneNumber = phoneNumberPrefix + phoneText.getText().toString();

        verifyButton.setEnabled(true);
        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of time
                this, // Activity (for callback biding)
                verificationCallbacks);
    }

    private void setUpVerificationCallbacks() {
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        verifyButton.setEnabled(true);
                        codeText.setText("");
                        signInWithPhoneAutchCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential");
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            Log.d(TAG, "SMS Quoata exceeded.");
                        }
                    }

                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        phoneVerificationId = verificationId;
                        resendToken = token;

                        verifyButton.setEnabled(true);
                        sendButton.setEnabled(false);
                    }
                };
    }

    public void verifyCode(View view) {
        String code = codeText.getText().toString();

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAutchCredential(credential);

        Intent intent = new Intent(LoginActivity.this,Main2Activity.class);
        startActivity(intent);
        finish();
    }

    private void signInWithPhoneAutchCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            codeText.setText("");
                            verifyButton.setEnabled(true);
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
