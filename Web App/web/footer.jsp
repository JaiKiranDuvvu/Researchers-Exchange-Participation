<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Section to display description --%>
<section class="copyright">
    <h2 class="hide">Footer</h2>    &copy; Researchers Exchange Participations
    <c:forEach var="c" items="${cookie}">
            <c:if test="${c.value.name!='JSESSIONID'}">
                <tr>
                    <td><c:out value="${c.value.name}"></c:out></td>
                    <td><c:out value="${c.value.value}"></c:out></td>
                </tr>
            </c:if>
    </c:forEach>       
</section>
</div>
</div>
<script type="text/javascript" src="js/main.js"></script>
</body>
</html>

