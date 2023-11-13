package com.boysozoku.controller;

import com.boysozoku.model.UserBean;
import com.boysozoku.model.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(value = "/log-user")
public class LogUser extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) req.getSession().getAttribute("userDAO");
        Optional<UserBean> user = userDAO.getUserList().stream().filter(userBean -> userBean.getUsername().equals(req.getParameter("username"))).findAny();
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps =
                        con.prepareStatement("SELECT COUNT(*) FROM UTENTE WHERE USERNAME=? AND PASSWORD_UTENTE = MD5(?)");
                ps.setString(1, user.get().getUsername());
                ps.setString(2, req.getParameter("pwd"));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        req.getSession().setAttribute("userBean", user.get());
                        resp.sendRedirect(req.getParameter("redirect"));
                    }
                    else {
                        req.getSession().setAttribute("pwdError", true);
                        resp.sendRedirect("login.jsp?redirect="+req.getParameter("redirect"));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}