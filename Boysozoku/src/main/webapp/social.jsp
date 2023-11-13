<%@ page import="com.boysozoku.model.Post" %>
<%@ page import="com.boysozoku.model.BuildBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BOYSOZOKU</title>
    <link href="https://fonts.cdnfonts.com/css/arcade-classic" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/pf-tempesta-seven" rel="stylesheet">
    <link rel="stylesheet" href="css/social.css" type="text/css">

</head>
<jsp:useBean id="social" class="com.boysozoku.model.Social"/>
<%social.dao();%>
<body>
<jsp:useBean id="userBean" scope="session" class="com.boysozoku.model.UserBean"/>
<div class="container">
    <div class="close">
        <button class="bt">
            <a href="./index.jsp"> X </a>
        </button>
    </div>
    <div class="logo">
        BOYSOZOKU
    </div>
    <div class="social">
        <c:forEach var="post" items="${social.postList}">
            <div class="cartuccia">
                <div class="item">
                    <div class="nome">
                        <div class="preset">
                            ${post.build.name}
                        </div>
                        <div class="user">
                            <c:if test="${post.user.equals(userBean.username)}">
                            <a href="./userpage.jsp">  ${post.user} </a>
                            </c:if>
                            <c:if test="${!post.user.equals(userBean.username)}">
                                <a href="./alt_userpage.jsp?user=${post.user}">  ${post.user} </a>
                            </c:if>
                        </div>
                    </div>
                    <div class="vuoto"></div>

                    <div class="console">
                        <a href="buy_page.jsp?console=${post.build.console.name()}&build=${post.build.toString()}">
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
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
    <div class="filter">
    </div>
    </div>

</body>
</html>
