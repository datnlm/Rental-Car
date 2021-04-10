<%-- 
    Document   : viewDetailHistory
    Created on : Jan 19, 2021, 6:50:36 PM
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
        <c:set var="list" value="${requestScope.LIST_HISTORY_DETAIL}"></c:set>
        <c:if test="${list == null && empty list}">
            <h1 style="text-align: center; color: red">Nothing</h1> 
        </c:if>
        <c:if test="${list != null && not empty list}">
            <table class="table table-striped">
                <tr>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col">Car name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Rental Date</th>
                    <th scope="col">Return Date</th>
                    <th scope="col">FeedBack</th>
                </tr>
                <tr>
                    <c:forEach var="dto" items="${list}">
                    <form action="FeedBackController" method="POST">
                        <td></td>
                        <td></td>
                        <td>${dto.carName}</td>
                        <td>${dto.price}</td>
                        <td>${dto.quantity}</td>
                        <td>${dto.rentalDate}</td>
                        <td>${dto.returnDate}</td>   
                        <c:if test="${dto.detailID != 'N/A'}">
                            <td>
                                <input type="hidden" name="txtDetailID" value="${dto.detailID}">
                                <input type="hidden" name="txtCarID" value="${dto.carID}">
                                <input type="number" min="1" max="10" name="txtFeedBack" value="${param.txtFeedBack}"><button type="submit" name="btnAction" value="verifyDiscount" class="btn btn-sm btn-success"><i>Feed Back</i></button>
                            </td>
                        </c:if>
                        <c:if test="${dto.detailID == 'N/A'}">
                            <td>It's not time to feedback yet, or you already finished feedback</td>
                        </c:if>
                    </form>
                </tbody>
            </c:forEach>
        </table>
    </c:if>
    <div class="col-sm-12  col-md-6">
        <form action="ViewHistoryController" method="POST">
            <button type ="sumbit" class="btn btn-block btn-light">Back to orders</button>
        </form>
    </div>
</body>
</html>
