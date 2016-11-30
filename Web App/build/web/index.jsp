<%-- 
    Document   : index
    Created on : Apr 9, 2016, 12:58:37 AM
    Author     : Jai Kiran
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:redirect url="/UserController">
     <c:param name="randId"
            value="${randId}"></c:param>
</c:redirect>
