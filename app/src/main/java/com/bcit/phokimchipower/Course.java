package com.bcit.phokimchipower;

import java.util.HashMap;

public class Course {
    String courseName;
    double currentGrade;
    HashMap<String, Integer> weight;

    public Course(){}

//    public Course(String courseName, HashMap<String, Integer> weight){
//        this.courseName = courseName;
//        this.weight = weight;
//        this.currentGrade = 0;
//    }

    public Course(String courseName){ //testing
        this.courseName = courseName;
        //this.weight = weight;
        this.currentGrade = 0;
    }

    public String getCourseName(){
        return courseName;
    }

    public Double getCurrentGrade(){
        return currentGrade;
    }
}
