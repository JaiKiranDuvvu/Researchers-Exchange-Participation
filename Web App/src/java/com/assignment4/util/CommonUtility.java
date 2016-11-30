/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.util;

/**
 *
 * @author Jai Kiran
 */
public class CommonUtility {
    
    public static final String App_Name="REP";
    public static final String Report_Status_Pending="Pending";
    public static final String Report_Status_Approved="approve";
    public static final String Report_Status_DisApproved="disapprove";
    public static final String Answer_Type_Numeric="Numeric";
    public static final String Answer_Type_Text="text";
    public static final String Study_Status_Start="start";
    public static final String Study_Status_Stop="stop";
    public static final String PARAM_STRING="String";
    public static final String PARAM_INTEGER="Integer";
    public static final String PARAM_DOUBLE="Double";
    public static final String PARAM_DATE="Date";
    public static final String PARAM_LONG="Long";
    public static final String PARAM_FLOAT="Float";
    public static final String PARAM_BLOB="Blob";
    public static final String MAIL_FROM_ADDRESS="donotreply.researchersexchange@gmail.com";
    public static final String MAIL_FROM_ADDRESS_NAME="Researchers Exchange Participations";
    public static final String MAIL_FROM_ADDRESS_PWD="researchersexchange";
    public static final String ACTIVATION_LINK = "?action=activate&activationtoken=";
    public static final String RESET_LINK = "?action=resetpassword&crypt=";
    
    public static String checkNullString(Object obj){   
        return obj==null?"":obj.toString();
    }
}
