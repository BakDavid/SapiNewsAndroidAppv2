package com.example.pedrohuan.sapinewsandroidappv2.application.models;

import com.google.firebase.database.IgnoreExtraProperties;

public class User {

    public String FirstName;
    public String LastName;
    public String Email;
    public String Address;
    public String PhoneNumber;
    public String UserImage;
    public int UserUpdated;

    public User() {
    }

    public User(String firstName, String lastName, String email, String address, String phoneNumber, String userImage, int userUpdated) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Address = address;
        PhoneNumber = phoneNumber;
        UserImage = userImage;
        UserUpdated = userUpdated;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public int getUserUpdated() {
        return UserUpdated;
    }

    public void setUserUpdated(int userUpdated) {
        UserUpdated = userUpdated;
    }
}
