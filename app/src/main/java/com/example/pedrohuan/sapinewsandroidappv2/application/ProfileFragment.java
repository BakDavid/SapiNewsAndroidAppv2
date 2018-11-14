package com.example.pedrohuan.sapinewsandroidappv2.application;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.example.pedrohuan.sapinewsandroidappv2.application.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    String userUID = "UID";

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("users").child(userUID);

    private StorageReference mStorageRef;

    private static final int PICK_IMAGE_REQUEST = 1;

    User muser;

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

    Uri mImageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fullView = inflater.inflate(R.layout.fragment_profile,container,false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        firstNameInput = (EditText) fullView.findViewById(R.id.first_name_input);
        lastNameInput = (EditText) fullView.findViewById(R.id.last_name_input);
        emailInput = (EditText) fullView.findViewById(R.id.email_input);
        phoneNumberInput = (EditText) fullView.findViewById(R.id.phone_number_input);
        addressInput = (EditText) fullView.findViewById(R.id.address_input);

        logoutButton = (Button) fullView.findViewById(R.id.logout_button);
        updateButton = (Button) fullView.findViewById(R.id.update_button);

        profileImageInput = (ImageView) fullView.findViewById(R.id.profile_image_button);

        //Get data from database, to prefill some of the EditTexts if there are data to be shown
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                muser = dataSnapshot.getValue(User.class);
                firstNameInput.setText(muser.getFirstName());
                lastNameInput.setText(muser.getLastName());
                emailInput.setText(muser.getEmail());
                phoneNumberInput.setText(muser.getPhoneNumber());
                addressInput.setText(muser.getAddress());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getContext(), "Database read failed!", Toast.LENGTH_SHORT).show();
            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), muser.getLastName() + " " + muser.getFirstName() + " " + muser.getPhoneNumber(),Toast.LENGTH_LONG).show();
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

                String childRoute = "images/" + userUID + "/profile/" + System.currentTimeMillis();

                myRef.child("First Name").setValue(mfirstName);
                myRef.child("Last Name").setValue(mlastName);
                myRef.child("Email").setValue(memail);
                myRef.child("Phone Number").setValue(mphoneNumber);
                myRef.child("Address").setValue(maddress);
                myRef.child("Updated").setValue(System.currentTimeMillis());
                myRef.child("Image").setValue(childRoute);

                StorageReference riversRef = mStorageRef.child(childRoute);

                riversRef.putFile(mImageUri);
            }
        });

        profileImageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        return fullView;
    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

            profileImageInput.setImageURI(mImageUri);
        }
    }
}
