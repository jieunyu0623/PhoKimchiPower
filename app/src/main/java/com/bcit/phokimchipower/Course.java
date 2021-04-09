package com.bcit.phokimchipower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Course {
    String courseName;
    double currentGrade;
    HashMap<String, Integer> weight;
    ArrayList<Grade> grades;

    public Course(){}

    public Course(String courseName, HashMap<String, Integer> weight){
        this.courseName = courseName;
        this.weight = weight;
        this.currentGrade = 0;
        this.grades = new ArrayList<>();
    }

    public String getCourseName(){
        return courseName;
    }

    public Double getCurrentGrade(){
        return currentGrade;
    }

    public HashMap<String, Integer> getWeight() {
        return weight;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", currentGrade=" + currentGrade +
                ", weight=" + weight +
                ", grades=" + grades +
                '}';
    }
}

