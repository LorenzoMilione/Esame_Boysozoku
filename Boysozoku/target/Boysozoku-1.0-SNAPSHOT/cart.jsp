<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.boysozoku.model.Cart" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.cdnfonts.com/css/arcade-classic" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/pf-tempesta-seven" rel="stylesheet">
    <link rel="stylesheet" href="css/cart.css" type="text/css">
    <jsp:useBean id="userBean" scope="session" class="com.boysozoku.model.UserBean"/>
    <jsp:useBean id="cart" scope="session" class="com.boysozoku.model.Cart"/>
    <%cart.setOwnerUsername(userBean.getUsername());%>
    <%cart.dao();%>
    <%cart.setTotalPrice(0);%>
</head>

<body>

<div class="container">
    <div class="close">
        <button class="bt">
            <a href="./index.jsp"> X </a>
        </button>
    </div>
    <div class="table">
        <div class="logo">
            <h1> Take  Your  Time </h1>
        </div>
        <div class="cart">
            <c:forEach var="build" items="${cart.buildsInCart}">
                <div class="item">
                    <div class="console">
                        <img class="image" src="image/${build.console.name()}Icon.png">
                    </div>
                    <div class="desrizione">
                        <h4>
                            ${build.name} <br>
                                <c:if test="${build.shell.present}">
                                    - ${build.shell.get().name} (+ ${build.shell.get().price}) <br>
                                </c:if>
                                <c:if test="${build.screen.present}">
                                    - ${build.screen.get().name} (+ ${build.screen.get().price}) <br>
                                </c:if>
                                <c:if test="${build.buttons.present}">
                                    - ${build.buttons.get().name} (+ ${build.buttons.get().price}) <br>
                                </c:if>
                                TOTAL = ${build.price}
                                ${cart.addToTotal(build.price)};
                        </h4>
                    </div>
                    <div class="x">
                        <a href="./removefromcart?item=${build.toString()}"> X </a>
                    </div>
                </div>
            </c:forEach>
            </div>
        </div>
    </div>

    <div class="lower">
        <div class="total">
            <h2> Total price: </h2>
        </div>
        <div class="price">
            <h3>${cart.totalPrice}</h3>
        </div>
        <div class="buy">
            <button class="compra">
                <a href="./order"> BUY </a>
            </button>
        </div>
    </div>
</div>


</body>

</html>
