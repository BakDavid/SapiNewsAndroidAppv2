package com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pedrohuan.sapinewsandroidappv2.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<ListItem> listitems;
    private Context context;

    public NewsAdapter(List<ListItem> listitems, Context context) {
        this.listitems = listitems;
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
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, int i) {
        ListItem listitem = listitems.get(i);

        viewHolder.creatorName.setText(listitem.getCreatorName());
        viewHolder.shortDescription.setText(listitem.getShortDescription());
        viewHolder.clicks.setText(listitem.getClicks());

        //Only to test out the image, modification required
        Glide.with(context)
                .load("https://post-phinf.pstatic.net/MjAxODA3MTFfOTYg/MDAxNTMxMzE3Mzg0Nzk1.d_buEM661Ys0LycI7u6OnbD2aCbrePP1M9WYnklY0UQg.LsS1HSr0bAByWtlHpy0AFzOmcL7aazmiqLRrt66gZgMg.JPEG/IBVmYy8g1ScRt2LXh4qNrKP8qW-0.jpg?type=f200_200")
                .into(viewHolder.uploadedImage);

        //viewHolder.uploadedImage.setImageURI(listitem.getUploadedImage());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            creatorName = (TextView) itemView.findViewById(R.id.profile_text);
            shortDescription = (TextView) itemView.findViewById(R.id.short_description_text);
            clicks = (TextView) itemView.findViewById(R.id.clicks_text);
            uploadedImage = (ImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}
