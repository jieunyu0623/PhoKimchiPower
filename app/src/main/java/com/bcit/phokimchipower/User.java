package com.bcit.phokimchipower;

import java.util.ArrayList;

public class User {
    String uId;
    String email;
    String name;
    ArrayList<Course> courses;
    int courseNumber = -1;

    //    public User(String uId, String email, String name, ArrayList<Course> courses) {
//        this.uId = uId;
//        this.email = email;
//        this.name = name;
//        this.courses = courses;
//    }
    public User(String uId, String email, String name) {
        this.courseNumber++;
        this.uId = uId;
        this.email = email;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public int getCourseNumber() {
        return courseNumber;
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


