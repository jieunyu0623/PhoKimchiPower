package com.bcit.phokimchipower;

public class Grade {
    String evaluationType;
    String gradeName;
    double grade;

    public Grade(){}

    public Grade(String evaluationType, String gradeName, double grade) {

        this.evaluationType = evaluationType;
        this.gradeName = gradeName;
        this.grade = grade;

    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public String getGradeName() {
        return gradeName;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "evaluationType='" + evaluationType + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", grade=" + grade +
                '}';
    }
}
