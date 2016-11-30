<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- Set the viewport so this responsive site displays correctly on mobile devices -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%-- title of the Page--%>
        <title>Researchers Exchange Participations</title>
        <%-- importing CSS stylesheet --%>
        <link rel="stylesheet" href="styles/main.css">
        <!-- BootStrap -->
        <link rel="stylesheet" href="styles/bootstrap.min.css">
        <script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
    </head>
    <body>
        <div id="overlay"><span class="msgOverlay"></span></div> 
        <div class="container-fluid page-wrap">
        <div class="row">
        <%-- Code to specify Header section of the page--%>
        <div id="header">
            <nav id="header_menu">
                <ul  class="left" >
                    <li><a href="UserController?action=main&randId=<c:out value="${randId}"></c:out>" style="color: #009933;padding: 0;">Researchers Exchange Participations</a></li>
                </ul>
                <ul class="right">
                    <c:if test="${theUser == null && theAdmin==null}">
                        <li><a href="UserController?action=about&randId=<c:out value="${randId}"></c:out>">About Us</a></li>
                        <li><a href="UserController?action=how&randId=<c:out value="${randId}"></c:out>">How it Works</a></li>
                        <li><a href="UserController?action=main&randId=<c:out value="${randId}"></c:out>">Login</a></li>
                        </c:if>
                        <c:if test="${theUser !=null}">
                        <li><a href="UserController?action=about&randId=<c:out value="${randId}"></c:out>">About Us</a></li>
                        <li><a href="UserController?action=how&randId=<c:out value="${randId}"></c:out>">How it Works</a></li>
                        <li>Hello,&nbsp;<c:out value="${theUser.getName()}"></c:out></li>
                        <li><a href="UserController?action=logout&randId=<c:out value="${randId}"></c:out>">Logout</a></li>
                        </c:if>
                        <c:if test="${theAdmin !=null}">
                        <li><a href="UserController?action=about&randId=<c:out value="${randId}"></c:out>">About Us</a></li>
                        <li><a href="UserController?action=how&randId=<c:out value="${randId}"></c:out>">How it Works</a></li>
                        <li>Hello,&nbsp;<c:out value="${theAdmin.getName()}"></c:out></li>
                        <li><a href="UserController?action=logout&randId=<c:out value="${randId}"></c:out>">Logout</a></li>
                        </c:if>
                </ul>

            </nav>



        </div>

