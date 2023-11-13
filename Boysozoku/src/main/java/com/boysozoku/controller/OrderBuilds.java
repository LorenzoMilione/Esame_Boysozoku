package com.boysozoku.controller;

import com.boysozoku.model.BuildBean;
import com.boysozoku.model.Cart;
import com.boysozoku.model.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "orderBuilds", value = "/order")
public class OrderBuilds extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date(new java.util.Date().getTime());
        Cart c = (Cart) req.getSession().getAttribute("cart");
        String username = ((UserBean) req.getSession().getAttribute("userBean")).getUsername();
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps =
                        con.prepareStatement("SELECT * FROM BUILD_CARRELLO WHERE UTENTE_CARRELLO = ?");
                        ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idOrder = rs.getInt("ID");
                    int idBuild = rs.getInt("ID_BUILD_CARRELLO");
                    boolean daMontare = rs.getBoolean("DA_MONTARE");
                    PreparedStatement psload= con.prepareStatement("INSERT INTO BUILD_ORDINATE (ID_BUILD_ORDINATE, UTENTE_ORDINE, DA_MONTARE, DATA_ORDINE) VALUES (?, ?, ?, ?);");
                    psload.setInt(1, idBuild);
                    psload.setString(2, username);
                    psload.setBoolean(3, daMontare);
                    psload.setDate(4, date);
                    psload.execute();

                    PreparedStatement psremove = con.prepareStatement("DELETE FROM BUILD_CARRELLO WHERE ID = ?");
                    psremove.setInt(1, idOrder);
                    psremove.execute();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.sendRedirect("./payment_confirm.jsp");
    }
}

