<%@ page import="com.boysozoku.model.UserDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://fonts.cdnfonts.com/css/arcade-classic" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/pf-tempesta-seven" rel="stylesheet">
    <link rel="stylesheet" href="css/zelda.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <jsp:useBean id="userDAO" scope="session" class="com.boysozoku.model.UserDAO"/>
    <%
           try {
               userDAO.dao();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
    %>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#username').on("blur change keyup", function () {
                var userValue = $(this).val();
                $.get("checkusername?username=" + userValue, function (responseText) {
                    if (responseText === 'true') {
                        $('#msg').text("-WELCOME BACK ");
                        $('#msg').append(userValue);
                        $('#msg').append("-");
                        $('label[for="email"]').hide();
                        $('#email').hide();
                        $('#hidableseparator').hide();
                        $('#signup').text("LOG IN");
                        $('#signup').click(function () {
                            $('#form').attr('action', 'log-user');
                            $('#form').attr('method', 'POST');
                        });
                    } else {
                        $('#msg').text("-BE PART OF THE BOYSOZOKU CLAN-");
                        $('label[for="email"]').show();
                        $('#email').show();
                        $('#hidableseparator').show();
                        $('#signup').text("SIGN UP");
                        $('#signup').click(function () {
                            $('#form').attr('action', 'create-user');
                            $('#form').attr('method', 'POST');
                        });
                    }
                });
            });
        });
    </script>
    <%if (session.getAttribute("pwdError")==null) session.setAttribute("pwdError", false);%>
</head>


<body>
<div class="close">
    <button class="bt">
        <a href="./index.jsp"> X </a>
    </button>
</div>
<div class="conteiner">
    <form id="form" action="create-user" method="post">
        <input hidden="hidden" type="text" id="redirect" name="redirect" value=${param["redirect"]}>
        <br>
    <p id="msg" class="thanks-centered">
        -BE PART OF THE BOYSOZOKU CLAN-
    </p>
    <table>
                <tr>
                    <td><label for="username">username</label></td>
                    <td class="separator">-</td>
                    <td><input type="text" id="username" name="username" pattern="[^\s]+" title="No spaces allowed"></td>
                </tr>
                <tr>
                    <td><label for="email">e-mail</label></td>
                    <td id="hidableseparator" class="separator">-</td>
                    <td><input type="text" id="email" name="email" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$"></td>
                </tr>
                <tr>
                    <td><label for="pwd">password</label></td>
                    <td class="separator">-</td>
                    <td><input type="password" id="pwd" name="pwd"  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one  number and one uppercase and lowercase letter, and at least 8 or more characters"></td>
                </tr>
                <tr><td> </td><td><button type="submit" id="signup" name="login" class="button">SIGNUP</button></td><td> </td></tr>
    </table>
    </form>
    <br>
    <%if((Boolean) session.getAttribute("pwdError")){%><span>Wrong password: try again or contact our support.</span><%}%>
</div>
</body>
</html>