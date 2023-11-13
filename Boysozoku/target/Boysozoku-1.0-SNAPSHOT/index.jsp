<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BOYSOZOKU</title>
    <link href="https://fonts.cdnfonts.com/css/arcade-classic" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/pf-tempesta-seven" rel="stylesheet">
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<jsp:useBean id="userBean" scope="session" class="com.boysozoku.model.UserBean"/>
<jsp:useBean id="social" class="com.boysozoku.model.Social"/>
<%social.dao();%>

<div class="page">
    <div class="header">
        <img src="${userBean.propic.exists() ? "./userimg/".concat(userBean.propic.name) : "./image/blocco.png"}" class="profilo">
        <%if(userBean.getUsername().isEmpty()){%> <a href="./login.jsp?redirect=index.jsp" class="username"> LOG IN </a>
        <%} else{%> <a href="./userpage.jsp" class="username"> <%=userBean.getUsername()%> </a> <%}%>
            <a href="./cart.jsp" class="cart">
                <img src="image/cart.png" style="width: 80%; height: 80%" >
            </a>
    </div>
    <div class="sfondo">
        <div>
            <div class="title">
                <h1> BOYSOZOKU </h1>
            </div>
            <div class="about">
                <p>
                    This is about us. <br>
                    This describes what the site is and what you could do with us. <br>
                    Here i add a third row just because it reflects better the final design.
                </p>
            </div>
            <div class="shop">
                <a href="./consolepage.jsp" class="shopping"> SHOPPING </a>
            </div>

        </div>
    </div>

    <div class="sopra">
        <a href="./social.jsp"> SOCIAL</a>
        <div class="social">
        <c:forEach var="post" items="${social.postList}" end="4">
            <div  class="b">
                <c:set var="build" value="${post.build}"/>
                <c:if test="${build.custom.exists()}">
                    <img class="shell" src="./buildimg/${build.custom.name}" alt="preview">
                    <img class="mask" src="image/${build.console.name()}.png">
                    <c:if test="${build.buttons.present}">
                        <img class="buttons" src="image/${build.buttons.get().name}.png" alt="">
                    </c:if>
                </c:if>
                <c:if test="${not build.custom.exists()}">
                    <c:if test="${build.shell.present}">
                        <img class="shell" src="image/${build.shell.get().name}.png" alt="preview">
                    </c:if>
                    <c:if test="${not build.shell.present}">
                        <img class="shell" src="image/Vuoto.png" alt="preview">
                    </c:if>
                    <img class="mask" src="image/${build.console.name()}.png">
                    <c:if test="${build.buttons.present}">
                        <img class="buttons" src="image/${build.buttons.get().name}.png" alt="">
                    </c:if>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>
</div>
</body>
</html>






















