package ro.sapientia.ms.sapinewsandroidappv2.application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import ro.sapientia.ms.sapinewsandroidappv2.application.adapter_and_item.ListItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedFragment extends Fragment {

    //Database
    String userUID = FirebaseAuth.getInstance().getUid();

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("news");

    String openedFromMyNews = "MyNewsFragment";
    String openedFromListNews = "ListNewsFragment";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    private static final String mNewsKey = "newsKey";
    private static final String mOpenedFromFragment = "openedFromFragment";

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
    private String NewsKey;
    private String OpenedFromFragment;

    TextView textTitle;
    TextView textFullName;
    TextView textLongDescription;
    TextView textPhoneNumber;
    TextView textLocation;

    ImageView imageUploadedImage;

    Button alertButton;
    Button deleteButton;
    Button shareButton;

    private StorageReference mStorageRef;

    public DetailedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailedFragment.
     */
    public static DetailedFragment newInstance(ListItem listItem,String newsKey,String openedFromFragment) {
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
        args.putString(mNewsKey,newsKey);
        args.putString(mOpenedFromFragment,openedFromFragment);
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
            NewsKey = getArguments().getString(mNewsKey);
            OpenedFromFragment = getArguments().getString(mOpenedFromFragment);

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
        deleteButton = (Button) fullview.findViewById(R.id.delete_button);
        shareButton = (Button) fullview.findViewById(R.id.share_button);

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

        if(userUID.matches(CreatedUser))
        {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Do you want to delete the new?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myRef.child(NewsKey).removeValue();

                            if(OpenedFromFragment.matches(openedFromMyNews))
                            {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new MyNewsFragment()).commit();
                            }
                            if(OpenedFromFragment.matches(openedFromListNews))
                            {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new ListNewsFragment()).commit();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.create();
                    alertDialog.show();
                }
            });
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
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

    private void shareIt()
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
