package com.boysozoku.controller;

import com.boysozoku.model.BuildBean;
import com.boysozoku.model.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

@WebServlet(value = "/buy")
    public class Buy extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
        BuildBean buildBean = (BuildBean) req.getSession().getAttribute("build");
        if (userBean.getUsername().isEmpty()) {
            resp.sendRedirect("login.jsp?redirect=buy");
            return;
        }
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("INSERT INTO BUILD(NOME, CONSOLE, FILE_BUILD, ID_SCOCCA, ID_SCHERMO, ID_PULSANTI, PREZZO) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, buildBean.getName());
            ps.setString(2, buildBean.getConsole().toString());
            if(buildBean.getCustom()!=null)
                ps.setString(3, buildBean.getCustom().getPath());
            else ps.setString(3, null);
            if(buildBean.getShell().isPresent())
                ps.setInt(4, buildBean.getShell().get().getId());
            if(buildBean.getScreen().isPresent())
                ps.setInt(5, buildBean.getScreen().get().getId());
            if(buildBean.getButtons().isPresent())
                ps.setInt(6,  buildBean.getButtons().get().getId());
            ps.setFloat(7, buildBean.getPrice());
            ps.execute();
            PreparedStatement ps1 =
                    con.prepareStatement("SELECT ID FROM BUILD ORDER BY ID DESC LIMIT 1");
            ResultSet rs1 = ps1.executeQuery();
            int idbuild = 0;
            while (rs1.next()){
                idbuild = rs1.getInt(1);
            }
            PreparedStatement ps2 =
                    con.prepareStatement("INSERT INTO BUILD_CARRELLO(DA_MONTARE, UTENTE_CARRELLO, ID_BUILD_CARRELLO) VALUES (?,?,?)");
            ps2.setBoolean(1, buildBean.getMount());
            ps2.setString(2, userBean.getUsername());
            ps2.setInt(3, idbuild);
            ps2.execute();
            resp.sendRedirect("cart.jsp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
