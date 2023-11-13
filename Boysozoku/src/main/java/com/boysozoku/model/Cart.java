package com.boysozoku.model;

import com.boysozoku.controller.ConPool;
import com.boysozoku.model.enums.Console;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    String ownerUsername = null;
    List<BuildBean> buildsInCart;
    float totalPrice = 0;

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public List<BuildBean> getBuildsInCart() {
        return buildsInCart;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void addToTotal(float buildPrice){
        totalPrice += buildPrice;
    }

    public void dao() {
        buildsInCart = new ArrayList<>();

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select BUILD.NOME, BUILD.CONSOLE, BUILD.FILE_BUILD, BUILD.ID_SCOCCA, BUILD.ID_SCHERMO, BUILD.ID_PULSANTI, BUILD.PREZZO, BUILD_CARRELLO.DA_MONTARE FROM BUILD, BUILD_CARRELLO WHERE BUILD_CARRELLO.UTENTE_CARRELLO = ? AND BUILD.ID = BUILD_CARRELLO.ID_BUILD_CARRELLO");
            ps.setString(1, ownerUsername);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BuildBean build = new BuildBean();
                int shellid = rs.getInt(4);
                int screenid = rs.getInt(5);
                int buttonsid = rs.getInt(6);

                build.setName(rs.getString(1));
                build.setConsole(Console.valueOf(rs.getString(2)));
                if (rs.getString(3)!=null)
                    build.setCustom(new File(rs.getString(3)));
                build.setShell(BuildDAO.shellList.stream().filter(shell -> shell.id == shellid).findFirst());
                build.setScreen(BuildDAO.screenList.stream().filter(screen -> screen.id == screenid).findFirst());
                build.setButtons(BuildDAO.buttonsList.stream().filter(buttons -> buttons.id == buttonsid).findFirst());
                build.setMount(rs.getBoolean(8));
                build.setPrice(rs.getFloat(7));
                buildsInCart.add(build);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}