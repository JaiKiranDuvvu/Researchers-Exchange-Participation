<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to go back to Main page  --%>
<br>
<a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>" id="back_to_page">&laquo;Back to the Main Page</a>
<%-- Section tag is used to display Message Sent   --%>
<section>
    <h3 class="text-center"><c:out value="${msg}"></c:out></h3>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>