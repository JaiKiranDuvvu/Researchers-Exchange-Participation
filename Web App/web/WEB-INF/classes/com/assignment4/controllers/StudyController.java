package com.assignment4.controllers;

import com.assignment4.business.Answer;
import com.assignment4.business.Report;
import com.assignment4.business.Study;
import com.assignment4.business.User;
import com.assignment4.dao.studyDAO;
import com.assignment4.dao.studyDAOImpl;
import com.assignment4.util.CommonUtility;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
/**
 *
 * @author Jai Kiran
 */
@WebServlet(name = "StudyController", urlPatterns = {"/StudyController"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class StudyController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        String url="/home.jsp";    
        String action=CommonUtility.checkNullString(request.getParameter("action"));
        
        if(action.equals("")){
            if(session.getAttribute("theUser")== null){
                if(session.getAttribute("theAdmin")==null){
                    url="/home.jsp";
                }else{
                    url="/admin.jsp";
                }
            }else{
                url="/main.jsp";
            }
        }else if(action.equals("participate")){
            if(session.getAttribute("theUser")!= null){
                if(request.getParameter("StudyCode")== null){
                    studyDAO sdao=new studyDAOImpl();
                    String eMail=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    ArrayList<Study> studies=sdao.getStudiesForParticipation(eMail);
                    request.setAttribute("studies", studies);
                    url="/participate.jsp";
                }
                else{
                    studyDAO sdao=new studyDAOImpl();
                    String studyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                    Study study=sdao.getStudy(studyCode);
                    request.setAttribute("study", study);
                    url="/question.jsp";
                }
            }else{
                url="/login.jsp";
            }
        }else if(action.equals("newStudy")){
            if(session.getAttribute("theUser")!=null){
               url="/newstudy.jsp";
            }else{
                url="/login.jsp";
            }
        }
        else if(action.equals("edit")){
            if(session.getAttribute("theUser")!=null){
                if(request.getParameter("StudyCode")!=null){
                    studyDAO sdao=new studyDAOImpl();
                    String creatorEmail=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    String StudyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                    Study study=sdao.getStudies(creatorEmail,StudyCode);
                    request.setAttribute("study", study);
                    url="/editstudy.jsp";
                }
            }else{
                url="/login.jsp";
            }
        }else if(action.equals("report")){
            if(session.getAttribute("theUser")!=null){
                if(request.getParameter("StudyCode")!=null){
                    if(request.getParameter("ReporterEmail")!=null){
                        String StudyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                        String QuestionId=CommonUtility.checkNullString(request.getParameter("QuestionId"));
                        String ReporterEmail=CommonUtility.checkNullString(request.getParameter("ReporterEmail"));
                        Report report=new Report();
                        report.setStudyCode(StudyCode);
                        report.setQuestionId(QuestionId);
                        report.setEmail(ReporterEmail);
                        report.setReportStatus(CommonUtility.Report_Status_Pending);
                        studyDAO sdao=new studyDAOImpl();
                        int count=sdao.reportQuestion(report);
                        String msg="";
                        if(count==1){
                            msg="Question Reported. . .";
                        }else if(count==0){
                            msg="Question Already Reported. . .";
                        }else{
                            msg="Error in Saving Data";
                        }
                        request.setAttribute("msg",msg);
                        url="/confirmrep.jsp";
                    }
                }else{
                    studyDAO sdao=new studyDAOImpl();
                    String ReporterEmail=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    ArrayList<Report> report=sdao.getReports(ReporterEmail);
                    request.setAttribute("reports", report);
                    url="/reporth.jsp";
                }
            }else{
               url="/login.jsp"; 
            }
        }else if(action.equals("reportques")){
            if(session.getAttribute("theAdmin")!=null){
                studyDAO sdao=new studyDAOImpl();  
                ArrayList<Report> reports=sdao.getReportedQues(CommonUtility.Report_Status_Pending);
                request.setAttribute("reports", reports);
                url="/reportques.jsp";
            }else{
                url="/login.jsp"; 
            }
        }else if(action.equals("approve")){
            if(session.getAttribute("theAdmin")!=null){
                if(request.getParameter("StudyCode")!=null){
                    String studyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                    String QuestionId=CommonUtility.checkNullString(request.getParameter("QuestionId"));
                    studyDAO sdao=new studyDAOImpl();
                    int count=sdao.updateReportRecord(studyCode, QuestionId,CommonUtility.Report_Status_Approved);
                    String msg="Error in Saving Data";
                    if(count==1){
                        msg="Reported Question Approved..";
                    }
                    ArrayList<Report> reports=sdao.getReportedQues(CommonUtility.Report_Status_Pending);
                    request.setAttribute("reports", reports);
                    request.setAttribute("msg", msg);
                    url="/reportques.jsp"; 
                }else{
                    studyDAO sdao=new studyDAOImpl();
                    ArrayList<Report> reports=sdao.getReportedQues(CommonUtility.Report_Status_Pending);
                    request.setAttribute("reports", reports);
                    url="/reportques.jsp";
                }
            }else{
                url="/login.jsp"; 
            }
        }else if(action.equals("disapprove")){
            if(session.getAttribute("theAdmin")!=null){
                if(request.getParameter("StudyCode")!=null){
                    String studyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                    String QuestionId=CommonUtility.checkNullString(request.getParameter("QuestionId"));
                    studyDAO sdao=new studyDAOImpl();
                    int count=sdao.updateReportRecord(studyCode, QuestionId,CommonUtility.Report_Status_DisApproved);
                    String msg="Error in Saving Data";
                    if(count==1){
                        msg="Reported Question Disapproved..";
                    }
                    ArrayList<Report> reports=sdao.getReportedQues(CommonUtility.Report_Status_Pending);
                    request.setAttribute("reports", reports);
                    request.setAttribute("msg", msg);
                    url="/reportques.jsp"; 
                }else{
                    studyDAO sdao=new studyDAOImpl();
                    ArrayList<Report> reports=sdao.getReportedQues(CommonUtility.Report_Status_Pending);
                    request.setAttribute("reports", reports);
                    url="/reportques.jsp";
                }
            }else{
                url="/login.jsp"; 
            }
        }else if(action.equals("update")){
            if(session.getAttribute("theUser")!=null){
                Study study=new Study();
                String StudyName=CommonUtility.checkNullString(request.getParameter("study_name"));
                String StudyCode=CommonUtility.checkNullString(request.getParameter("study_code"));
                String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                String Question=CommonUtility.checkNullString(request.getParameter("question_text"));
                String Requestedparticipants=CommonUtility.checkNullString(request.getParameter("participants"));
                String Numofpartipants=CommonUtility.checkNullString(request.getParameter("Numofpartipants"));
                String Description=CommonUtility.checkNullString(request.getParameter("description"));
                String Status=CommonUtility.checkNullString(request.getParameter("Status"));
                String AnswerType=CommonUtility.checkNullString(request.getParameter("Answer1"));
                try{
                    Integer.parseInt(AnswerType);
                    AnswerType=CommonUtility.Answer_Type_Numeric;
                }
                catch(NumberFormatException e){
                    AnswerType=CommonUtility.Answer_Type_Text;
                }
                String NoOfAnswers=CommonUtility.checkNullString(request.getParameter("answers"));
                ArrayList<String> Answers=new ArrayList<String>();
                if(AnswerType.equals(CommonUtility.Answer_Type_Numeric)){
                    for(int i=1;i<=Integer.parseInt(NoOfAnswers);i++){
                        Answers.add(CommonUtility.checkNullString(request.getParameter("Answer"+i)));
                    }
                }else{
                    for(int i=1;i<=Integer.parseInt(NoOfAnswers);i++){
                        Answers.add(CommonUtility.checkNullString(request.getParameter("Answer"+i)));
                    }
                } 
                study.setStudyName(StudyName);
                study.setStudyCode(StudyCode);
                study.setEmail(Email);
                study.setQuestion(Question);
                study.setRequestedparticipants(Requestedparticipants);
                study.setNumofpartipants(Numofpartipants);
                study.setDescription(Description);
                study.setStatus(Status);
                study.setAnswerType(AnswerType);
                study.setNoOfAnswers(NoOfAnswers);
                study.setAnswers(Answers);
                studyDAO sdao=new studyDAOImpl();
                Part filePart=request.getPart("file"); 
                InputStream inputStream = null;
                if (filePart != null) {
                    inputStream = filePart.getInputStream();
                }
                int count=sdao.updateStudyRecord(study,inputStream);
                if(count==1){
                    request.setAttribute("studies", sdao.getStudies(Email));
                    url="/studies.jsp"; 
                }else{
                    url="/login.jsp"; 
                }
            }else{
                url="/login.jsp"; 
            }
        }else if(action.equals("add")){
            if(session.getAttribute("theUser")!=null){
                Study study=new Study();
                String StudyName=CommonUtility.checkNullString(request.getParameter("study_name"));
                Date date=new Date();
                String DateCreated=new SimpleDateFormat("dd-MM-YYYY").format(date);
                String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                String Question=CommonUtility.checkNullString(request.getParameter("question_text"));
                String Requestedparticipants=CommonUtility.checkNullString(request.getParameter("participant_text"));
                String Numofpartipants="0";
                String Description=CommonUtility.checkNullString(request.getParameter("description"));
                String Status=CommonUtility.Study_Status_Start;
                String AnswerType=CommonUtility.checkNullString(request.getParameter("Answer1"));
                try{
                    Integer.parseInt(AnswerType);
                    AnswerType=CommonUtility.Answer_Type_Numeric;
                }
                catch(NumberFormatException e){
                    AnswerType=CommonUtility.Answer_Type_Text;
                }
                String NoOfAnswers=CommonUtility.checkNullString(request.getParameter("answers"));
                ArrayList<String> Answers=new ArrayList<String>();
                if(AnswerType.equals(CommonUtility.Answer_Type_Numeric)){
                    for(int i=1;i<=Integer.parseInt(NoOfAnswers);i++){
                        Answers.add(CommonUtility.checkNullString(request.getParameter("Answer"+i)));
                    }
                }else{
                    for(int i=1;i<=Integer.parseInt(NoOfAnswers);i++){
                        Answers.add(CommonUtility.checkNullString(request.getParameter("Answer"+i)));
                    }
                } 
                study.setStudyName(StudyName);
                study.setDateCreated(DateCreated);
                study.setEmail(Email);
                study.setQuestion(Question);
                study.setRequestedparticipants(Requestedparticipants);
                study.setNumofpartipants(Numofpartipants);
                study.setDescription(Description);
                study.setStatus(Status);
                study.setAnswerType(AnswerType);
                study.setNoOfAnswers(NoOfAnswers);
                study.setAnswers(Answers);
                studyDAO sdao=new studyDAOImpl();
                Part filePart=request.getPart("file"); 
                InputStream inputStream = null;
                if (filePart != null) {
                    inputStream = filePart.getInputStream();
                }
                int count=sdao.addStudyRecord(study,inputStream);
                if(count==1){
                    request.setAttribute("studies", sdao.getStudies(Email));
                    url="/studies.jsp"; 
                }else{
                    url="/login.jsp"; 
                }
            }
        }else if(action.equals("start")){
            if(session.getAttribute("theUser")!=null){
                if(request.getParameter("StudyCode")!=null){
                    String studyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                    studyDAO sdao=new studyDAOImpl();
                    int count=sdao.updateStudyRecordStatus(studyCode,CommonUtility.Study_Status_Start);
                    String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    ArrayList<Study> studies=sdao.getStudies(Email);
                    request.setAttribute("studies", studies);
                    if(count==1)
                        request.setAttribute("msg", "Details saved successfully");
                    else
                        request.setAttribute("msg", "Error in Saving Data.");
                    url="/studies.jsp"; 
                }else{
                    studyDAO sdao=new studyDAOImpl();
                    String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    ArrayList<Study> studies=sdao.getStudies(Email);
                    request.setAttribute("studies", studies);
                    url="/studies.jsp"; 
                }
            }else{
                url="/login.jsp"; 
            }
        }else if(action.equals("stop")){
        
            if(session.getAttribute("theUser")!=null){
                if(request.getParameter("StudyCode")!=null){
                    String studyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                    studyDAO sdao=new studyDAOImpl();
                    int count=sdao.updateStudyRecordStatus(studyCode,CommonUtility.Study_Status_Stop);
                    String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    ArrayList<Study> studies=sdao.getStudies(Email);
                    request.setAttribute("studies", studies);
                    if(count==1)
                        request.setAttribute("msg", "Details saved successfully");
                    else
                        request.setAttribute("msg", "Error in Saving Data.");
                    url="/studies.jsp"; 
                }else{
                    studyDAO sdao=new studyDAOImpl();
                    String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    ArrayList<Study> studies=sdao.getStudies(Email);
                    request.setAttribute("studies", studies);
                    url="/studies.jsp"; 
                }
            }else{
                url="/login.jsp"; 
            }
        }else if(action.equals("answer")){
            if(session.getAttribute("theUser")!=null){
                if(request.getParameter("StudyCode")!=null
                        && 
                   request.getParameter("choice")!=null
                  ){
                    String studyCode=CommonUtility.checkNullString(request.getParameter("StudyCode"));
                    String QuestionId=CommonUtility.checkNullString(request.getParameter("QuestionId"));
                    String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    String choice=CommonUtility.checkNullString(request.getParameter("choice"));
                    studyDAO sdao=new studyDAOImpl();
                    Answer answer=new Answer();
                    answer.setStudyCode(studyCode);
                    answer.setQuestionId(QuestionId);
                    answer.setEmail(Email);
                    answer.setChoice(choice);
                    int count=sdao.saveAnswer(answer);
                    if(count==1){
                    String Coins=session.getAttribute("coins")==null?
                            "0":CommonUtility.checkNullString(session.getAttribute("coins"));
                    String NumParticipation=session.getAttribute("NumParticipation")==null?
                            "0":CommonUtility.checkNullString(session.getAttribute("NumParticipation"));
                    User user=(User)session.getAttribute("theUser");
                    user.setNumCoins(Integer.parseInt(Coins)+1);
                    user.setNumParticipation(Integer.parseInt(NumParticipation)+1);
                    session.setAttribute("theUser", user);
                    }else{
                        request.setAttribute("msg","Error in Saving Data");
                    }ArrayList<Study> studies=sdao.getStudiesForParticipation(Email);
                    request.setAttribute("studies", studies);
                    url="/participate.jsp";
                }
            }else{
                url="/login.jsp"; 
            }
        }else if(action.equals("studies")){
            if(session.getAttribute("theUser")!=null){
                    String Email=CommonUtility.checkNullString(((User)session.getAttribute("theUser")).getEmail());
                    studyDAO sdao=new studyDAOImpl();
                    ArrayList <Study> studies=sdao.getStudies(Email);
                    request.setAttribute("studies",studies);
                    url="/studies.jsp"; 
                }else{
                    url="/login.jsp"; 
                }
        }else{
            if(session.getAttribute("theUser")==null){
                if(session.getAttribute("theAdmin")==null){
                    url="/home.jsp";
                }else{
                    url="/admin.jsp";
                }
            }else{
                url="/main.jsp";
            }
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);    
    }
    
}

