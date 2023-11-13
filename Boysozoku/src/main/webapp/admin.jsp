<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<jsp:useBean id="userBean" type="com.boysozoku.model.UserBean" scope="session"/>
<c:if test="${userBean.username.equals('admin')}">
    <sql:setDataSource
            var="db"
            driver="com.mysql.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/Boysozoku"
            user="root" password="Lorenzo_2002_"
    />
    <sql:query var="ordini" dataSource="${db}">
        SELECT * FROM BUILD_ORDINATE;
    </sql:query>

    <h3>Ordini</h3>
    <table>
        <th>ID BUILD</th>
        <th>UTENTE</th>
        <th>DATA ORDINE</th>
        <th>DA MONTARE</th>
        <c:forEach var="ordine" items="${ordini.rows}">

            <tr>
                <td><c:out value="${ordine.ID_BUILD_ORDINATE}" /></td>

                <td><c:out value="${ordine.UTENTE_ORDINE}" /></td>

                <td><c:out value="${ordine.DATA_ORDINE}" /></td>

                <td><c:out value="${ordine.DA_MONTARE}" /></td>
            </tr>
        </c:forEach>
    </table>

    <sql:update dataSource="${db}">
        DROP TABLE IF EXISTS SCOCCA;
    </sql:update>
    <sql:update dataSource="${db}">
        DROP TABLE IF EXISTS SCHERMO;
    </sql:update>
    <sql:update dataSource="${db}">
        DROP TABLE IF EXISTS PULSANTI;
    </sql:update>
    <sql:update dataSource="${db}">
        CREATE TABLE SCOCCA AS SELECT * FROM MOD_BUILD WHERE TIPO = "SCOCCA";
    </sql:update>
    <sql:update dataSource="${db}">
        ALTER TABLE SCOCCA
        RENAME COLUMN DESCRIZIONE TO SHELL;
    </sql:update>
    <sql:update dataSource="${db}">
        CREATE TABLE SCHERMO AS SELECT * FROM MOD_BUILD WHERE TIPO = "SCHERMO";
    </sql:update>
    <sql:update dataSource="${db}">
        ALTER TABLE SCHERMO
        RENAME COLUMN DESCRIZIONE TO SCREEN;
    </sql:update>
    <sql:update dataSource="${db}">
        CREATE TABLE PULSANTI AS SELECT * FROM MOD_BUILD WHERE TIPO = "PULSANTI";
    </sql:update>
    <sql:update dataSource="${db}">
        ALTER TABLE PULSANTI
        RENAME COLUMN DESCRIZIONE TO BUTTONS;
    </sql:update>

    <sql:query var="builds" dataSource="${db}">
        SELECT BUILD.ID, BUILD.FILE_BUILD, SCOCCA.SHELL, SCHERMO.SCREEN, PULSANTI.BUTTONS
        FROM BUILD
        INNER JOIN SCOCCA ON BUILD.ID_SCOCCA = SCOCCA.ID
        INNER JOIN PULSANTI ON BUILD.ID_PULSANTI = PULSANTI.ID
        INNER JOIN SCHERMO ON BUILD.ID_SCHERMO = SCHERMO.ID
        ORDER BY ID
    </sql:query>

    <h3>Build</h3>
    <table>
        <th>ID</th>
        <th>FILE CUSTOM</th>
        <th>SCOCCA</th>
        <th>SCHERMO</th>
        <th>PULSANTI</th>

        <c:forEach var="B" items="${builds.rows}">

            <tr>
                <td><c:out value="${B.ID}" /></td>

                <td><a href="${B.FILE_BUILD}">APRI</a></td>

                <td><c:out value="${B.SHELL}" /></td>

                <td><c:out value="${B.SCREEN}" /></td>

                <td><c:out value="${B.BUTTONS}" /></td>
            </tr>
        </c:forEach>
    </table>
    <h3>MOD</h3>
    <sql:query var="mods" dataSource="${db}">
        SELECT * FROM MOD_BUILD
    </sql:query>
    <table>
        <th>ID</th>
        <th>CONSOLE</th>
        <th>TIPO</th>
        <th>DESCRIZIONE</th>
        <th>PREZZO</th>
        <th>AGGIUNGI/CANCELLA</th>
        <c:forEach var="m" items="${mods.rows}">
            <tr>
                <td>${m.ID}</td>
                <td>${m.CONSOLE}</td>
                <td>${m.TIPO}</td>
                <td>${m.DESCRIZIONE}</td>
                <td><form method="get" action="./modifica-prezzo"><input name="id" id="id_p" type="number" value="${m.ID}" style="display: none"><input name="prezzo" id="prezzo" type="number" step="0.01" min="0" value="${m.PREZZO}"><input type="submit" value="MOD. PREZZO"></form></td>
                <td><form method="get" action="./rimuovi-mod"><input name="id" id="id_m" type="number" value="${m.ID}" style="display: none"><input type="submit" value="RIMUOVI"></form></td>
            </tr>
        </c:forEach>
        <form method="get" action="./aggiungi-mod">
            <td>
            <td>NUOVO</td>
            <td><select name="console" id="cons"><option>GB</option><option>GBC</option><option>GBA</option></select></td>
            <td><select name="tipo" id="tipo"><option>SCOCCA</option><option>SCHERMO</option><option>PULSANTI</option></select></td>
            <td><input type="text" name="descrizione" id="desc" placeholder="Descrizione"></td>
            <td><input name="prezzo" id="prz" type="number" step="0.01" value="0" min="0"></td>
            <td><input type="submit" value="AGGIUNGI"></td>
            </tr>
        </form>

    </table>
</c:if>
<c:if test="${not userBean.username.equals('admin')}">
    <h1>NON HAI I PERMESSI DA AMMINISTRATORE.</h1>
</c:if>
</body>
</html>