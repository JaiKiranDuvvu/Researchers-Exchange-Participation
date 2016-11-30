/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.dao;

import com.assignment4.business.TempUserModel;
import com.assignment4.business.User;

/**
 *
 * @author Jai Kiran
 * 
 * 
 */
public interface userDAO {
    
    public User getUser(String Email);
    public int addUserRecord(User user,String password);
    public User valiadteLogin(String userName,String password);
    public int insertForgotPasswordRecord(String toEmail,String cryptTime);
    public int getForgotPasswordRecord(String toEmail,String cryptTime);
    
    /**
     * New method added as per phase 4 requirement
     * 
     * @param User userObject
     * @param String password
     * @return boolean value indicating the transaction result
     */
    public String newUserActivation(User user, String password);
    /**
     * Method signature to fetch user details from user_unactivated table
     * @param email
     * @return TempUserModel
     */
    public TempUserModel getUnactivatedUser(String email);
    /**
     * Method signature to delete user from temp table upon activation
     * @param email
     * @return integer
     */
    public int deleteActivatedUser(String email);
    
    /**
     * Method signature to updat the password for forgot password feature
     * @param email
     * @param password
     * @return
     */
    public int updatePassword(String email, String password);
    
    public String generateRandomToken();
    }
    