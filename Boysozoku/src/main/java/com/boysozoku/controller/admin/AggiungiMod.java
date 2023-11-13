package com.boysozoku.controller.admin;

import com.boysozoku.controller.ConPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "aggiungi-mod", value = "/aggiungi-mod")
public class AggiungiMod extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("INSERT INTO MOD_BUILD(CONSOLE, TIPO, PREZZO, DESCRIZIONE) VALUES (?, ?, ?, ?)");
            ps.setString(1, req.getParameter("console"));
            ps.setString(2, req.getParameter("tipo"));
            ps.setFloat(3, Float.parseFloat(req.getParameter("prezzo")));
            ps.setString(2, req.getParameter("descrizione"));
            ps.execute();
            resp.sendRedirect("./admin.jsp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
