package com.bcit.phokimchipower;

import java.util.HashMap;

public class Course {
    String courseName;
    double currentGrade;
    HashMap<String, Double> assignments;  //name - grade; key-value pair
    HashMap<String, Double> quizzes;
    HashMap<String, Double> projects;
    HashMap<String, Double> midterm;
    HashMap<String, Double> finals;
    HashMap<String, Double> labs;

    public Course(){}

    public Course(String courseName){
        this.courseName = courseName;
        assignments = new HashMap<String, Double>();
        quizzes = new HashMap<String, Double>();
        projects = new HashMap<String, Double>();
        midterm = new HashMap<String, Double>();
        finals = new HashMap<String, Double>();
        labs = new HashMap<String, Double>();
    }

    public String getCourseName(){
        return courseName;
    }

    public Double getCurrentGrade(){
        return currentGrade;
    }

    public void addAssignment(String assignName, double grade){
        assignments.put(assignName, grade);
    }

    public void addQuiz(String quizName, double grade){
        quizzes.put(quizName, grade);
    }

    public void addProject(String projName, double grade){
        projects.put(projName, grade);
    }

    public void addMidterm(String midtermName, double grade){
        midterm.put(midtermName, grade);
    }

    public void addFinal(String finalsName, double grade){
        finals.put(finalsName, grade);
    }

    public void addLabs(String labName, double grade){
        labs.put(labName, grade);
    }
}
