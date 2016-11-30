/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.dao;

import com.assignment4.business.Answer;
import com.assignment4.business.Report;
import com.assignment4.business.Study;
import com.assignment4.data.ConnectionPool;
import com.assignment4.data.DBUtil;
import com.assignment4.util.CommonUtility;
import com.assignment4.util.DataAccess;
import com.assignment4.util.Queries;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jai Kiran
 */
public class studyDAOImpl implements studyDAO {

    @Override
    public ArrayList<Study> getStudies(String Email) {
        
        ArrayList<Study> studies = new ArrayList<Study>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(Email);
            dataList.add(tempList);
            rs = DataAccess.getData(Queries.GET_STUDY_FRM_EMAIL, dataList, connection, ps, rs);
            Study study=null;
            while(rs.next()) {
                study = new Study();
                study.setStudyCode(rs.getString(1));
                study.setStudyName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedparticipants(rs.getString(6));
                study.setNumofpartipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNoOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                tempList = new ArrayList();
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswers(tempList);
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
                studies.add(study);
            }
        } catch (SQLException e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return studies;
    }

    @Override
    public int addStudyRecord(Study study, InputStream inputStream) {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count=-1;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            ArrayList dataList = new ArrayList();
            ArrayList tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(study.getStudyName());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(study.getDescription());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(study.getEmail());
            dataList.add(tempList);
            if(inputStream!=null){
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_BLOB);
                tempList.add(inputStream);
                dataList.add(tempList);
            }
            //REQPARTICIPANTS,ACTPARTICIPANTS,SSTATUS
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(study.getRequestedparticipants());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(study.getNumofpartipants());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(study.getStatus());
            dataList.add(tempList);
            HashMap<String,Integer> statusMap=null;
            if(inputStream!=null){
                statusMap= DataAccess.updateDataHandleCommit(Queries.INSERT_STUDY_WITH_IMAGE, dataList, connection, ps);
            }else{
                statusMap= DataAccess.updateDataHandleCommit(Queries.INSERT_STUDY_WITHOUT_IMAGE, dataList, connection, ps);
            }
            int key=-1;
            if(statusMap!=null && statusMap.size()>0){
                count=statusMap.get("count");
                key=statusMap.get("key");
            }
            if(count==1){
                count=-1;
                dataList = new ArrayList();
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_STRING);
                tempList.add(String.valueOf(key));
                dataList.add(tempList);
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_STRING);
                tempList.add(study.getQuestion());
                dataList.add(tempList);
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_STRING);
                tempList.add(study.getAnswerType());
                dataList.add(tempList);
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_INTEGER);
                tempList.add(study.getNoOfAnswers());
                dataList.add(tempList);
                int noOfAnswers=Integer.parseInt(study.getNoOfAnswers());
                for(int k=0;k<noOfAnswers;k++){
                    tempList = new ArrayList();
                    tempList.add(CommonUtility.PARAM_STRING);
                    tempList.add(study.getAnswers().get(k));
                    dataList.add(tempList);
                }
                for(int k=0;k<5-noOfAnswers;k++){
                    tempList = new ArrayList();
                    tempList.add(CommonUtility.PARAM_STRING);
                    tempList.add(null);
                    dataList.add(tempList);
                }
                count=DataAccess.updateData(Queries.INSERT_QUESTION,dataList, connection, ps);
            }
        } catch (Exception e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return count;
    }

    @Override
    public Study getStudies(String Email, String StudyCode) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        Study study=null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(Email);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(StudyCode);
            dataList.add(tempList);
            rs = DataAccess.getData(Queries.GET_STUDY_FRM_EMAIL_AND_STUDYCODE, dataList, connection, ps, rs);
            if(rs.next()) {
                study = new Study();
                study.setStudyCode(rs.getString(1));
                study.setStudyName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedparticipants(rs.getString(6));
                study.setNumofpartipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNoOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                tempList = new ArrayList();
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswers(tempList);
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
            }
        } catch (SQLException e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return study;
    }

    @Override
    public int updateStudyRecord(Study study, InputStream inputStream) {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count=-1;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            ArrayList dataList = new ArrayList();
            ArrayList tempList = null;
            if(inputStream!=null){
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_BLOB);
                tempList.add(inputStream);
                dataList.add(tempList);
            }
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(study.getRequestedparticipants());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(study.getDescription());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(study.getStudyCode());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(study.getStudyName());
            dataList.add(tempList);
            HashMap<String,Integer> statusMap=null;
            if(inputStream!=null){
                statusMap= DataAccess.updateDataHandleCommit(Queries.UPDATE_STUDY_WITH_IMAGE, dataList, connection, ps);
            }else{
                statusMap= DataAccess.updateDataHandleCommit(Queries.UPDATE_STUDY_WITHOUT_IMAGE, dataList, connection, ps);
            }
            if(statusMap!=null && statusMap.size()>0){
                count=statusMap.get("count");
            }
            if(count==1){
                count=-1;
                dataList = new ArrayList();
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_STRING);
                tempList.add(study.getQuestion());
                dataList.add(tempList);
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_STRING);
                tempList.add(study.getAnswerType());
                dataList.add(tempList);
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_INTEGER);
                tempList.add(study.getNoOfAnswers());
                dataList.add(tempList);
                int noOfAnswers=Integer.parseInt(study.getNoOfAnswers());
                for(int k=0;k<noOfAnswers;k++){
                    tempList = new ArrayList();
                    tempList.add(CommonUtility.PARAM_STRING);
                    tempList.add(study.getAnswers().get(k));
                    dataList.add(tempList);
                }
                for(int k=0;k<5-noOfAnswers;k++){
                    tempList = new ArrayList();
                    tempList.add(CommonUtility.PARAM_STRING);
                    tempList.add(null);
                    dataList.add(tempList);
                }
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_STRING);
                tempList.add(study.getStudyCode());
                dataList.add(tempList);
                count=DataAccess.updateData(Queries.UPDATE_QUESTION,dataList, connection, ps);
            }
        } catch (Exception e) {
            count=-1;
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return count;
    }

    @Override
    public int updateStudyRecordStatus(String studyCode, String Status) {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count=-1;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            ArrayList dataList = new ArrayList();
            ArrayList tempList = null;
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(Status);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(studyCode);
            dataList.add(tempList);
            count=DataAccess.updateData(Queries.UPDATE_STUDY_STATUS,dataList, connection, ps);
        } catch (Exception e) {
            count=-1;
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return count;
    }

    @Override
    public ArrayList<Study> getStudiesForParticipation(String eMail) {
        ArrayList<Study> studies = new ArrayList<Study>();
        ArrayList<String> tempList =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        ArrayList dataList=new ArrayList();
        tempList = new ArrayList();
        tempList.add(CommonUtility.PARAM_STRING);
        tempList.add(eMail);
        dataList.add(tempList);
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            rs = DataAccess.getData(Queries.GET_OPEN_STUDIES, dataList, connection, ps, rs);
            Study study=null;
            while(rs.next()) {
                study = new Study();
                study.setStudyCode(rs.getString(1));
                study.setStudyName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedparticipants(rs.getString(6));
                study.setNumofpartipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNoOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                tempList = new ArrayList();
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswers(tempList);
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
                studies.add(study);
            }
        } catch (SQLException e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return studies;
    }

    @Override
    public Study getStudy(String StudyCode) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        Study study=null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(StudyCode);
            dataList.add(tempList);
            rs = DataAccess.getData(Queries.GET_STUDY_FRM_STUDYCODE, dataList, connection, ps, rs);
            if(rs.next()) {
                study = new Study();
                study.setStudyCode(rs.getString(1));
                study.setStudyName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedparticipants(rs.getString(6));
                study.setNumofpartipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNoOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                tempList = new ArrayList();
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswers(tempList);
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
            }
        } catch (SQLException e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return study;
    
    }

    @Override
    public int saveAnswer(Answer answer) {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count=-1;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            ArrayList dataList = new ArrayList();
            ArrayList tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(answer.getStudyCode());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(answer.getQuestionId());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(answer.getEmail());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(answer.getChoice());
            dataList.add(tempList);
            HashMap<String,Integer> statusMap=null;
            statusMap= DataAccess.updateDataHandleCommit(Queries.INSERT_ANSWER, dataList, connection, ps);
            int key=-1;
            if(statusMap!=null && statusMap.size()>0){
                count=statusMap.get("count");
                key=statusMap.get("key");
            }
            if(count==1){
                count=-1;
                dataList = new ArrayList();
                tempList = new ArrayList();
                tempList.add(CommonUtility.PARAM_STRING);
                tempList.add(answer.getEmail());
                dataList.add(tempList);
                statusMap=DataAccess.updateDataHandleCommit(Queries.UPDATE_USER_PARTICIPATION,dataList, connection, ps);
                key=-1;
                if(statusMap!=null && statusMap.size()>0){
                    count=statusMap.get("count");
                    key=statusMap.get("key");
                }
                if(count==1){
                    count=-1;
                    dataList = new ArrayList();
                    tempList = new ArrayList();
                    tempList.add(CommonUtility.PARAM_STRING);
                    tempList.add(answer.getStudyCode());
                    dataList.add(tempList);
                    count=DataAccess.updateData(Queries.UPDATE_STUDY_PARTICIPATION, dataList, connection, ps);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return count;
    }

    @Override
    public int reportQuestion(Report report) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count = -1;
        boolean isAlreadyReported = false;
        boolean isAlreadyReportedStatus = false;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            ArrayList dataList = new ArrayList();
            ArrayList tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(report.getQuestionId());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(report.getStudyCode());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(report.getEmail());
            dataList.add(tempList);
            rs = DataAccess.getData(Queries.VALIDATE_REPORTED_BY_USER, dataList, connection, ps, rs);
            if (rs.next()) {
                isAlreadyReported = true;
            }
            if (!isAlreadyReported) {
                HashMap<String, Integer> statusMap = null;
                statusMap = DataAccess.updateDataHandleCommit(Queries.INSERT_REPORTED, dataList, connection, ps);
                int key = -1;
                if (statusMap != null && statusMap.size() > 0) {
                    count = statusMap.get("count");
                    key = statusMap.get("key");
                }
                if (count == 1) {
                    count = -1;
                    dataList = new ArrayList();
                    tempList = new ArrayList();
                    tempList.add(CommonUtility.PARAM_INTEGER);
                    tempList.add(report.getQuestionId());
                    dataList.add(tempList);
                    tempList = new ArrayList();
                    tempList.add(CommonUtility.PARAM_INTEGER);
                    tempList.add(report.getStudyCode());
                    dataList.add(tempList);
                    rs = null;
                    rs = DataAccess.getData(Queries.VALIDATE_REPORTED_STATUS, dataList, connection, ps, rs);
                    if (rs.next()) {
                        isAlreadyReportedStatus = true;
                    }
                    if (!isAlreadyReportedStatus) {
                        tempList = new ArrayList();
                        tempList.add(CommonUtility.PARAM_STRING);
                        tempList.add(CommonUtility.Report_Status_Pending);
                        dataList.add(tempList);
                        count = DataAccess.updateData(Queries.INSERT_REPORTED_STATUS, dataList, connection, ps);
                    }else{
                        count=1;
                        connection.commit();
                    }
                }
            }else{
                count=0;
            }

        } catch (Exception e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (ps != null) {
                DBUtil.closePreparedStatement(ps);
            }
            if (connection != null) {
                pool.freeConnection(connection);
            }
        }
        return count;
    }

    @Override
    public ArrayList<Report> getReports(String eMail) {
        ArrayList<Report> reports = new ArrayList<Report>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(eMail);
            dataList.add(tempList);
            rs = DataAccess.getData(Queries.FETCH_REPORT_HISTORY, dataList, connection, ps, rs);
            Report report=null;
            while(rs.next()) {
                report = new Report();
                report.setReportDate(rs.getString(1));
                report.setReportedQuestion(rs.getString(2));
                report.setReportStatus(rs.getString(3));
                reports.add(report);
            }
        } catch (SQLException e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return reports;
    
    }

    @Override
    public ArrayList<Report> getReportedQues(String status) {
        ArrayList<Report> reports = new ArrayList<Report>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(status);
            dataList.add(tempList);
            rs = DataAccess.getData(Queries.FETCH_REPORTED_QUESTIONS, dataList, connection, ps, rs);
            Report report=null;
            while(rs.next()) {
                report = new Report();
                report.setReportedQuestion(rs.getString(1));
                report.setNumberOfParticipants(rs.getInt(2));
                report.setStudyCode(rs.getString(3));
                report.setQuestionId(rs.getString(4));
                reports.add(report);
            }
        } catch (SQLException e) {
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return reports;
    }

    @Override
    public int updateReportRecord(String StudyCode, String QuestionId, String Status) {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count=-1;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            ArrayList dataList = new ArrayList();
            ArrayList tempList = null;
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(Status);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(StudyCode);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(QuestionId);
            dataList.add(tempList);
            count=DataAccess.updateData(Queries.UPDATE_REPORT_RECORD,dataList, connection, ps);
        } catch (Exception e) {
            count=-1;
            Logger.getLogger(studyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return count;
    
    }
}
