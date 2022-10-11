<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Pominov7
  Date: 10.10.2022
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Каталог автомобилей</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: gray">
        <div>
            <a href="<%=request.getContextPath()%>/list" class="navbar-brand">На главную</a>
        </div>
    </nav>
</header>
<br>

<div class="row">

    <div class="container">
        <h3 class="text-center">Каталог автомобилей</h3>
        <hr>
        <div class="container text-left" style="text-align: center">

            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Добавить авто в каталог</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Производитель</th>
                <th>Модель</th>
                <th>Год выпуска</th>
                <th>Цена</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="car" items="${listCars}">

                <tr>
                    <td><c:out value="${car.id}"/></td>
                    <td><c:out value="${car.name}"/></td>
                    <td><c:out value="${car.model}"/></td>
                    <td><c:out value="${car.year}"/></td>
                    <td><c:out value="${car.price}"/></td>
                    <td><a href="edit?id=<c:out value='${car.id}' />">Изменить</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a
                                href="delete?id=<c:out value='${car.id}' />">Удалить</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>