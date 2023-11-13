package com.boysozoku.model;

import com.boysozoku.controller.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    static List<UserBean> userList = new ArrayList<>();
    public void dao() throws SQLException {
        userList = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT USERNAME, EMAIL, INDIRIZZO, APPARTAMENTO, CITTA, STATO, CAP, PROPIC FROM UTENTE");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userList.add(new UserBean(rs.getString("USERNAME"), rs.getString("EMAIL"), rs.getString("INDIRIZZO"), rs.getString("APPARTAMENTO"), rs.getString("CITTA"), rs.getString("STATO"), rs.getInt("CAP"), rs.getString("PROPIC")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsernameFromMail(String email){
        return userList.stream().filter(userBean -> userBean.getEmail().equals(email)).findAny().get().getUsername();
    }
    public List<UserBean> getUserList() {
        return userList;
    }
}
