<%-- Include tag is used to import header page --%>
<%@include file="header.jsp" %>
<script type="text/javascript" src="js/aes.js"></script>
<script type="text/javascript" src="js/pad-nopadding-min.js"></script>
<%--Code to signup form --%>
<section>
<br/><br/><br/>
        <form id="change_password_form" class="col-sm-10 col-sm-offset-1 form-horizontal" action="UserController?action=updatepassword&randId=<c:out value="${randId}"></c:out>" method="post">
        
            <input type="hidden" name="action" value="updatepassword" />
            <c:if test="${not empty msg}">
                <div class="form-group"style="color:red" >
       		 	<label class="col-sm-4 control-label" >*</label>
        	<div class="col-sm-4">
                    <c:out value="${msg}"></c:out>
        	</div>
        	<div> 
        		<a href="home.jsp">Back to home page</a>
        	</div>
       		</div>
            </c:if>
            <div class="form-group">
            <label class="col-sm-4 control-label">Email *</label>
            <div class="col-sm-4">
                <input type="email" class="form-control" name="email" id="email" required value="<c:out value="${email}"></c:out>" readonly/>
            </div>
            </div>
            <div class="form-group">
            <label class="col-sm-4 control-label">Password *</label>
            <div class="col-sm-4">
            <input type="password" class="form-control" name="password" id="password" required/>
            </div>
            </div>
            <div class="form-group">
            <label class="col-sm-4 control-label">Confirm Password *</label>
            <div class="col-sm-4">
            <input type="password" class="form-control" name="confirm_password" id="cpassword" required />
            </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">&nbsp;</label>
	            <div class="col-sm-4">
	            <input type="submit" id="test_button" value="Change Password" class="btn btn-primary">
	            </div>
	            </div>
           
            <br><br/><br/>
        </form> 
        </section>
  
<%-- Include tag is used to import footer page --%>
<%@include file="footer.jsp" %>