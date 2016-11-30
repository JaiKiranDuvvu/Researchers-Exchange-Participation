/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.dao;

import com.assignment4.business.Answer;
import com.assignment4.business.Report;
import com.assignment4.business.Study;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Jai Kiran
 */
public interface studyDAO {
    
    public ArrayList<Study> getStudies(String Email);
    public Study getStudies(String Email,String StudyCode);
    public int addStudyRecord(Study study,InputStream inputStream);
    public int updateStudyRecord(Study study,InputStream inputStream);
    public int updateStudyRecordStatus(String studyCode,String Status);
    public ArrayList<Study> getStudiesForParticipation(String eMail);
    public Study getStudy(String StudyCode);
    public int saveAnswer(Answer answer);
    public int reportQuestion(Report report);
    public ArrayList<Report> getReports(String eMail);
    public ArrayList<Report> getReportedQues(String status);
    public int updateReportRecord(String StudyCode,String QuestionId,String Status);
}
