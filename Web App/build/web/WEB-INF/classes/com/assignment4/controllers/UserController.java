package com.assignment4.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.assignment4.business.Mail;
import com.assignment4.business.TempUserModel;
import com.assignment4.business.User;
import com.assignment4.dao.userDAO;
import com.assignment4.dao.userDAOImpl;
import com.assignment4.security.AESCrypt;
import com.assignment4.security.PasswordUtil;
import com.assignment4.util.CommonUtility;
import com.assignment4.util.EmailUtil;
import com.assignment4.security.googleRecaptcha;

import datameer.com.google.common.cache.Cache;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jai Kiran
 */
@WebServlet(urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "/home.jsp";
        String action = CommonUtility.checkNullString(request.getParameter("action"));
        if (action.equals("")) {
            Cookie cookie1 = new Cookie("Host", request.getServerName());
            cookie1.setPath("/");
            Cookie cookie2 = new Cookie("port", Integer.toString(request.getServerPort()));
            cookie2.setPath("/");
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            url = "/home.jsp";
        } else if (action.equals("login")) {
            String userName = CommonUtility.checkNullString(request.getParameter("email"));
            String password = CommonUtility.checkNullString(request.getParameter("password"));
            if (PasswordUtil.checkPasswordStrength(password)) {
                String gRecaptchaResponse = CommonUtility.checkNullString(request.getParameter("g-recaptcha-response"));
                boolean verify = googleRecaptcha.verify(gRecaptchaResponse);
                if (verify) {
                    User user = null;
                    userDAO udao = new userDAOImpl();
                    user = udao.valiadteLogin(userName, password);
                    if (user != null) {
                        String userType = CommonUtility.checkNullString(user.getType());
                        if (userType.equals("Participant")) {
                            session.setAttribute("theUser", user);
                            session.setAttribute("coins", user.getNumCoins());
                            session.setAttribute("NumParticipation", user.getNumParticipation());
                            url = "/main.jsp";
                        }
                        if (userType.equals("Admin")) {
                            session.setAttribute("theAdmin", user);
                            session.setAttribute("coins", user.getNumCoins());
                            session.setAttribute("NumParticipation", user.getNumParticipation());
                            url = "/admin.jsp";
                        }
                    } else {
                        request.setAttribute("msg", "Invalid User Name/Password");
                        url = "/login.jsp";
                    }
                } else {
                    request.setAttribute("msg", "You missed the Captcha");
                    url = "/login.jsp";
                }
            }else {
                    request.setAttribute("msg", "Invalid User Name/Password");
                    url = "/login.jsp";
            }
        } else if (action.equals("create")) {
            String Name = CommonUtility.checkNullString(request.getParameter("name"));
            String Email = CommonUtility.checkNullString(request.getParameter("email"));
            String password = CommonUtility.checkNullString(request.getParameter("password"));
            String confirmPassword = CommonUtility.checkNullString(request.getParameter("confirm_password"));
            if (PasswordUtil.checkPasswordStrength(password) && PasswordUtil.checkPasswordStrength(confirmPassword)) {
                String gRecaptchaResponse = CommonUtility.checkNullString(request.getParameter("g-recaptcha-response"));
                boolean verify = googleRecaptcha.verify(gRecaptchaResponse);
                if (verify) {
                    if (!Email.equals("") && password.equals(confirmPassword)) {
                        User user = new User();
                        user.setName(Name);
                        user.setEmail(Email);
                        user.setType("Participant");
                        user.setNumCoins(0);
                        user.setNumParticipation(0);
                        user.setNumPostedStudies(0);
                        userDAO udao = new userDAOImpl();
                        User checkUserExists = udao.getUser(Email);
                        if (checkUserExists == null) {
                           TempUserModel tempUser = udao.getUnactivatedUser(Email);
                           if(tempUser == null){
                        	String activationToken = udao.newUserActivation(user, password);
                            if (null != activationToken && !("").equalsIgnoreCase(activationToken)) {
                                String toEmail = CommonUtility.checkNullString(request.getParameter("email"));
                                String Message = CommonUtility.ACTIVATION_LINK+activationToken+"&email="+toEmail;
                                String fromName = user.getName();
                                String fromEmail = CommonUtility.MAIL_FROM_ADDRESS;
                                Mail mail = new Mail();
                                mail.setToName(user.getName());
                                mail.setToEmail(toEmail);
                                mail.setMessage(Message);
                                mail.setFromName(fromName);
                                mail.setFromEmail(fromEmail);
                                mail.setMailType("activate");
                                mail.setSubject("Activation Link " + fromName + " - Researchers Exchange Participation");
                                boolean status = EmailUtil.sendMessage(mail);
                                String msg = "";
                                if (status) {
                                    msg = "Please check your mail for an activation link. Please follow the link to access your account";
                                } else {
                                    msg = "Failed to send activation link! Please try after sometime";
                                }
                                request.setAttribute("msg", msg);
                                url = "/signup.jsp";
                            	
                            	
                            } else {
                                request.setAttribute("name", Name);
                                request.setAttribute("email", Email);
                                request.setAttribute("password", password);
                                request.setAttribute("confirmPassword", confirmPassword);
                                if (!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("")) {
                                    request.setAttribute("msg", "Error in Saving Data");
                                }
                                url = "/signup.jsp";
                            }
                            
	                        }else{
	                        	 request.setAttribute("name", Name);
	                             request.setAttribute("email", Email);
	                             request.setAttribute("password", password);
	                             request.setAttribute("confirmPassword", confirmPassword);
	                             if (!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("")) {
	                                 request.setAttribute("msg", "User already Registered");
	                             }
	                             url = "/signup.jsp";
	                        }
                        } else {
                            request.setAttribute("name", Name);
                            request.setAttribute("email", Email);
                            request.setAttribute("password", password);
                            request.setAttribute("confirmPassword", confirmPassword);
                            if (!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("")) {
                                request.setAttribute("msg", "User already Registered");
                            }
                            url = "/signup.jsp";
                        }
                    } else {
                        request.setAttribute("name", Name);
                        request.setAttribute("email", Email);
                        request.setAttribute("password", password);
                        request.setAttribute("confirmPassword", confirmPassword);
                        if (!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("")) {
                            request.setAttribute("msg", "Please fill all the details correctly");
                        }
                        url = "/signup.jsp";
                    }
                } else {
                    if (!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("")) {
                        request.setAttribute("msg", "You missed the Captcha");
                    }
                    url = "/signup.jsp";
                }
            }else{
                if (!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("")) {
                    request.setAttribute("msg", "Password Strength weak");
                }
                url = "/signup.jsp";
            }
        } else if (action.equals("activate")){ 
            String email = CommonUtility.checkNullString(request.getParameter("email"));
            String activationToken = CommonUtility.checkNullString(request.getParameter("activationtoken"));
        	
            if (!("").equalsIgnoreCase(email) && !("").equalsIgnoreCase(activationToken)){
            	userDAO udao = new userDAOImpl();
            	TempUserModel unActivatedUser = udao.getUnactivatedUser(email);
                        User user = new User();
                        user.setName(unActivatedUser.getName());
                        user.setEmail(unActivatedUser.getEmail());
                        user.setType("Participant");
                        user.setNumCoins(0);
                        user.setNumParticipation(0);
                        user.setNumPostedStudies(0);
                        int result = udao.addUserRecord(user, unActivatedUser.getPassWord());
                        if (result > 0) {
                        	udao.deleteActivatedUser(email);
                        	session.setAttribute("theUser", user);
                            session.setAttribute("coins", user.getNumCoins());
                            session.setAttribute("NumParticipation", user.getNumParticipation());
                            request.setAttribute("msg", "Please login with your credentials");
                        } else {
                            request.setAttribute("msg", "Error in Saving Data");
                            url = "/signup.jsp";
                        }
               
                    url = "/login.jsp";
            }else{
                request.setAttribute("msg", "Please sign up again");
                url = "/signup.jsp";
            }
        	
        	
        }else if(action.equals("reset")){
        	//get email
        	String email = CommonUtility.checkNullString(request.getParameter("email"));
        	userDAO udao = new userDAOImpl();
        	//check if the email exists in the system
        	User user = udao.getUser(email);
        	if(null!=user){
        		//Generate a dummy password using the time stamp and aes encryption
        		String cryptTime = AESCrypt.encrypt(udao.generateRandomToken());
        		String actionLink = CommonUtility.RESET_LINK+cryptTime;
        		String toEmail = email;
                String Message = actionLink+"&email="+toEmail;
                String fromName = "REP Admin Team";
                String fromEmail = CommonUtility.MAIL_FROM_ADDRESS;
                Mail mail = new Mail();
                mail.setToName(user.getName());
                mail.setToEmail(toEmail);
                mail.setMessage(Message);
                mail.setFromName(fromName);
                mail.setFromEmail(fromEmail);
                mail.setMailType("reset");
                mail.setSubject("Password reset Link " + fromName + " - Researchers Exchange Participation");
                boolean status = EmailUtil.sendMessage(mail);
                String msg = "Check mail! Please follow the link to reset the password";
                if (status) {
                    msg = "Please check your mail for an reset link. Please follow the link to access your account";
                } else {
                    msg = "Failed to send reset link! Please try after sometime";
                }
                request.setAttribute("msg", msg);
                url = "/signup.jsp";
        	}else{
        		request.setAttribute("msg", "The given email was not a registered email! sign up if you are a new user");
        		url="/signup.jsp";
        	}
        	
        }else if(action.equals("resetpassword")){
        	String email = CommonUtility.checkNullString(request.getParameter("email"));
        	String cryptText = CommonUtility.checkNullString(request.getParameter("crypt"));
        	try{
	        	if((null!=email&&null!=cryptText)&&(!("").equalsIgnoreCase(email)&&!("").equalsIgnoreCase(cryptText))){
							request.setAttribute("email", email);
							url="/changepassword.jsp";
	        	}else{
	        		request.setAttribute("msg", "The link seems to be invalid. Please try again");
	                url = "/login.jsp";
	        	}
	        }catch(Exception e){
				request.setAttribute("msg", "The link seems to be invalid. Please try again");
                url = "/login.jsp";
				e.printStackTrace();
			}
        	
        }else if(action.equals("updatepassword")){
        	String email = CommonUtility.checkNullString(request.getParameter("email"));
        	String password = CommonUtility.checkNullString(request.getParameter("password"));
            String confirmPassword = CommonUtility.checkNullString(request.getParameter("confirm_password"));
            if (PasswordUtil.checkPasswordStrength(password) && PasswordUtil.checkPasswordStrength(confirmPassword)) {
            	if (!email.equals("") && password.equals(confirmPassword)) {
            		userDAO udao = new userDAOImpl();
            		int result = udao.updatePassword(email, password);
            		if(result>0){
            			request.setAttribute("email", email);
                    	request.setAttribute("msg", "Password updated");
        				url="/login.jsp";
            		}else{
            			request.setAttribute("email", email);
                    	request.setAttribute("msg", "Something went wrong. Please try again");
        				url="/changepassword.jsp";
            		}
            	}else{
            		request.setAttribute("email", email);
                	request.setAttribute("msg", "Password doesnt match. Try again");
    				url="/changepassword.jsp";
            	}
            }else{
            	request.setAttribute("email", email);
            	request.setAttribute("msg", "Weak password . Try a new one");
				url="/changepassword.jsp";
            }
            
        }else if (action.equals("how")) {
            if (session.getAttribute("theUser") != null) {
                url = "/main.jsp";
            } else if (session.getAttribute("theAdmin") != null) {
                url = "/admin.jsp";
            } else {
                url = "/how.jsp";
            }
        } else if (action.equals("about")) {
            if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
                url = "/aboutl.jsp";
            } else {
                url = "/about.jsp";
            }
        } else if (action.equals("home")) {
            if (session.getAttribute("theUser") != null) {
                url = "/main.jsp";
            } else if (session.getAttribute("theAdmin") != null) {
                url = "/admin.jsp";
            } else {
                url = "/home.jsp";
            }
        } else if (action.equals("main")) {
            if (session.getAttribute("theUser") != null) {
                url = "/main.jsp";
            } else if (session.getAttribute("theAdmin") != null) {
                url = "/admin.jsp";
            } else {
                url = "/login.jsp";
            }
        } else if(action.equals("forgotPassword")){
        	url = "/forgotpassword.jsp";
        }
        else if (action.equals("logout")) {
            if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
                Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) request.getSession().getAttribute("csrfPreventionSaltCache");
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);

            }
            url = "/index.jsp";
        } else if (action.equals("mvToContact")) {
            if (session.getAttribute("theUser") != null) {
                url = "/contact.jsp";
            } else {
                url = "/login.jsp";
            }
        } else if (action.equals("contact")) {
            if (session.getAttribute("theUser") != null) {
                String toName = CommonUtility.checkNullString(request.getParameter("study_name"));
                String toEmail = CommonUtility.checkNullString(request.getParameter("email"));
                String Message = CommonUtility.checkNullString(request.getParameter("message"));
                String fromName = ((User) session.getAttribute("theUser")).getName();
                String fromEmail = ((User) session.getAttribute("theUser")).getEmail();
                Mail mail = new Mail();
                mail.setToName(toName);
                mail.setToEmail(toEmail);
                mail.setMessage(Message);
                mail.setFromName(fromName);
                mail.setFromEmail(fromEmail);
                mail.setMailType(action);
                mail.setSubject("Contact Request from " + fromName + " - Researchers Exchange Participation");
                boolean status = EmailUtil.sendMessage(mail);
                String msg = "";
                if (status) {
                    msg = "Message Sent. . .";
                } else {
                    msg = "Message Sending Failed. . .";
                }
                request.setAttribute("msg", msg);
                url = "/confirmc.jsp";
            } else {
                url = "/login.jsp";
            }
        } else if (action.equals("mvTorecommend")) {
            if (session.getAttribute("theUser") != null) {
                url = "/recommend.jsp";
            } else {
                url = "/login.jsp";
            }
        } else if (action.equals("recommendc")) {
            if (session.getAttribute("theUser") != null) {
                String toName = CommonUtility.checkNullString(request.getParameter("study_name"));
                String toEmail = CommonUtility.checkNullString(request.getParameter("friend_email"));
                //String friend_email=CommonUtility.checkNullString(request.getParameter("friend_email"));
                String Message = CommonUtility.checkNullString(request.getParameter("message"));
                String fromName = ((User) session.getAttribute("theUser")).getName();
                String fromEmail = ((User) session.getAttribute("theUser")).getEmail();
                Mail mail = new Mail();
                mail.setToName(toName);
                mail.setToEmail(toEmail);
                mail.setMessage(Message);
                mail.setFromName(fromName);
                mail.setFromEmail(fromEmail);
                mail.setMailType(action);
                mail.setSubject("Recommendation Recieved from " + fromName + " - Researchers Exchange Participation");
                boolean status = EmailUtil.sendMessage(mail);
                String msg = "";
                if (status) {
                    msg = "Recommendation Sent. . .";
                } else {
                    msg = "Recommendation Sent Failed. . .";
                }
                request.setAttribute("msg", msg);
                url = "/confirmr.jsp";
            } else {
                url = "/login.jsp";
            }
        } else {
            url = "/home.jsp";
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

}
