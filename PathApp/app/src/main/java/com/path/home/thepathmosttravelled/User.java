package com.path.home.thepathmosttravelled;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String email;
    public String DateCreated;
    public String DateModified;
    public boolean isActive;
    public double score;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String lastName, String username,String password, String email,boolean isActive,String cDate, String mDate,double mScore) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.DateCreated = cDate;
        this.DateModified = mDate;
        this.isActive = isActive;
        this.score = mScore;

    }

}