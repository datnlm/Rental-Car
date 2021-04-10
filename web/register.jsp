<%-- 
    Document   : register
    Created on : Jan 31, 2021, 1:21:40 AM
    Author     : macbook
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Register Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    </head>

    <body>
        <c:set var="error" value="${requestScope.ERROR}"></c:set>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">Register</div>
                            <div class="card-body">
                                <form class="form-horizontal" action="RegisterController" method="POST">
                                    <div class="form-group">
                                        <label for="email" class="cols-sm-2 control-label">Your email</label>
                                        <div class="cols-sm-10">
                                            <div class="input-group">
                                                <input type="email" class="form-control" name="txtEmail" id="email" placeholder="Enter your Email"  value="${param.txtEmail}"/>
                                        </div>
                                    </div>
                                    <p class="text-center">   
                                        <font color="red">${error.getUserIDError()}</font>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="cols-sm-2 control-label">Your name</label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="txtName" id="name" placeholder="Enter your Name" value="${param.txtName}"/>
                                        </div>
                                    </div>
                                    <p class="text-center">   
                                        <font color="red">${error.getNameError()}</font>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label class="cols-sm-2 control-label">Your phone</label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="txtPhone" placeholder="Enter your phone" value="${param.txtPhone}"/>
                                        </div>
                                    </div>
                                    <p class="text-center">   
                                        <font color="red">${error.getPhoneError()}</font>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label class="cols-sm-2 control-label">Your address</label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="txtAddress" placeholder="Enter your address" value="${param.txtAddress}"/>
                                        </div>
                                    </div>
                                    <p class="text-center">   
                                        <font color="red">${error.getAdrressError()}</font>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="cols-sm-2 control-label">Password</label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <input type="password" class="form-control" name="txtPassword" id="password" placeholder="Enter your Password"/>
                                        </div>
                                    </div>
                                    <p class="text-center">   
                                        <font color="red">${error.getPasswordError()}</font>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label for="confirm" class="cols-sm-2 control-label">Confirm Password</label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <input type="password" class="form-control" name="txtConfirm" id="confirm" placeholder="Confirm your Password"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <button type="submit" class="btn btn-primary btn-lg btn-block login-button">Register</button>
                                </div>
                            </form>
                            <c:url var="login" value="Login">
                            </c:url>
                            <div class="login-register">
                                <a href="${login}">Login</a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>