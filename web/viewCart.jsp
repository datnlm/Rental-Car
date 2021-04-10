<%-- 
    Document   : viewCart
    Created on : Jan 13, 2021, 9:50:44 AM
    Author     : macbook
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/styleViewCart.css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    </head>
    <body>

        <nav class="navbar navbar-expand-md navbar-dark bg-dark">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a style="color:white"class="navbar-brand">Rental Car</a>
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <form class="form-inline my-2 my-lg-0">
                        <c:if test="${sessionScope.LOGIN_USER != null}">
                            <c:url var="logout" value="LogoutController">
                            </c:url>
                            <a class="btn btn-danger btn-sm ml-3" href="${logout}">  <i class="fa fa-sign-out"></i>${sessionScope.LOGIN_USER.name} - Logout</a>
                        </c:if>
                    </form>
                </ul>
            </div>
        </nav>

        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading">Buy Confirmation</h1>
            </div>
        </section>
        <h1 style="text-align: center; color:green"> ${requestScope.MESSAGE} </h1>
        <c:if test="${requestScope.MESSAGE == null}">
            <h1 style="text-align: center; color:red"> ${requestScope.ERROR} </h1>
        </c:if>
        <c:set var="cart" value="${sessionScope.CART}"></c:set>       
        <c:if test="${cart != null && not empty cart}">
            <c:if test="${cart.getCart() != null && not empty cart.getCart()}">
                <div class="container mb-4">
                    <div class="row">
                        <div class="col-12">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th scope="col"> </th>
                                            <th scope="col">Cas Name</th>
                                            <th scope="col" class="text-center">Rental Date</th>
                                            <th scope="col" class="text-center">Return Date</th>
                                            <th scope="col" class="text-center">Quantity</th>
                                            <th scope="col" class="text-center">Price</th>
                                            <th scope="col" class="text-right">Total Date</th>
                                            <th scope="col" class="text-right">Total</th>
                                            <th> </th>
                                            <th> </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="total" value="0"></c:set>
                                        <c:forEach var="dto" items="${cart.getCart()}">  
                                        <form action="UpdateController" method="POST">
                                            <tr>
                                                <td></td>
                                                <td>${dto.carName}</td>
                                                <td>${dto.rentalDate}</td>
                                                <td>${dto.returnDate}</td>
                                                <td class="text-right">
                                                    <input type="number" min="1" name="txtQuantity" value="${dto.quantity}">
                                                </td>
                                                <td class="text-center">
                                                    $${dto.price}
                                                </td>
                                                <td class="text-center">
                                                    <fmt:parseDate value="${dto.rentalDate}" var="rentalDate" pattern="yyyy-MM-dd"/>
                                                    <fmt:parseDate value="${dto.returnDate}" var="returnDate" pattern="yyyy-MM-dd"/>
                                                    <c:set var="date" value="${returnDate.getTime() - rentalDate.getTime()}"></c:set>
                                                    <c:set var="totalDate" value="${date / (24 * 60 * 60 * 1000)}"></c:set>
                                                    ${totalDate}
                                                </td>
                                                <td class="text-right">
                                                    $${dto.price * dto.quantity * totalDate}
                                                </td>
                                                <td class="text-right">
                                                    <input type="hidden" name="txtCarID" value="${dto.carID}">
                                                    <input type="hidden" name="txtCarName" value="${dto.carName}">
                                                    <input type="hidden" name="txtPrice" value="${dto.price}">
                                                    <input type="hidden" name="txtRentalDate" value="${dto.rentalDate}">
                                                    <input type="hidden" name="txtReturnDate" value="${dto.returnDate}">
                                                    <input type="hidden" name="txtQuantity" value="${param.txtQuantity}">
                                                    <button type="submit" class="btn btn-sm btn-success"><i class="fa fa-wrench"></i> </button>
                                                </td>
                                        </form>

                                        <td>
                                            <c:url var="delete" value="DeleteController">
                                                <c:param name="txtCarID" value="${dto.carID}"></c:param>
                                                <c:param name="txtRentalDate" value="${dto.rentalDate}"></c:param>
                                                <c:param name="txtReturnDate" value="${dto.returnDate}"></c:param>
                                            </c:url>
                                            <a href="${delete}">
                                                <button class="btn btn-sm btn-danger" onclick="return confirm('Do you want to delete?')"> <i class="fa fa-trash"></i> </button>
                                            </a>
                                        </td>
                                        </tr>   
                                        <c:set var="total" value="${total + dto.price * dto.quantity * totalDate}"></c:set>
                                    </c:forEach>
                                    <form action="SaveController" method="POST">
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td><strong>Phone:</strong></td>
                                            <td class="text-right"><input type="text" maxlength="11"  minlength="10" name="txtPhone" value="${sessionScope.LOGIN_USER.phone}"></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td><strong>Address:</strong></td>
                                            <td class="text-right"><input type="text" maxlength="30" name="txtAddress" value="${sessionScope.LOGIN_USER.address}"></td>
                                            <td></td>
                                            <td></td>
                                        </tr>

                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td><strong>Voucher:</strong></td>
                                            <td class="text-right">
                                                <input type="text" name="txtDiscount" value="${sessionScope.DISCOUNT_CODE}">
                                            <td>
                                                <button type="submit" name="btnAction" value="verifyDiscount" class="btn btn-sm btn-success"><i>Check available</i></button>
                                            </td>
                                            <td></td>
                                        </tr>

                                        <c:if test="${sessionScope.DISCOUNT != null}">
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td>Discount: </td>
                                                <td class="text-right"><strong>${sessionScope.DISCOUNT}%</strong></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                        </c:if>
                                        <c:set var="total" value="${total - (total * (sessionScope.DISCOUNT/100))}"></c:set>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td><strong>Total:</strong></td>
                                                <td class="text-right"><strong>US$${total}</strong></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>
                                                <input type="hidden" name="txtPercentOfDiscount" value="${sessionScope.DISCOUNT}">
                                                <input type="hidden" name="txtTotal" value="${total}">
                                                <button style="width: 225px;" name="btnAction" value="Buy" class="btn btn-lg btn-block btn-success text-uppercase">Buy</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                    </form>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>
        <c:if test="${(cart.getCart() == null || empty cart.getCart()) && requestScope.MESSAGE == null}">
            <h1 style="margin-left: 40%; color:red"> Nothing in cart </h1>
        </c:if>
        <div class="col mb-4">
            <div class="row">
                <div class="col-sm-12  col-md-6">
                    <form action="SearchController" method="POST">
                        <button type ="submit" class="btn btn-block btn-light">Continue Shopping</button>
                    </form>
                </div>
                <div class="col-sm-12  col-md-6">
                    <form action="ViewHistoryController" method="POST">
                        <button type ="submit" class="btn btn-block btn-light">View History</button>
                    </form>
                </div>
            </div>
        </div>

    </div>

    <!-- Footer -->
    <footer class="text-light">
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-lg-4 col-xl-3">
                    <h5>About</h5>
                    <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                    <p class="mb-0">
                        Best food
                    </p>
                </div>

                <div class="col-md-2 col-lg-2 col-xl-2 mx-auto">
                    <h5>Informations</h5>
                    <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                    <ul class="list-unstyled">
                        <li>Datnlm</li>
                        <li>SE140870</li>
                    </ul>
                </div>

                <div class="col-md-3 col-lg-2 col-xl-2 mx-auto">                                            
                </div>

                <div class="col-md-4 col-lg-3 col-xl-3">
                    <h5>Contact</h5>
                    <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                    <ul class="list-unstyled">
                        <li><i class="fa fa-home mr-2"></i> Rental Car</li>
                        <li><i class="fa fa-envelope mr-2"></i> datnlm@fpt.com</li>
                        <li><i class="fa fa-phone mr-2"></i> + 33 12 14 15 16</li>
                        <li><i class="fa fa-print mr-2"></i> + 33 12 14 15 16</li>
                    </ul>
                </div>                   
            </div>
        </div>
    </footer>
</body>
</html>
