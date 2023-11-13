<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.cdnfonts.com/css/arcade-classic" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/pf-tempesta-seven" rel="stylesheet">
    <link rel="stylesheet" href="css/userpage.css" type="text/css">
    <script id="search-js" defer="" src="https://api.mapbox.com/search-js/v1.0.0-beta.17/web.js"></script>

</head>
<body>
<jsp:useBean id="userBean" scope="session" class="com.boysozoku.model.UserBean"/>
<jsp:useBean id="social" class="com.boysozoku.model.Social"/>
<%social.dao();%>
<div class="sopra">
    <form name="loadpropic" method="post" enctype="multipart/form-data" action="update-propic">
    <div class="profile">
        <%if(userBean.getPropic()!=null){%>
            <img src="./userimg/<%=userBean.getPropic().getName()%>">
        <%} else %> <img src="image/blocco.png">
    </div>
    <div class="edit">
        <input type="file" id="propic" name="propic" accept="image/*" onchange="form.submit()">
    </div>
    </form>
    <div class="user">
        <input name="username" id="username" type="text" value="${userBean.username}">
    </div>
    <div class="close">
        <button class="bt">
            <a href="./index.jsp"> X </a>
        </button>
    </div>
</div>
<div class="sotto">
    <form action="update-user" method="POST">
    <div class="form">
        <input type="email" name="email" value="${userBean.email}">
        <br>
        <h3>Shipping</h3>
        <br>
        <input class="input" id="indirizzo" name="indirizzo" autocomplete="shipping address-line1" placeholder="Address" value="${userBean.indirizzo}" onchange="saveInput()">
        <br>
        <input class="input" id="apartment" name="apartment" autocomplete="shipping address-line2" value="${userBean.appartamento}" placeholder="Apartment">
            <input class="input" id="city" name="city" autocomplete="shipping address-level2" value="${userBean.citta}" placeholder="City">
            <br>
            <input class="input" id="state" name="state" autocomplete="shipping address-level1" value="${userBean.provincia}" placeholder="State">
            <br>
            <input class="input" id="country" name="country" autocomplete="shipping country" placeholder="Country">
            <br>
            <input class="input" id="postcode" name="postcode" autocomplete="shipping postal-code" value="${userBean.cap}"  placeholder="ZIP / Postcode">
    </div>
    <div class="save">
        <input type="submit" name="save" id="save" value="save">
    </div>
        <input type="text" id="address" name="address" hidden="hidden">
    </form>
</div>
<div class="shared">
    <div class="preset">
        <h3> Shared Post</h3>
    </div>
    <div class="post">
        <c:forEach var="preset" items="${social.getPostListByUser(userBean.username)}">
        <div class="sotto2">
                <div class="console2">
                    <img src="image/${preset.getBuild().getConsole().name()}Icon.png">
                </div>
                <div class="form2">
                    <div class="presetname">
                        <h2> ${preset.getBuild().getName()}</h2>
                    </div>
                    <div class="data2">
                        <h2> ${preset.getPostDate()} </h2>
                    </div>
                </div>
        </div>
        </c:forEach>
    </div>
</div>
<script>
    const ACCESS_TOKEN = 'pk.eyJ1IjoicmFkaW9tYXJpYSIsImEiOiJjbG8xN2g1YjgxbXFoMm1vNjQxemdjcXgyIn0.EE0DWDoPNhKmHiCKTw592w';

    const script = document.getElementById('search-js');
    script.onload = () => {
        const collection = mapboxsearch.autofill({
            accessToken: ACCESS_TOKEN
        });
    };

    function saveInput() {
        var x = document.getElementById("indirizzo");
        document.getElementById("address").value = x.value;
    }
</script>
</body>
</html>