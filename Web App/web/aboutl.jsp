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
<%-- Section tag is used to write description  --%>
<section class="main">
    <h3>About us</h3>
    <p>Researchers Exchange Participations is a platform for researchers 
        to exchange participations</p>
    <p>The aim of this platform is to encourage researchers participate in each others
        user studies. Moreover, recruiting serious participants has been always a burden on
        a researcher's shoulder, thus, this platform gives researchers the opportunity
        to do that effectively and in a suitable time.</p>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>