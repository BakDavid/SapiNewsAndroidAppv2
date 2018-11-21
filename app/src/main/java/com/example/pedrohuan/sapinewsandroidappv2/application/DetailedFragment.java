package com.example.pedrohuan.sapinewsandroidappv2.application;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item.ListItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String mAlert = "alerts";
    private static final String mClicks = "clicks";
    private static final String mCreated = "created";
    private static final String mCreatedUser = "created_user";
    private static final String mFullName = "full_name";
    private static final String mUploadedImage = "uploaded_image";
    private static final String mLocation = "location";
    private static final String mLongDescription = "long_description";
    private static final String mPhoneNumber = "phone_number";
    private static final String mShortDescription = "short_description";
    private static final String mTitle = "title";

    private Long Alert;
    private Long Clicks;
    private Long Created;
    private String CreatedUser;
    private String FullName;
    private String UploadedImage;
    private String Location;
    private String LongDescription;
    private String PhoneNumber;
    private String ShortDescription;
    private String Title;

    TextView textTitle;
    TextView textFullName;
    TextView textLongDescription;
    TextView textPhoneNumber;
    TextView textLocation;

    ImageView imageUploadedImage;

    Button alertButton;

    private StorageReference mStorageRef;

//static
//    private OnFragmentInteractionListener mListener;

    public DetailedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailedFragment.
     */
    public static DetailedFragment newInstance(ListItem listItem) {
        DetailedFragment fragment = new DetailedFragment();
        Bundle args = new Bundle();
        args.putString(mCreatedUser,listItem.getCreatedUser());
        args.putLong(mClicks,listItem.getClicks());
        args.putLong(mAlert,listItem.getAlert());
        args.putLong(mCreated,listItem.getCreated());
        args.putString(mFullName,listItem.getFullName());
        args.putString(mUploadedImage,listItem.getUploadedImage());
        args.putString(mLocation,listItem.getLocation());
        args.putString(mLongDescription,listItem.getLongDescription());
        args.putString(mPhoneNumber,listItem.getPhoneNumber());
        args.putString(mShortDescription,listItem.getShortDescription());
        args.putString(mTitle,listItem.getTitle());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Alert = getArguments().getLong(mAlert);
            Clicks = getArguments().getLong(mClicks);
            Created = getArguments().getLong(mCreated);
            CreatedUser = getArguments().getString(mCreatedUser);
            FullName = getArguments().getString(mFullName);
            UploadedImage = getArguments().getString(mUploadedImage);
            Location = getArguments().getString(mLocation);
            LongDescription = getArguments().getString(mLongDescription);
            PhoneNumber = getArguments().getString(mPhoneNumber);
            ShortDescription = getArguments().getString(mShortDescription);
            Title = getArguments().getString(mTitle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fullview = inflater.inflate(R.layout.fragment_detailed, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        textFullName = (TextView) fullview.findViewById(R.id.creator_text);
        textLocation = (TextView) fullview.findViewById(R.id.location_text);
        textLongDescription = (TextView) fullview.findViewById(R.id.long_description_text);
        textPhoneNumber = (TextView) fullview.findViewById(R.id.phone_number_text);
        textTitle = (TextView) fullview.findViewById(R.id.title_text);

        imageUploadedImage = (ImageView) fullview.findViewById(R.id.uploaded_image);

        alertButton = (Button) fullview.findViewById(R.id.alert_button);

        textTitle.setText(Title);
        textPhoneNumber.setText(PhoneNumber);
        textLongDescription.setText(LongDescription);
        textLocation.setText(Location);
        textFullName.setText(FullName);

        mStorageRef.child(UploadedImage).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Successfully downloaded data to local file
                        // ...
                        Glide.with(getContext())
                                .load(uri)
                                .into(imageUploadedImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
                Toast.makeText(getContext(), "Image load failed!", Toast.LENGTH_SHORT).show();
            }
        });

        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return fullview;
    }

    //Ennel lennebb ne irj at semmit (Danks man) !!!!!!!!!!!!!!!
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
