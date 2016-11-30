<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to go back to Main page  --%>
<br>
<h3><span id="studies">Reported Questions</span></h3><br/>
<a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>" id="back_to_page">&laquo;Back to the Main Page</a><br/>
<br/><br/><br/>

  <section class="col-sm-10 col-sm-offset-1" style="margin-bottom:100px">
  <c:if test="${not empty msg}">
      <div class="col-sm-8 col-sm-offset-2" style="text-align:center"><c:out value="${msg}"></c:out></div>
  </c:if> 
  <c:if test="${empty reports}">
      <div class="col-sm-8 col-sm-offset-2" style="text-align:center">No Data Found</div>
  </c:if>    
  <c:if test="${not empty reports}">    
  <div class="table-responsive">
  <table class="table" >
        <%--Column Names --%>
        <tr>
            <th>Question</th>
            <th>No. of Participants Reported</th>
            <th>Action</th>		
        </tr>
        <c:forEach var="report" items="${reports}">
        <tr>
            <%-- First study details --%>
            <td><c:out value="${report.getReportedQuestion()}"></c:out></td>
            <td><c:out value="${report.getNumberOfParticipants()}"></c:out></td>
            <td>
            <form method="post">
            <input type="hidden" name="StudyCode" value="<c:out value="${report.getStudyCode()}"></c:out>"/>
            <input type="hidden" name="QuestionId" value="<c:out value="${report.getQuestionId()}"></c:out>"/> 
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=approve&randId=<c:out value="${randId}"></c:out>"  value="Approve">
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=disapprove&randId=<c:out value="${randId}"></c:out>"  value="Dispprove">
            </form>
           </td>
            

        </tr>
        </c:forEach>
        </table>
        </div>
        </c:if>
  </section>
  
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>