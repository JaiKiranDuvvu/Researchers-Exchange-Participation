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
        <li><a href="StudyController?action=reportques&randId=<c:out value="${randId}"></c:out>">Reported Questions</a></li>
    </ul>
    </nav>
</c:if>
<%-- Section tag is used to write description  --%>
<section class="main">
    <h3>How it Works</h3>
    <p>This site was built to help researchers conduct their user studies</p>
    <p>1 participation = 1 coin</p>
    <p><b>To participate,</b> go to "Participate" section and choose a study to complete</p>
    <p><b>To get participants,</b> submit your study here to start getting Participations. Inorder to do so, you must have enough coins in Your account</p>

</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>