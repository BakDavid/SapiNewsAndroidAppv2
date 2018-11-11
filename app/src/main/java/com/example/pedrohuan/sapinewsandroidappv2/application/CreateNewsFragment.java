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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewsFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    View fullView;

    EditText titleInput;
    EditText shortDescriptionInput;
    EditText longDescriptionInput;
    EditText phoneNumberInput;
    EditText locationInput;

    ImageButton imageButton;

    Button createButton;

    String mtitle;
    String mshortDescription;
    String mlongDescription;
    String mphoneNumber;
    String mlocation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fullView = inflater.inflate(R.layout.fragment_create_news,container,false);

        titleInput = (EditText) fullView.findViewById(R.id.title_input);
        shortDescriptionInput = (EditText) fullView.findViewById(R.id.short_description_input);
        longDescriptionInput = (EditText) fullView.findViewById(R.id.long_description_input);
        phoneNumberInput = (EditText) fullView.findViewById(R.id.phone_number_input);
        locationInput = (EditText) fullView.findViewById(R.id.location_input);
        imageButton = (ImageButton) fullView.findViewById(R.id.image_button);
        createButton = (Button) fullView.findViewById(R.id.create_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mtitle = titleInput.getText().toString();
                mshortDescription = shortDescriptionInput.getText().toString();
                mlongDescription = longDescriptionInput.getText().toString();
                mphoneNumber = phoneNumberInput.getText().toString();
                mlocation = locationInput.getText().toString();

                DatabaseReference myRef = database.getReference("news").child("UID");

                myRef.child("Title").setValue(mtitle);
                myRef.child("ShortDescription").setValue(mshortDescription);
                myRef.child("LongDescription").setValue(mlongDescription);
                myRef.child("PhoneNumber").setValue(mphoneNumber);
                myRef.child("Location").setValue(mlocation);
                myRef.child("Created").setValue(System.currentTimeMillis());

            }
        });

        return fullView;
    }
}
