
<%@include file="header.jsp" %>

<nav id="menu">
    <ul>
       
        <li><a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>">Home</a></li>
        <li><a href="StudyController?action=reportques&randId=<c:out value="${randId}"></c:out>">Reported Questions</a></li>
        
    </ul>
</nav>

<section class="main">
    <h3>How it Works</h3>
    <p>This site was built to help researchers conduct their user studies</p>
    <p>1 participation = 1 coin</p>
    <p><b>To participate,</b> go to "Participate" section and choose a study to complete</p>
    <p><b>To get participants,</b> submit your study here to start getting Participations. Inorder to do so, you must have enough coins in Your account</p>

</section>

<%@include file="footer.jsp" %>