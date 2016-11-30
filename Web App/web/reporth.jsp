<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to go back to Main page  --%>
<br>
<a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>" id="back_to_page">&laquo;Back to the Main Page</a>
<br>
<c:if test="${reports!=null}">
 <div class="table-responsive">
<table class="table" >
        <%--Column Names --%>
        <tr>
            <th>Report Date</th>
            <th>Report Question</th>		
            <th>Report Status</th>
            
        </tr>
        <c:forEach var="report" items="${reports}">
            <tr>
            <%-- First study details --%>
                <td><c:out value="${report.getReportDate()}"></c:out></td>
                <td><c:out value="${report.getReportedQuestion()}"></c:out></td>
                <td><c:out value="${report.getReportStatus()}"></c:out></td>
            </tr>
        </c:forEach>
        </table>
    </div>
   
</c:if>
 <%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>