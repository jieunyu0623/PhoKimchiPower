package com.bcit.phokimchipower;

import java.util.ArrayList;

public class Assessment {

    String assessmentName;
    int weight;
    ArrayList<Double> grades;
    double currentGrade;

    public Assessment(String assessmentName, int weight, ArrayList<Double> grades) {
        this.assessmentName = assessmentName;
        this.weight = weight;
        this.grades = grades;
        if (!grades.isEmpty()) {
            this.currentGrade = calculateAssessment();
        } else {
            this.currentGrade = 0.0;
        }
    }

    public double calculateAssessment() {
        double sum = 0;
        for (Double grade: grades) {
            sum += grade;
        }
        return (sum / grades.size());
    }

    public int getWeight() {
        return weight;
    }

    public double getCurrentGrade() {
        return currentGrade;
    }
}
