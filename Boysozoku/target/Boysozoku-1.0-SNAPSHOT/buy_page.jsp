<%@ page import="com.boysozoku.model.enums.Console" %>
<%@ page import="com.boysozoku.model.BuildDAO" %>
<%@ page import="com.boysozoku.model.BuildBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Build your Console</title>
    <link href="https://fonts.cdnfonts.com/css/arcade-classic" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/pf-tempesta-seven" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/buypage.css">
</head>
<body>
<jsp:useBean id="buildDAO" scope="session" class="com.boysozoku.model.BuildDAO"/>
        <%buildDAO.dao(Console.valueOf(request.getParameter("console")));%>
<jsp:useBean id="build" scope="session" class="com.boysozoku.model.BuildBean"/>
<%build.setConsole(Console.valueOf(request.getParameter("console")));%>
<%if(request.getParameter("clear") != null){session.setAttribute("build", new BuildBean());}%>
<%if(request.getParameter("build") != null){build = BuildBean.fromString(request.getParameter("build"));}%>
<% build.setPrice(BuildBean.calcFullPrice(build));%>
<form id="updatebuild" enctype="multipart/form-data" action="update-build" method="post">
    <div class="container">
        <div class="left">
            <div class="print">
                <%if(build.getCustom()!=null){%>
                    <img class="shell" src="./buildimg/<%=build.getCustom().getName()%>" alt="preview">
                    <img class="mask" src="image/<%=request.getParameter("console")%>.png" alt="">
                    <img class="buttons" src="<%if(build.getButtons().isPresent()){%>image/<%=build.getButtons().get().getName()%>.png<%}%>" alt="" >
                <%} else{%>
                    <%if (build.getShell().isPresent()){%>
                    <img class="shell" src="image/<%=build.getShell().get().getName()%>.png" alt="preview"> <%}
                    else{%> <img class="shell" src="image/Vuoto.png" alt=""> <%}%>
                    <img class="mask" src="image/<%=request.getParameter("console")%>.png" alt="">
                    <img class="buttons" src="<%if(build.getButtons().isPresent()){%>image/<%=build.getButtons().get().getName()%>.png<%}%>" alt="">
                <%}%>
            </div>
            <input type="file" id="immagine" name="immagine" onchange="form.submit()">
        </div>
        <div class="right">
            <div class="riga">
                <label for="shell">Shell:</label>
                <select id="shell" name="shell" onchange="form.submit()">
                    <option style="font-family: 'PF Tempesta Seven', sans-serif;" value="NONE">NONE</option>
                    <c:set var="buildshell"><%if (build.getShell().isPresent())%><%=build.getShell().get().getName()%></c:set>
                    <c:forEach var="sh" items="${buildDAO.shellList}">
                        <option style="font-family: 'PF Tempesta Seven', sans-serif;" value="${sh.name}" ${buildshell==sh.name ? 'selected' : ''}>${sh.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="riga">
                <label for="screen">Screen:</label>
                <select id="screen" name="screen" onchange="form.submit()">
                    <option style="font-family: 'PF Tempesta Seven', sans-serif;" value="NONE">NONE</option>
                    <c:set var="buildscreen"><%if (build.getScreen().isPresent())%><%=build.getScreen().get().getName()%></c:set>
                    <c:forEach var="screen" items="${buildDAO.screenList}">
                        <option style="font-family: 'PF Tempesta Seven', sans-serif;" value="${screen.name}"${buildscreen == screen.name ? 'selected' : ''}>${screen.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="riga">
                <label for="buttons">Buttons:</label>
                <select id="buttons" name="buttons" onchange="form.submit()">
                    <option style="font-family: 'PF Tempesta Seven', sans-serif;" value="NONE">NONE</option>
                    <c:set var="buildbuttons"><%if (build.getButtons().isPresent())%><%=build.getButtons().get().getName()%></c:set>
                    <c:forEach var="buttons" items="${buildDAO.buttonsList}">
                        <option style="font-family: 'PF Tempesta Seven', sans-serif;" value="${buttons.name}"${buildbuttons == buttons.name ? 'selected' : ''}>${buttons.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="close">
            <button class="bt">
                <a href="./index.jsp"> X </a>
            </button>
        </div>
    </div>
    <div class="lowercontainer">
        <div class="lowerleft">
            <input type="text" id="presetname" name="presetname" placeholder="Edit the Preset Name" onchange="form.submit()" <%if (build.getName()!=null){%><%="value="+build.getName()%><%}%>>
            <div class="underleft">
                <button type="submit" formaction="share" formmethod="post">Share</button>
                <button type="submit" formaction="save" formmethod="post">Save</button>
                <input type="file" id="load" name="load" accept="text/plain">
                <button type="submit" id="submitload" name="subload" formaction="load" formmethod="post">Load</button>
            </div>
        </div>
        <div class="lowerright">
            <label for="mount">Do you want your preset mounted on your console? (+15.00$ at checkout) </label>
            <input type="checkbox" id="mount" name="mount">
            <h5><%=build.getPrice()%></h5>
            <input id="console" name="console" type="text" hidden="hidden" value="<%=request.getParameter("console")%>">
            <button type="submit" formaction="buy" formmethod="post"> Buy </button>
        </div>
    </div>
</form>
<script type="text/javascript">
    var frm = $('#updatebuild');

    frm.submit(function (e) {

        e.preventDefault();

        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: frm.serialize(),
            success: function (data) {
                console.log('Submission was successful.');
                console.log(data);
            },
            error: function (data) {
                console.log('An error occurred.');
                console.log(data);
            },
        });
    });
</script>
</body>
</html>

