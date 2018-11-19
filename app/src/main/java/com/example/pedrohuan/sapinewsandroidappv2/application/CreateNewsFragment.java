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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;

public class CreateNewsFragment extends Fragment {

    String userUID = "UID";

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private StorageReference mStorageRef;

    View fullView;

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText titleInput;
    EditText shortDescriptionInput;
    EditText longDescriptionInput;
    EditText phoneNumberInput;
    EditText locationInput;

    ImageView imageView;

    Button createButton;
    Button uploadImageButton;

    String mtitle;
    String mshortDescription;
    String mlongDescription;
    String mphoneNumber;
    String mlocation;

    boolean imageUploaded = false;

    Uri mImageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fullView = inflater.inflate(R.layout.fragment_create_news,container,false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        titleInput = (EditText) fullView.findViewById(R.id.title_input);
        shortDescriptionInput = (EditText) fullView.findViewById(R.id.short_description_input);
        longDescriptionInput = (EditText) fullView.findViewById(R.id.long_description_input);
        phoneNumberInput = (EditText) fullView.findViewById(R.id.phone_number_input);
        locationInput = (EditText) fullView.findViewById(R.id.location_input);
        imageView = (ImageView) fullView.findViewById(R.id.image_view);
        createButton = (Button) fullView.findViewById(R.id.create_button);
        uploadImageButton = (Button) fullView.findViewById(R.id.image_upload_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mtitle = titleInput.getText().toString();
                mshortDescription = shortDescriptionInput.getText().toString();
                mlongDescription = longDescriptionInput.getText().toString();
                mphoneNumber = phoneNumberInput.getText().toString();
                mlocation = locationInput.getText().toString();

                if(validateInputs())
                {
                    final DatabaseReference myRef = database.getReference("news").child(userUID);
                    DatabaseReference myRefUser = database.getReference("users").child(userUID);

                    myRefUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String mfirstName = dataSnapshot.child("FirstName").getValue().toString();
                            String mlastName = dataSnapshot.child("LastName").getValue().toString();

                            String fullName = mfirstName + " " + mlastName;

                            myRef.child("FullName").setValue(fullName);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Failed to get users full name!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    String childRoute = "images/" + userUID + "/news/" + System.currentTimeMillis() + ".jpg";

                    myRef.child("Title").setValue(mtitle);
                    myRef.child("ShortDescription").setValue(mshortDescription);
                    myRef.child("LongDescription").setValue(mlongDescription);
                    myRef.child("PhoneNumber").setValue(mphoneNumber);
                    myRef.child("Location").setValue(mlocation);
                    myRef.child("Clicks").setValue(0);
                    myRef.child("Alert").setValue(0);
                    myRef.child("Created").setValue(System.currentTimeMillis());
                    myRef.child("CreatedUser").setValue(userUID);
                    myRef.child("Image").setValue(childRoute);

                    StorageReference riversRef = mStorageRef.child(childRoute);

                    riversRef.putFile(mImageUri);

                    clearInputs();
                }
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
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

            imageView.setImageURI(mImageUri);

            imageUploaded = true;
        }
    }

    private boolean validateInputs()
    {
        String phonePattern = "[0-9]+";

        if(mtitle.isEmpty())
        {
            Toast.makeText(getContext(), "Title can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mshortDescription.isEmpty())
        {
            Toast.makeText(getContext(), "Short description can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mlongDescription.isEmpty())
        {
            Toast.makeText(getContext(), "Long description can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mlocation.isEmpty())
        {
            Toast.makeText(getContext(), "Location can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mphoneNumber.isEmpty())
        {
            Toast.makeText(getContext(), "Phone number can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!mphoneNumber.matches(phonePattern))
        {
            Toast.makeText(getContext(), "Phone number must only containt numbers!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!imageUploaded)
        {
            Toast.makeText(getContext(), "You must upload an image!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearInputs()
    {
        titleInput.setText("");
        shortDescriptionInput.setText("");
        longDescriptionInput.setText("");
        phoneNumberInput.setText("");
        locationInput.setText("");

        imageUploaded = false;
        imageView.setImageURI(null);

        Toast.makeText(getContext(), "New uploaded!", Toast.LENGTH_SHORT).show();
    }
}