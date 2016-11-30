/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.security;

/**
 *
 * @author Jai Kiran
 */

import com.assignment4.util.CommonUtility;

import datameer.com.google.common.cache.Cache;
import datameer.com.google.common.cache.CacheBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;

public class ValidateSaltFilter implements Filter  {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        // Assume its HTTP
        HttpServletRequest httpReq = (HttpServletRequest) request;
        
	        // Get the salt sent with the request
	        String salt = (String) httpReq.getParameter("randId");
	        // Validate that the salt is in the cache
	        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)
	            httpReq.getSession().getAttribute("csrfPreventionSaltCache");
	        
	        if(csrfPreventionSaltCache != null &&
	                salt == null &&
	                 (null!=request.getParameter("activationtoken")|| null!=request.getParameter("crypt"))){
	        	
	                 csrfPreventionSaltCache = CacheBuilder.newBuilder()
	                     .maximumSize(5000)
	                     .expireAfterWrite(20, TimeUnit.MINUTES)
	                     .build();

	                 httpReq.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
	            

	             // Generate the salt and store it in the users cache
	              salt = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());
	             csrfPreventionSaltCache.put(salt, Boolean.TRUE);

	             // Add the salt to the current request so it can be used
	             // by the page rendered in this request
	             httpReq.setAttribute("randId", salt);
	        }
	        
	        if (csrfPreventionSaltCache != null &&
	                salt != null &&
	                csrfPreventionSaltCache.getIfPresent(salt) != null){
	
	            // If the salt is in the cache, we move on
	            chain.doFilter(request, response);
	        }else {
	            // Otherwise we throw an exception aborting the request flow
	            throw new ServletException("Potential CSRF detected!! Inform a scary sysadmin ASAP.");
	        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}