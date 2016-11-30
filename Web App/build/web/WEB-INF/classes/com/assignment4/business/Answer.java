package com.assignment4.business;

import java.io.Serializable;

public class Answer implements Serializable {

    private String StudyCode;
    private String QuestionId;
    private String Email;
    private String Choice;
    private String SubmissionDate;
    
    public Answer() {
        StudyCode="";
        QuestionId="";
        Email = "";
        Choice = "";
        SubmissionDate = "";
    }

    public Answer(String StudyCode,String QuestionId,String Email, String Choice, String SubmissionDate) {
        this.StudyCode=StudyCode;
        this.QuestionId=QuestionId;
        this.Email = Email;
        this.Choice = Choice;
        this.SubmissionDate = SubmissionDate;
    }

    public String getStudyCode() {
        return StudyCode;
    }

    public void setStudyCode(String StudyCode) {
        this.StudyCode = StudyCode;
    }
    
    public String getQuestionId(){
        return QuestionId;
    }
    
    public void setQuestionId(String QuestionId){
        this.QuestionId=QuestionId;
    }
    
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getChoice() {
        return Choice;
    }

    public void setChoice(String Choice) {
        this.Choice = Choice;
    }

    public String getSubmissionDate() {
        return SubmissionDate;
    }

    public void setSubmissionDate(String SubmissionDate) {
        this.SubmissionDate = SubmissionDate;
    }
    
}