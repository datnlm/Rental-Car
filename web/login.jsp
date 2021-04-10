<%-- 
    Document   : login
    Created on : Nov 3, 2020, 11:55:18 PM
    Author     : macbook
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/style.css">
        <!------ Include the above in your HEAD tag ---------->
        <script src='https://www.google.com/recaptcha/api.js'></script>
        <script src='https://www.google.com/recaptcha/api.js?hl=vi'></script>
        <title>Login Page</title>
    </head>
    <body>
    <body
        <c:set var="error" value="${requestScope.ERROR}"></c:set>
            <div class="container">
                <div class="row">
                    <div class="col-md-5 mx-auto">
                        <div id="first">
                            <div class="myform form ">
                                <form action="LoginController" method="POST">
                                    <div class="logo mb-3">
                                        <div class="col-md-12 text-center">
                                            <h1>Login</h1>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>User ID</label>
                                        <input type="text" name="txtUserID"  class="form-control" placeholder="Enter account" value="${param.txtUserID}">
                                    <font color="red"> ${error.getUserIDError()}</font>

                                </div>
                                <div class="form-group">
                                    <label>Password</label>
                                    <input type="password" name="txtPassword" id="password"  class="form-control" placeholder="Enter Password">
                                    <font color="red">  ${error.getPasswordError()}</font>
                                </div>
                                <div class="g-recaptcha" data-sitekey="6LePNN8ZAAAAAP5F2bzRhFGDjWTmfr3UEEnFYqFV"></div>
                                <div class="col-md-12 text-center ">
                                    <button type="submit" class=" btn btn-block mybtn btn-primary tx-tfm" name="btnAction" value="Login">Login</button>
                                </div>
                                <div class="col-md-12 ">
                                    <div class="login-or">
                                        <hr class="hr-or">
                                        <span class="span-or">or</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <c:url var="SignUp" value="Register">
                                    </c:url>
                                    <p class="text-center">Don't have account? 
                                        <a href="${SignUp}" id="signup">Sign up here</a>
                                    </p>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>   
    </body>
</html>
