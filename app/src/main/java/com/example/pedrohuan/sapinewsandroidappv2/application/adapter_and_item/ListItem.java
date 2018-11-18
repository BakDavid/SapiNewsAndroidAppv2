package com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItem {

    private String shortDescription;
    private String creatorName;
    private String clicks;
    private Uri uploadedImage;

    public ListItem() {
    }

    public ListItem(String shortDescription, String creatorName, String clicks, Uri uploadedImage) {
        this.shortDescription = shortDescription;
        this.creatorName = creatorName;
        this.clicks = clicks;
        this.uploadedImage = uploadedImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getClicks() {
        return clicks;
    }

    public void setClicks(String clicks) {
        this.clicks = clicks;
    }

    public Uri getUploadedImage() {
        return uploadedImage;
    }

    public void setUploadedImage(Uri uploadedImage) {
        this.uploadedImage = uploadedImage;
    }
}
