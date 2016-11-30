/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.util;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jai Kiran
 */
public class DataAccess {
    
    public static ResultSet getData(String query,ArrayList typeList,Connection connection,PreparedStatement ps,ResultSet rs){
        ArrayList tempList=null;
        try {
            ps = connection.prepareStatement(query);
            String type="";String value="";
            for(int i=0;i<typeList.size();i++){
                tempList=(ArrayList)typeList.get(i);
                type=(String)tempList.get(0);
                value=(String)tempList.get(1);
                if(type.equals(CommonUtility.PARAM_INTEGER))
                    ps.setInt(i+1,Integer.parseInt(value));
                else if(type.equals(CommonUtility.PARAM_STRING))
                    ps.setString(i+1,value);
                else if(type.equals(CommonUtility.PARAM_DOUBLE))
                    ps.setDouble(i+1,Double.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_DATE))
                   ps.setDate(i+1,java.sql.Date.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_LONG))
                   ps.setLong(i+1,Long.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_FLOAT))
                   ps.setFloat(i+1,Float.valueOf(value));
                else
                    throw new Exception("Unsupported Parameter type");
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return rs;
    }
    
    public static int updateData(String query,ArrayList typeList,Connection connection,PreparedStatement ps){
        ArrayList tempList=null;
        int count=-1;
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query);
            String type="";String value="";
            for(int i=0;i<typeList.size();i++){
                tempList=(ArrayList)typeList.get(i);
                type=(String)tempList.get(0);
                if(!type.equals(CommonUtility.PARAM_BLOB))
                    if(tempList.get(1)!=null)
                        value=(String)tempList.get(1);
                    else
                        value=null;
                if(type.equals(CommonUtility.PARAM_INTEGER))
                    ps.setInt(i+1,Integer.parseInt(value));
                else if(type.equals(CommonUtility.PARAM_STRING))
                    ps.setString(i+1,value);
                else if(type.equals(CommonUtility.PARAM_DOUBLE))
                    ps.setDouble(i+1,Double.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_DATE))
                   ps.setDate(i+1,java.sql.Date.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_LONG))
                   ps.setLong(i+1,Long.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_FLOAT))
                   ps.setFloat(i+1,Float.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_BLOB))
                   ps.setBlob(i+1,(InputStream)tempList.get(1));
                else
                    throw new Exception("Unsupported Parameter type");
            }
            count = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            try {
                if(count==1)
                    connection.commit();
                else
                    connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    
    public static HashMap<String,Integer> updateDataHandleCommit(String query,ArrayList typeList,Connection connection,PreparedStatement ps){
        ArrayList tempList=null;
        int count=-1;
        HashMap<String,Integer> returnMap=new HashMap<String,Integer>();
        try {
            //connection.setAutoCommit(false);
            ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            String type="";String value="";
            for(int i=0;i<typeList.size();i++){
                tempList=(ArrayList)typeList.get(i);
                type=(String)tempList.get(0);
                if(!type.equals(CommonUtility.PARAM_BLOB))
                    if(tempList.get(1)!=null)
                        value=(String)tempList.get(1);
                    else
                        value=null;
                if(type.equals(CommonUtility.PARAM_INTEGER))
                    ps.setInt(i+1,Integer.parseInt(value));
                else if(type.equals(CommonUtility.PARAM_STRING))
                    ps.setString(i+1,value);
                else if(type.equals(CommonUtility.PARAM_DOUBLE))
                    ps.setDouble(i+1,Double.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_DATE))
                   ps.setDate(i+1,java.sql.Date.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_LONG))
                   ps.setLong(i+1,Long.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_FLOAT))
                   ps.setFloat(i+1,Float.valueOf(value));
                else if(type.equals(CommonUtility.PARAM_BLOB))
                   ps.setBlob(i+1,(InputStream)tempList.get(1));
                else
                    throw new Exception("Unsupported Parameter type");
            }
            count = ps.executeUpdate();
            returnMap.put("count", count);
            int key=-1;
            if(count==1){
                ResultSet genKeys=ps.getGeneratedKeys();
                if(genKeys.next()){
                    key=genKeys.getInt(1);
                }
            }
            returnMap.put("key",key);
        } catch (SQLException e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnMap;
    }
}
