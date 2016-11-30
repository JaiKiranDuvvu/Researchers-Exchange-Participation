/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.dao;

import com.assignment4.business.TempUserModel;
import com.assignment4.business.User;
import com.assignment4.data.ConnectionPool;
import com.assignment4.data.DBUtil;
import com.assignment4.security.PasswordUtil;
import com.assignment4.util.CommonUtility;
import com.assignment4.util.DataAccess;
import com.assignment4.util.Queries;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jai Kiran
 */
public class userDAOImpl implements userDAO {

    @Override
    public User getUser(String Email) {
        User user = null;
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
            rs = DataAccess.getData(Queries.SELECT_USER_FRM_EMAIL, dataList, connection, ps, rs);
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString(1));
                user.setEmail(rs.getString(2));
                user.setType(rs.getString(3));
                user.setNumPostedStudies(rs.getInt(4));
                user.setNumParticipation(rs.getInt(5));
                user.setNumCoins(rs.getInt(6));
            }
        } catch (SQLException e) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return user;
    }

    @Override
    public int addUserRecord(User user, String password) {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count=-1;
        try {
            HashMap<String,String> map=PasswordUtil.gethashAndSaltPassword(password);
            password=map.get("hashedPassword");
            String salt=map.get("salt");
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(user.getName());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(user.getEmail());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(password);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(salt);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(user.getType());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(String.valueOf(user.getNumPostedStudies()));
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(String.valueOf(user.getNumParticipation()));
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(String.valueOf(user.getNumCoins()));
            dataList.add(tempList);
            count= DataAccess.updateData(Queries.INSERT_USER, dataList, connection, ps);
        } catch (Exception e) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return count;
    }
    
    public String newUserActivation(User user, String password){
    	String activationToken = "";
    	PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        int count=-1;
        try {
        	
        	activationToken = generateRandomToken();
        	
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(user.getName());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(user.getEmail());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(password);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(password);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(user.getType());
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(String.valueOf(user.getNumPostedStudies()));
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(String.valueOf(user.getNumParticipation()));
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_INTEGER);
            tempList.add(String.valueOf(user.getNumCoins()));
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(activationToken);
            dataList.add(tempList); 
            count= DataAccess.updateData(Queries.INSERT_USER_ACTIVATION, dataList, connection, ps);
        } catch (Exception e) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
    	
    	return activationToken;
    }
    
    public int deleteActivatedUser(String email){
    	int result = -1;
    	PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(email);
            dataList.add(tempList);
            result= DataAccess.updateData(Queries.DELETE_ACTIVATED_USER, dataList, connection, ps);
        } catch (Exception e) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    	
    	return result;
    }
    
    public String generateRandomToken(){
    	String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    public int updatePassword(String email, String password){
    	int result = -1;
    	PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection connection = null;
        
        try {
        	HashMap<String,String> map=PasswordUtil.gethashAndSaltPassword(password);
            password=map.get("hashedPassword");
            String salt=map.get("salt");
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(password);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(salt);
            dataList.add(tempList);
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(email);
            dataList.add(tempList);
            result= DataAccess.updateData(Queries.UPDATE_PASSWORD, dataList, connection, ps);
        } catch (Exception e) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    	
    	return result;
    }

    @Override
    public User valiadteLogin(String userName, String password) {
        User user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool pool = null;
        Connection connection = null;
        String salt="";
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            ArrayList dataList = new ArrayList();
            ArrayList<String> tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(userName);
            dataList.add(tempList);
            rs=DataAccess.getData(Queries.GET_USER_SALT,dataList,connection,ps,rs);
            if (rs.next()) {
                salt=rs.getString(1);
            }
            password=PasswordUtil.gethashedPassword(password,salt);
            rs=null;
            tempList = new ArrayList();
            tempList.add(CommonUtility.PARAM_STRING);
            tempList.add(password);
            dataList.add(tempList);
            rs = DataAccess.getData(Queries.VALIDATE_USER, dataList, connection, ps, rs);
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString(1));
                user.setEmail(rs.getString(2));
                user.setType(rs.getString(3));
                user.setNumPostedStudies(rs.getInt(4));
                user.setNumParticipation(rs.getInt(5));
                user.setNumCoins(rs.getInt(6));
            }
        } catch (SQLException e) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return user;
    }
    
    public TempUserModel getUnactivatedUser(String Email) {
        TempUserModel user = null;
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
            rs = DataAccess.getData(Queries.SELECT_UNACTUSER_FRM_EMAIL, dataList, connection, ps, rs);
            if (rs.next()) {
                user = new TempUserModel();
                user.setName(rs.getString(1));
                user.setEmail(rs.getString(2));
                user.setType(rs.getString(3));
                user.setNumPostedStudies(rs.getInt(4));
                user.setNumParticipation(rs.getInt(5));
                user.setNumCoins(rs.getInt(6));
                user.setPassWord(rs.getString(7));
            }
        } catch (SQLException e) {
            Logger.getLogger(userDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if(rs!=null)
                DBUtil.closeResultSet(rs);
            if(ps!=null)
                DBUtil.closePreparedStatement(ps);
            if(connection!=null)
                pool.freeConnection(connection);
        }
        return user;
    }

}
