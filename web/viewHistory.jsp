<%-- 
    Document   : viewHistory
    Created on : Jan 19, 2021, 6:12:25 PM
    Author     : macbook
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <title>View History</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand">Rental Car</a>
                </div>

                <ul class="nav navbar-nav navbar-right">
                    <form class="form-inline my-2 my-lg-0">
                        <c:url var="logout" value="LogoutController">
                        </c:url>
                        <a class="btn btn-danger btn-sm ml-2" href="${logout}">  <i class="fa fa-sign-out"></i> ${sessionScope.LOGIN_USER.name} - Logout</a>
                    </form>
                </ul>
            </div>
        </nav>
        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading">Rental Car</h1>
            </div>
        </section>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <h1 style="text-align: center; color:green"> ${requestScope.MESSAGE} </h1>
        <c:if test="${requestScope.MESSAGE == null}">
            <h1 style="text-align: center; color:red"> ${requestScope.ERROR} </h1>
        </c:if>
        <table class="table table-striped">
            <tr>
            <form action="SearchHistoryController" method="POST">
                <td class="text-right">
                    Name <input type="text" name="txtCarName" value="${param.txtCarName}">
                </td>
                <td class="text-right"> 
                    Date<input type="date" name="txtOrderDate" value="${param.txtOrderDate}"></td>
                <td>
                </td>
                <td >
                    <input type="submit" value="SearchOrder">
                </td>
                <td>
                </td>
                </tbody>
            </form>
        </tr>
    </table>
    <c:set var="list" value="${requestScope.LIST_HISTORY}"></c:set>
    <c:if test="${list == null && empty list}">
        <h1 style="text-align: center; color: red">Nothing</h1> 
    </c:if>
    <c:if test="${list != null && not empty list}">
        <table class="table table-striped">
            <tr>
                <th scope="col"></th>
                <th scope="col">Order date</th>
                <th scope="col">Total money</th>
                <th scope="col">Address</th>
                <th scope="col">Phone</th>
                <th scope="col">Code</th>
                <th scope="col">Discount</th>
                <th scope="col">Delete</th>
                <th scope="col">View detail</th>
            </tr>
            <tr>
                <c:forEach var="dto" items="${list}">
                    <td> </td>
                    <td>${dto.createDate}</td>
                    <td>${dto.total}</td>
                    <td>${dto.address}</td>
                    <td>${dto.phone}</td>
                    <td>${dto.code}</td>
                    <td>${dto.percentOfDiscount}
                        <c:if test="${dto.percentOfDiscount != null}">
                            %
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${dto.orderDetailID != 'N/A'}">
                            <c:url var="delete" value="DeleteHistoryController">
                                <c:param name="txtOrderID" value="${dto.orderID}"></c:param>
                            </c:url>
                            <a href="${delete}">
                                <button class="btn btn-sm btn-danger" onclick="return confirm('Do you want to delete?')"> <i class="fa fa-trash"></i> </button>
                            </a>
                        </c:if>
                    </td>
                    <td>
                        <c:url var="view" value="ViewHistoryDetailController">
                            <c:param name="txtOrderID" value="${dto.orderID}"></c:param>
                        </c:url>
                        <a href="${view}">
                            <i class="fa fa-search"></i>
                        </a>
                    </td>
                    </tbody>
                </c:forEach>
        </table>
    </c:if>
    <div class="col-sm-12  col-md-6">
        <form action="viewCart" method="POST">
            <button type ="sumbit" value="Back to Cart" class="btn btn-block btn-light">Back to cart</button>
        </form>
    </div>
</body>
</html>
