<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to display items in List --%>
<c:if test="${theUser != null}">
<nav id="menu">
    <ul>
        <li>Coins (<span class="count"><c:out value="${theUser.getNumCoins()}"></c:out></span>) </li>
        <li>Participants (<span class="count"><c:out value="${theUser.getNumPostedStudies()}"></c:out></span>) </li>
        <li>Participation (<span class="count"><c:out value="${theUser.getNumParticipation()}"></c:out></span>) </li>
        <li><br></li>
        <li><a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>">Home</a></li>
        <li><a href="StudyController?action=participate&randId=<c:out value="${randId}"></c:out>">Participate</a></li>
        <li><a href="StudyController?action=studies&randId=<c:out value="${randId}"></c:out>">My Studies</a></li>
        <li><a href="UserController?action=mvTorecommend&randId=<c:out value="${randId}"></c:out>">Recommend</a></li>
        <li><a href="UserController?action=mvToContact&randId=<c:out value="${randId}"></c:out>">Contact</a></li>
    </ul>
</nav>            
</c:if>
<c:if test="${theAdmin !=null}">
    <nav id="menu">
    <ul>
        <li><a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>">Home</a></li>
        <li><a href="StudyController?action=approve&randId=<c:out value="${randId}"></c:out>">Reported Questions</a></li>
    </ul>
    </nav>
</c:if>
<%-- Code to Display Question--%>
<section class="question_section">
    <h3><span class="label label-default" >Question</span></h3>
    <%-- Img tag to display image--%>
    <img src="<c:out value="${study.getImageURL()}"></c:out>" class="img-responsive" height="250" width="250" alt="<c:out value="${study.getStudyName()}"></c:out>"/>

<%--Code to rating the Question --%>
    <p class="text-left"><c:out value="${study.getQuestion()}"></c:out></p>

        <form action="StudyController?action=answer&randId=<c:out value="${randId}"></c:out>" method="post">
            <input type="hidden" name="StudyCode" value="<c:out value="${study.getStudyCode()}"></c:out>">
            <input type="hidden" name="QuestionId" value="<c:out value="${study.getQuestionId()}"></c:out>" />
        <c:forEach var="answer" items="${study.getAnswers()}">
            <div class="radio">
                <input type="radio" name="choice" value="<c:out value="${answer}"></c:out>" required>
            <c:out value="${answer}"></c:out>
            </div>
        </c:forEach> 
    
        <%-- Code to submit the Rating  --%>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-4">
                <button type="submit"  class="btn btn-primary">Submit</button>
            </div>
        </div>
        <br/><br/><br/>   
        </form>
        
    
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>