<%-- 
    Document   : verify
    Created on : Mar 2, 2021, 7:23:10 AM
    Author     : macbook
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Code</title>
    </head>
    <body>
        <h1>Enter code</h1>
        <form action="VerifyController" method="POST">
            <input type="text" name="txtVerifyCode" placeholder="Enter code">
            <input type="submit" value="Submit">
        </form>
        <h1 style="color:red"> ${requestScope.ERROR} </h1>
        <c:url var="resend" value="ResendEmailController">
        </c:url>
        <div class="login-register">
            <a href="${resend}">Resend code</a>
        </div>
        <c:url var="login" value="Login">
        </c:url>
        <div class="login-register">
            <a href="${login}">Login</a>
        </div>
    </body>
</html>
