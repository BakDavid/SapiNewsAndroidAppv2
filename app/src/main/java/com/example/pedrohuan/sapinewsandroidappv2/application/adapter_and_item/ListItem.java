package com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItem {

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

    //private String profileImage;

    public ListItem() {
    }

    public ListItem(Long alert, Long clicks, Long created, String createdUser, String fullName, String uploadedImage, String location, String longDescription, String phoneNumber, String shortDescription, String title) {
        Alert = alert;
        Clicks = clicks;
        Created = created;
        CreatedUser = createdUser;
        FullName = fullName;
        UploadedImage = uploadedImage;
        Location = location;
        LongDescription = longDescription;
        PhoneNumber = phoneNumber;
        ShortDescription = shortDescription;
        Title = title;
    }

    public Long getAlert() {
        return Alert;
    }

    public void setAlert(Long alert) {
        Alert = alert;
    }

    public Long getClicks() {
        return Clicks;
    }

    public void setClicks(Long clicks) {
        Clicks = clicks;
    }

    public Long getCreated() {
        return Created;
    }

    public void setCreated(Long created) {
        Created = created;
    }

    public String getCreatedUser() {
        return CreatedUser;
    }

    public void setCreatedUser(String createdUser) {
        CreatedUser = createdUser;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUploadedImage() {
        return UploadedImage;
    }

    public void setUploadedImage(String uploadedImage) {
        UploadedImage = uploadedImage;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
