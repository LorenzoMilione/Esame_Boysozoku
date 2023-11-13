package com.boysozoku.controller;

import com.boysozoku.model.UserBean;
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

@WebServlet(name = "updateUser", value = "/update-user")
public class UpdateUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       System.out.println(req.getParameter("address"));
        UserBean user = (UserBean) req.getSession().getAttribute("userBean");
        user.setEmail(req.getParameter("email"));
        user.setIndirizzo(req.getParameter("address"));
        user.setAppartamento(req.getParameter("apartment"));
        user.setCitta(req.getParameter("city"));
        user.setStato(req.getParameter("state"));
        user.setCap(Integer.parseInt(req.getParameter("postcode")));
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("UPDATE UTENTE SET EMAIL = ?, INDIRIZZO = ?, APPARTAMENTO = ?, CITTA = ?, STATO = ?, CAP = ? WHERE USERNAME = ?");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getIndirizzo());
            ps.setString(3, user.getAppartamento());
            ps.setString(4, user.getCitta());
            ps.setString(5, user.getStato());
            ps.setInt(6, user.getCap());
            ps.setString(7, user.getUsername());
            ps.execute();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
