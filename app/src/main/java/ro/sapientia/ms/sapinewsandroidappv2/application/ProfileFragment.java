package ro.sapientia.ms.sapinewsandroidappv2.application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pedrohuan.sapinewsandroidappv2.R;
import ro.sapientia.ms.sapinewsandroidappv2.authentication.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;

/**
 * Ez a fragment a felhasznalo adatait jeleniti meg. Lehetosege van modositani az adatait, profilkepet.
 * Ugyanitt ki is tud jelentkezni az applikaciobol.
 */
public class ProfileFragment extends Fragment {

    String userUID = FirebaseAuth.getInstance().getUid();

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("users");

    private StorageReference mStorageRef;

    private static final int PICK_IMAGE_REQUEST = 1;

    View fullView;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText addressInput;

    Button logoutButton;
    Button updateButton;

    ImageView profileImageInput;

    String mfirstName;
    String mlastName;
    String memail;
    String maddress;

    Uri mImageUri;

    boolean photoChanged = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fullView = inflater.inflate(R.layout.fragment_profile,container,false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        firstNameInput = (EditText) fullView.findViewById(R.id.first_name_input);
        lastNameInput = (EditText) fullView.findViewById(R.id.last_name_input);
        emailInput = (EditText) fullView.findViewById(R.id.email_input);
        addressInput = (EditText) fullView.findViewById(R.id.address_input);

        logoutButton = (Button) fullView.findViewById(R.id.logout_button);
        updateButton = (Button) fullView.findViewById(R.id.update_button);

        profileImageInput = (ImageView) fullView.findViewById(R.id.profile_image_button);

        /**
         * Ez a resz betolti a felhasznalo adatait az adatbazisbol.
         */
        //Get data from database, to prefill some of the EditTexts if there are data to be shown
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.hasChild(userUID))
                {
                    String dfirstName = dataSnapshot.child(userUID).child("FirstName").getValue().toString();
                    String dlastName = dataSnapshot.child(userUID).child("LastName").getValue().toString();
                    String demail = dataSnapshot.child(userUID).child("Email").getValue().toString();
                    String daddress = dataSnapshot.child(userUID).child("Address").getValue().toString();
                    if(dataSnapshot.hasChild(userUID + "/UserImage"))
                    {
                        String duserImage = dataSnapshot.child(userUID).child("UserImage").getValue().toString();

                        if(duserImage != null && !(duserImage.trim().isEmpty()))
                        {

                            mStorageRef.child(duserImage).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Successfully downloaded data to local file
                                            // ...
                                            Glide.with(getContext())
                                                    .load(uri)
                                                    .into(profileImageInput);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle failed download
                                    // ...
                                    Toast.makeText(getContext(), "Image load failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    firstNameInput.setText(dfirstName);
                    lastNameInput.setText(dlastName);
                    emailInput.setText(demail);
                    //phoneNumberInput.setText(dphoneNumber);
                    addressInput.setText(daddress);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getContext(), "Database read failed!", Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * Kijelentkezes megvalositasa
         */
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        /**
         * A profil adatainak a frissitese.
         */
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mfirstName = firstNameInput.getText().toString();
                mlastName = lastNameInput.getText().toString();
                memail = emailInput.getText().toString();
                maddress = addressInput.getText().toString();

                if(validateInputs())
                {
                    myRef.child(userUID).child("FirstName").setValue(mfirstName);
                    myRef.child(userUID).child("LastName").setValue(mlastName);
                    myRef.child(userUID).child("Email").setValue(memail);
                    myRef.child(userUID).child("Address").setValue(maddress);
                    myRef.child(userUID).child("UserUpdated").setValue(System.currentTimeMillis());

                    if(photoChanged)
                    {
                        //Might have to change the +.jpg to a function that detects file type (mime)
                        String childRoute = "images/" + userUID + "/profile/" + System.currentTimeMillis() + ".jpg";

                        StorageReference riversRef = mStorageRef.child(childRoute);

                        riversRef.putFile(mImageUri);

                        myRef.child(userUID).child("UserImage").setValue(childRoute);
                    }
                    Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * A profil kepenek a modositasa es kivalasztasa
         */
        profileImageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        return fullView;
    }

    /**
     * Ez a fuggveny valositja meg azt, hogy a felhasznalo a telefonjarol tudjon kepet kivalasztani.
     */
    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    /**
     * Ha a kepet kivalasztotta a felhasznalo, akkor azt az applikacio frissiteni fogja a fragmenten
     * es latni fogja, hogy azt valasztotta-e ki amit szeretett volna vagy sem.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

            Glide.with(getContext())
                    .load(mImageUri)
                    .into(profileImageInput);

            photoChanged = true;
        }
    }

    /**
     * Ez a fuggveny validalja a bemeneti adatokat, hogy azok megfeleloek-e vagy sem.
     * @return
     */
    private boolean validateInputs()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String namePattern = "[A-Z]{1}[a-z]+";

        if(mfirstName.isEmpty())
        {
            Toast.makeText(getContext(), "First name can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mlastName.isEmpty())
        {
            Toast.makeText(getContext(), "Last name can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!mfirstName.matches(namePattern) || !mlastName.matches(namePattern))
        {
            Toast.makeText(getContext(), "Name should start with capital letters!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(memail.isEmpty())
        {
            Toast.makeText(getContext(), "Email can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!memail.matches(emailPattern))
        {
            Toast.makeText(getContext(), "Not a valid email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(maddress.isEmpty())
        {
            Toast.makeText(getContext(), "Address can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
