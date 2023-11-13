package com.boysozoku.controller;

import com.boysozoku.model.BuildBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

@WebServlet(name = "removefromcart", value = "/removefromcart")
public class RemoveFromCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BuildBean build = BuildBean.fromString(req.getParameter("item"));
        int id_build = -1;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps1 = con.prepareStatement("SELECT ID FROM BUILD WHERE NOME = ? AND ID_PULSANTI = ? AND ID_SCHERMO = ? AND ID_SCOCCA = ? AND CONSOLE = ? LIMIT 1");

            ps1.setString(1, build.getName());

            if(build.getButtons().isPresent())
                ps1.setInt(2, build.getButtons().get().getId());
            else
                ps1.setNull(2, Types.INTEGER);

            if(build.getScreen().isPresent())
                ps1.setInt(3, build.getScreen().get().getId());
            else
                ps1.setNull(3, Types.INTEGER);

            if(build.getShell().isPresent())
                ps1.setInt(4, build.getShell().get().getId());
            else
                ps1.setNull(4, Types.INTEGER);

            ps1.setString(5, build.getConsole().name());

            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()){
                id_build = rs1.getInt("ID");
            }

            PreparedStatement ps2 = con.prepareStatement("DELETE FROM BUILD_CARRELLO WHERE ID_BUILD_CARRELLO = ?");
            ps2.setInt(1, id_build);
            ps2.execute();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        resp.sendRedirect("./cart.jsp");
    }
}
