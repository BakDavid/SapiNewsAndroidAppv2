package com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.example.pedrohuan.sapinewsandroidappv2.application.CreateNewsFragment;
import com.example.pedrohuan.sapinewsandroidappv2.application.DetailedFragment;
import com.example.pedrohuan.sapinewsandroidappv2.application.Main2Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myref = database.getReference().child("news");
    DatabaseReference myrefUser = database.getReference().child("users");

    private List<ListItem> listitems;
    private List<String> newKey;
    private Context context;

    private StorageReference mStorageRef;

    private String profileImage;

    public NewsAdapter(List<ListItem> listitems,List<String> newKey, Context context) {
        this.listitems = listitems;
        this.newKey = newKey;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.ViewHolder viewHolder, int i) {
        final ListItem listitem = listitems.get(i);
        final String newsKey = newKey.get(i);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        viewHolder.creatorName.setText(listitem.getFullName());
        viewHolder.shortDescription.setText(listitem.getShortDescription());
        viewHolder.clicks.setText(String.valueOf(listitem.getClicks()));


        mStorageRef.child(listitem.getUploadedImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                drawWithGlide(context,uri,viewHolder.uploadedImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to load uploaded image!", Toast.LENGTH_SHORT).show();
            }
        });

        //Get image for user profile here !!!!!!!!!!!!!!!!!!
        myrefUser.child(listitem.getCreatedUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileImage = dataSnapshot.child("UserImage").getValue().toString();

                mStorageRef.child(profileImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .into(viewHolder.profileImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to load image!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Failed to get profile image!", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myref.child(newsKey).child("Clicks").setValue(listitem.getClicks() + 1);

                Fragment detailedFragment = DetailedFragment.newInstance(listitem);

                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,detailedFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView shortDescription;
        public TextView creatorName;
        public TextView clicks;
        public ImageView uploadedImage;
        public CircleImageView profileImage;
        public ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            creatorName = (TextView) itemView.findViewById(R.id.profile_text);
            shortDescription = (TextView) itemView.findViewById(R.id.short_description_text);
            clicks = (TextView) itemView.findViewById(R.id.clicks_text);
            uploadedImage = (ImageView) itemView.findViewById(R.id.new_image);
            profileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);
        }
    }

    public void drawWithGlide(Context context,Uri uri,ImageView imageView)
    {
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }
}
