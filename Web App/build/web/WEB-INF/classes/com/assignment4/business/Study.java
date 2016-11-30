package com.assignment4.business;

import java.io.Serializable;
import java.util.ArrayList;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class Study implements Serializable {

    private String StudyName;
    private String StudyCode;
    private String DateCreated;
    private String Email;
    private String Question;
    private String Requestedparticipants;
    private String Numofpartipants;
    private String Description;
    private String Status;
    private String AnswerType;
    private String NoOfAnswers;
    private byte[] ImageURL;
    private String QuestionId;

    private ArrayList<String> Answers;

    public Study() {
        StudyName = "";
        StudyCode = "";
        DateCreated = "";
        Email="";
        Question="";
        Requestedparticipants="";
        Numofpartipants="";
        Description="";
        Status="";
        AnswerType="";
        NoOfAnswers="";
        Answers=null;
        ImageURL=null;
        QuestionId="";
    }

    public Study(String StudyName, String StudyCode, String DateCreated,String Email,
            String Question,String Requestedparticipants,String Numofpartipants,String Description,
            String Status,String AnswerType,String NoOfAnswers,ArrayList<String> Answers,byte[]  ImageURL,String QuestionId) {
        this.StudyName = StudyName;
        this.StudyCode = StudyCode;
        this.DateCreated = DateCreated;
        this.Email=Email;
        this.Question=Question;
        this.Requestedparticipants=Requestedparticipants;
        this.Numofpartipants=Numofpartipants;
        this.Description=Description;
        this.Status=Status;
        this.AnswerType=AnswerType;
        this.NoOfAnswers=NoOfAnswers;
        this.Answers=Answers;
        this.ImageURL=ImageURL;
        this.QuestionId=QuestionId;
    }
    
    public String getStudyName() {
        return StudyName;
    }

    public void setStudyName(String StudyName) {
        this.StudyName = StudyName;
    }

    public String getStudyCode() {
        return StudyCode;
    }

    public void setStudyCode(String StudyCode) {
        this.StudyCode = StudyCode;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String DateCreated) {
        this.DateCreated = DateCreated;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String QuestionId) {
        this.QuestionId = QuestionId;
    }
    
    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getRequestedparticipants() {
        return Requestedparticipants;
    }

    public void setRequestedparticipants(String Requestedparticipants) {
        this.Requestedparticipants = Requestedparticipants;
    }

    public String getNumofpartipants() {
        return Numofpartipants;
    }

    public void setNumofpartipants(String Numofpartipants) {
        this.Numofpartipants = Numofpartipants;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    public String getNoOfAnswers() {
        return NoOfAnswers;
    }

    public void setNoOfAnswers(String NoOfAnswers) {
        this.NoOfAnswers = NoOfAnswers;
    }
    
    public String getAnswerType() {
        return AnswerType;
    }

    public void setAnswerType(String AnswerType) {
        this.AnswerType = AnswerType;
    }
    
    public String getImageURL(){
        StringBuilder sb = new StringBuilder();
        sb.append("data:image/jpeg;base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(ImageURL, false)));
        return sb.toString();
    }
    
    public void setImageURL(byte[] ImageURL) {
        this.ImageURL = ImageURL;
    }

//    public ArrayList getAnswers(){
//        ArrayList answerList=new ArrayList();
//        answerList=getAnswersFrmStdyCode(StudyCode);
//        return answerList;
//    }
    public ArrayList<String> getAnswers() {
        return Answers;
    }

    public void setAnswers(ArrayList<String> Answers) {
        this.Answers = Answers;
    }
    
    public int getAverage(){
        //return getAverageFrmStdyCode(StudyCode);
        int Avg=0;
        if(AnswerType.equals("Numeric")){
            int sum=0;
            int i=0;
            for(String s:Answers){
                sum=sum+Integer.parseInt(s);
                i=i+1;
            }
            if(sum>0 && i>0)
                Avg=sum/i;
        }
        return Avg;
    }
    
    public int getMinimum(){
        int Min=0;
        if(AnswerType.equals("Numeric")){
            for(String s:Answers){
                if(Min>Integer.parseInt(s))
                    Min=Integer.parseInt(s);
            }
        }
        return Min;
    }
    
    public int getMaximum(){
        int Max=0;
        if(AnswerType.equals("Numeric")){
            for(String s:Answers){
                if(Max<Integer.parseInt(s))
                    Max=Integer.parseInt(s);
            }
        }
        return Max;
    }
    
    public Double getStandardDeviation(){
        //return getStandardDeviationFrmStdyCode(StudyCode);
        return 4.2;
    }
 
}