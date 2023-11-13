package com.boysozoku.controller;

import com.boysozoku.model.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(value = "/create-user")
public class CreateUser extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = ConPool.getConnection()) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String pwd = req.getParameter("pwd");

            if(!(username.matches("[^\s]+") && email.matches("[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,}$") && pwd.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"))) {
                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Error! Follow the tips given by the boxes');");
                out.println("location='index.jsp';");
                out.println("</script>");
            }
            PreparedStatement ps =
                    con.prepareStatement("INSERT INTO UTENTE VALUES (?, ?, NULL, NULL, NULL, NULL, NULL, NULL, MD5(?))");
            ps.setString(1, username);
            ps.setString(2, req.getParameter("email"));
            ps.setString(3, req.getParameter("pwd"));
            ps.execute();
            req.getSession().setAttribute("userBean", new UserBean(req.getParameter("username"), req.getParameter("email"), null, null, null, null, 0, null));
            resp.sendRedirect(req.getParameter("redirect"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}