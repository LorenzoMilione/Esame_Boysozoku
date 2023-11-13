package com.boysozoku.controller;

import com.boysozoku.model.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/checkusername")
public class CheckUsernameDB extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) req.getSession().getAttribute("userDAO");
        String username = req.getParameter("username");
        PrintWriter out = resp.getWriter();
        out.print(userDAO.getUserList().stream().anyMatch(user->user.getUsername().equals(username)));
    }
}
