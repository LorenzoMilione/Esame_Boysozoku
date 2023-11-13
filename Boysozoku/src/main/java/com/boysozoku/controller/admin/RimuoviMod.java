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

@WebServlet(name = "rimuovi-mod", value = "/rimuovi-mod")
public class RimuoviMod extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("DELETE FROM MOD_BUILD WHERE ID = ?");
            ps.setInt(1, Integer.parseInt(req.getParameter("id")));
            ps.execute();
            resp.sendRedirect("./admin.jsp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
