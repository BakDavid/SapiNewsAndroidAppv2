package com.example.pedrohuan.sapinewsandroidappv2.application;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("users").child("UID");

    View fullView;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText phoneNumberInput;
    EditText addressInput;

    Button logoutButton;
    Button updateButton;

    ImageView profileImageInput;


    String mfirstName;
    String mlastName;
    String memail;
    String mphoneNumber;
    String maddress;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fullView = inflater.inflate(R.layout.fragment_profile,container,false);

        firstNameInput = (EditText) fullView.findViewById(R.id.first_name_input);
        lastNameInput = (EditText) fullView.findViewById(R.id.last_name_input);
        emailInput = (EditText) fullView.findViewById(R.id.email_input);
        phoneNumberInput = (EditText) fullView.findViewById(R.id.phone_number_input);
        addressInput = (EditText) fullView.findViewById(R.id.address_input);

        logoutButton = (Button) fullView.findViewById(R.id.logout_button);
        updateButton = (Button) fullView.findViewById(R.id.update_button);

        profileImageInput = (ImageView) fullView.findViewById(R.id.profile_image_button);

        //Get data from database, to prefill some of the EditTexts if there are data to be shown




        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mfirstName = firstNameInput.getText().toString();
                mlastName = lastNameInput.getText().toString();
                memail = emailInput.getText().toString();
                mphoneNumber = phoneNumberInput.getText().toString();
                maddress = addressInput.getText().toString();

                myRef.child("First Name").setValue(mfirstName);
                myRef.child("Last Name").setValue(mlastName);
                myRef.child("Email").setValue(memail);
                myRef.child("Phone Number").setValue(mphoneNumber);
                myRef.child("Address").setValue(maddress);
                myRef.child("Updated").setValue(System.currentTimeMillis());

            }
        });

        profileImageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return fullView;
    }
}
