<%-- 
    Document   : search
    Created on : Jan 31, 2021, 1:21:40 AM
    Author     : macbook
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rental Car</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="css/styleViewCart.css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

    </head>
    <body>
        <c:set var="listCars" value="${requestScope.LIST_CARS}"></c:set> 
        <c:set var="listCategory" value="${sessionScope.LIST_CATEGORY}"></c:set>
            <nav class="navbar navbar-expand-md navbar-dark bg-dark">
                <div class="container">               
                    <a style="color:white" class="navbar-brand">Rental Car</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                    <ul class="navbar-nav m-auto">
                        <form action="SearchController" method="post" class="navbar-nav m-auto">
                            <li class="nav-item">                     
                                <input type="text" class="form-control ml-3" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Name..." name="txtName" value="${param.txtName}">
                            </li>

                            <li style="margin-left: 10px" class="nav-item">
                                <c:set var="selectedCategory" value="${requestScope.CATEGORY}"></c:set>
                                    <select class="form-control ml-3" name="cbxCategory">
                                        <option value="">Category</option>
                                    <c:forEach var="category" items="${listCategory}">
                                        <c:if test="${selectedCategory == category}">
                                            <option selected="selected">${category}</option>
                                        </c:if>
                                        <c:if test="${selectedCategory != category}">
                                            <option>${category}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </li>
                            <li style="margin-left: 5px" class="nav-item">                     
                                <input style="width: 150px" type="date" class="form-control ml-3" aria-label="Small" aria-describedby="inputGroup-sizing-sm" name="txtRentalDate" value="${param.txtRentalDate}">
                            </li>
                            <li style="margin-left: 5px" class="nav-item">                     
                                <input style="width: 150px" type="date" class="form-control ml-3" aria-label="Small" aria-describedby="inputGroup-sizing-sm" name="txtReturnDate" value="${param.txtReturnDate}">
                            </li>
                            <li class="nav-item">                     
                                <input style="width: 100px" type="number" min="1" class="form-control ml-3" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Quantity"  name="txtQuantity" value="${param.txtQuantity}">
                            </li>

                            <li clas="nav-item">
                                <div class="input-group-append">
                                    <button type="submit" class="btn btn-secondary btn-number ml-3">
                                        <i class="fa fa-search"></i>
                                    </button>

                                </div>
                            </li>
                        </form>
                        <c:if test="${sessionScope.LOGIN_USER != null}">
                            <form class="form-inline my-2 my-lg-0">
                                <c:url var="viewCart" value="viewCart">
                                </c:url>
                                <a class="btn btn-success btn-sm ml-3" href="${viewCart}">
                                    <i class="fa fa-shopping-cart"></i> Cart
                                </a>
                            </form>
                            <form class="form-inline my-2 my-lg-0">
                                <c:url var="logout" value="LogoutController">
                                </c:url>
                                <a class="btn btn-danger btn-sm ml-2" href="${logout}">  <i class="fa fa-sign-out"></i> ${sessionScope.LOGIN_USER.name} - Logout</a>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.LOGIN_USER == null}">
                            <form class="form-inline my-2 my-lg-0">
                                <c:url var="login" value="LoginController">
                                </c:url>
                                <a class="btn btn-success btn-sm ml-3" href="${login}">  <i class="fa fa-sign-login"></i>Login</a>
                            </form>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>
        <h1 style="color:red; text-align: center">${requestScope.ERROR}</h1>
        <section class="jumbotron text-center">
        </section>
        <h1 style="margin-left: 40%; color:red">${requestScope.EMPTY_LIST}</h1>
        <h1 style="margin-left: 40%; color:green">${requestScope.SUCCESS}</h1>
        <c:if test="${listCars != null && not empty listCars}">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="row">
                            <c:forEach var="dto" items="${listCars}">
                                <div class="col-3">
                                    <div class="card">
                                        <form action="AddController" method="POST">
                                            <img style="width: 225px;height: 225px" class="card-img-top" src="./images/${dto.img}" alt="Card image cap">
                                            <div class="card-body">
                                                <h4 class="card-title">${dto.carName}</h4>
                                                <p class="card-text">Color: ${dto.color}</p>
                                                <p class="card-text">Year: ${dto.year}</p>
                                                <p class="card-text">Category: ${dto.category}</p>
                                                <p class="card-text">Available in stock ${dto.quantity}</p>
                                                <div class="row">
                                                    <div class="col">
                                                        <p class="btn btn-danger btn-block">${dto.price}$</p>
                                                    </div>
                                                    <div class="col">
                                                        <p class="btn btn-warning btn-block">Rate: ${dto.rate}</p>
                                                    </div>
                                                    <div class="col">
                                                        <button type ="submit" class="btn btn-success btn-block">Add to cart</button>
                                                        <input type="hidden" name="txtCarID" value="${dto.carID}">
                                                        <input type="hidden" name="txtCarName" value="${dto.carName}">
                                                        <input type="hidden" name="txtPrice" value="${dto.price}">
                                                        <input type="hidden" name="txtQuantity" value="${param.txtQuantity}">
                                                        <input type="hidden" name="txtRentalDate" value="${param.txtRentalDate}">
                                                        <input type="hidden" name="txtReturnDate" value="${param.txtReturnDate}">
                                                    </div>
                                                </div>
                                            </div>
                                        </form>                                 
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="col-12">
                                <nav aria-label="...">
                                    <ul class="pagination">
                                        <c:forEach var="number" begin="1" end="${requestScope.NUMBER_PAGE}"> 
                                            <li class="page-item">
                                                <a class="page-link" href="SearchController?txtName=${param.txtName}&cbxCategory=${param.cbxCategory}&txtRentalDate=${param.txtRentalDate}&txtReturnDate=${param.txtReturnDate}&txtQuantity=${param.txtQuantity}&txtIndex=${number}">${number}</a>
                                            </li>
                                        </c:forEach>                                  
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <!-- Footer -->
        <footer class="text-light">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 col-lg-4 col-xl-3">
                        <h5>About</h5>
                        <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                        <p class="mb-0">
                            Rental Car
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
