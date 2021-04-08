package com.bcit.phokimchipower;

import java.util.ArrayList;

public class Course {

    String courseName;
    ArrayList<Assessment> assessments;
    Double courseGrade;

    public Course(String courseName, ArrayList<Assessment> assessments) {
        this.courseName = courseName;
        this.assessments = assessments;
        this.courseGrade = calculateCourseGrade();
    }

    public double calculateCourseGrade() {
        double gradeSum = 0;
        int weightSum = 0;
        for (Assessment assessment: assessments) {
            if (assessment.getCurrentGrade() != 0.0) {
                gradeSum += assessment.getCurrentGrade() * assessment.getWeight();
                weightSum += assessment.getWeight();
            }
        }
        return (gradeSum / weightSum);
    }

    public Double getCourseGrade() {
        return courseGrade;
    }
}
