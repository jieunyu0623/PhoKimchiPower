package com.bcit.phokimchipower;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    String uId;
    String email;
    String name;
    ArrayList<Course> courses;

    public User(String uId, String email, String name) {
        this.uId = uId;
        this.email = email;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getuId(){
        return uId;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public void addCourse(Course c){
        courses.add(c);
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }
}


