package com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItem {

    private String shortDescription;
    private String creatorName;
    private String clicks;
    private String uploadedImage;
    private String profileImage;

    public ListItem() {
    }

    public ListItem(String shortDescription, String creatorName, String clicks, String uploadedImage, String profileImage) {
        this.shortDescription = shortDescription;
        this.creatorName = creatorName;
        this.clicks = clicks;
        this.uploadedImage = uploadedImage;
        this.profileImage = profileImage;
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

    public String getUploadedImage() {
        return uploadedImage;
    }

    public void setUploadedImage(String uploadedImage) {
        this.uploadedImage = uploadedImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
