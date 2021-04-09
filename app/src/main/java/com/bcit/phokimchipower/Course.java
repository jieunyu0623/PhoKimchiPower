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

    public Course(String courseName, HashMap<String, Integer> weight, ArrayList<Grade> grades) {
        this.courseName = courseName;
        this.weight = weight;
        this.currentGrade = 0;
        this.grades = grades;
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

    public void printGrades() {
        if (grades != null) {
            for (Grade g : grades) {
                System.out.println(g.toString());
            }
        }
    }

    public void setCurrentGrade(Double grade) {
        this.currentGrade = grade;
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

