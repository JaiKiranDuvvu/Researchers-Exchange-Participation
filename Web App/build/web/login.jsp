<%-- Include tag is used to import header page --%>
<%@include file="header.jsp" %>
<script src='https://www.google.com/recaptcha/api.js'></script>
<script type="text/javascript" src="js/aes.js"></script>
<script type="text/javascript" src="js/pad-nopadding-min.js"></script>
<%-- Section to input login details --%>
<br/>
    <%-- Code to create login form--%>
    <form id="login_form" class="col-md-8 col-md-offset-2 form-horizontal" action="UserController?action=login&randId=<c:out value="${randId}"></c:out>" method="post">
        <input type="hidden" name="action" value="login">
            <c:if test="${not empty msg}">
	            <div class="form-group"style="color:red" >
	       		 	<label class="col-sm-4 control-label" >*</label>
		        	<div class="col-sm-4">
		                    <c:out value="${msg}"></c:out>
		        	</div>
	       		</div>
            </c:if>
        	<div class="form-group">
       		 	<label class="col-sm-4 control-label" >Email Address *</label>
	        	<div class="col-sm-4">
	        		<input type="email"  id="email" class="form-control" name="email" required/>
	        	</div>
       		</div>
        	<div class="form-group">
	        	<label class="col-sm-4 control-label" >Password *</label>
	        	<div class="col-sm-4">
	        		<input class="form-control" id="password" type="password" name="password" required/>
	        	</div>
        	</div>
        	<div class="form-group" >
		            <div class="col-sm-10 col-sm-offset-4">
		                <div class="g-recaptcha" data-sitekey="6LfL8RwTAAAAAKFZMKT3qU8zM0mUOR2UTuPD3rYJ"></div>
		            </div>
                </div>
	        <div class="form-group" >
		            <div class=" col-sm-10 col-sm-offset-4">
		                <input id="login_button" type="submit" value="Log in" class="btn btn-primary" >
		            </div>
		</div>
		
   	 </form>
   	 
   	<div class="col-sm-6 col-md-offset-3" style="outline: 1px solid grey;" align="center">
	    <div class="col-sm-4" style="display: inline-block;">
	    <%-- Code to go to Sign up for a new account  --%>
	    <a href="UserController?action=create&randId=<c:out value="${randId}"></c:out>" id="sign_up_link">Sign up for a new account</a>
	    </div>
	    <div class="col-sm-3 col-sm-offset-4" style="display: inline-block;padding-left: 10px">
	    <%-- Code to go to Sign up for a new account  --%>
	    <a href="UserController?action=forgotPassword&randId=<c:out value="${randId}"></c:out>" id="sign_up_link">Forgot password?</a>
	    </div>
    </div>
<br/>
<br/>
<br/>

    <%-- Include tag is used to import footer page --%>
<%@include file="footer.jsp" %>