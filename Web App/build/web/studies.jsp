<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to display Page Name --%>
<h3 id="page_name">My Studies</h3>
 <%-- Code to add new study   --%>
<h3 id="add_new_study"><a href="StudyController?action=newStudy&randId=<c:out value="${randId}"></c:out>" >Add a new study</a></h3>
 <%-- Code to go Back to the Main Page  --%>
<a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>" id="back_to_page">&laquo;Back to the Main Page</a>
<%-- Section to display studies details --%> 
<%-- Clicking on Start, Stop to Participate in that study and  Edit button to display edit page and edit study details in it--%>
<section style="margin-bottom: 100px;">
<c:if test="${studies !=null}">
<div class="table-responsive">
    <table class="table" >
        <tr>
            <th>Study Name</th>
            <th>Requested Participants</th>     
            <th>Participations</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        <c:forEach var="study" items="${studies}">
        <tr>
            <td><c:out value="${study.getStudyName()}"></c:out></td>
            <td><c:out value="${study.getRequestedparticipants()}"></c:out></td>
            <td><c:out value="${study.getNumofpartipants()}"></c:out></td>
            <td>
                <form action="StudyController?StudyCode=<c:out value="${study.getStudyCode()}"></c:out>&randId=<c:out value="${randId}"></c:out>" method="post">
                  <button type="submit" class="btn btn-primary">
                  <c:if test="${study.getStatus()=='start'}">
                    stop
                  </c:if>
                  <c:if test="${study.getStatus()=='stop' }">
                    start
                  </c:if>
                  </button>
                  <input type="hidden" name="action" value="<c:if test="${study.getStatus()=='start'}">stop</c:if><c:if test="${study.getStatus()=='stop' }">start</c:if>" />  
                </form>
            </td>
            <td>
                <form action="StudyController?action=edit&StudyCode=<c:out value="${study.getStudyCode()}"></c:out>&randId=<c:out value="${randId}"></c:out>" method="post">
                  <button type="submit" class="btn btn-primary">Edit</button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>
</c:if>
<c:if test="${studies == null}">
    <div style="margin:0px auto;text-align:center">No Study Records Found..</div>
</c:if>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>