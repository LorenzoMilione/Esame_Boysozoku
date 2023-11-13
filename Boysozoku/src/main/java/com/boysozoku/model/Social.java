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

public class Social {
    List<Post> postList;

    public List<Post> getPostList() {
        return postList;
    }
    public List<Post> getPostListByUser(String username) {
        return postList.stream().filter(post -> post.getUser().equals(username)).toList();
    }

    public void dao() {
        postList = new ArrayList<>();

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select * from PUBBLICAZIONE");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    PreparedStatement ps1 =
                            con.prepareStatement("select * from BUILD where ID=?");
                    ps1.setInt(1, rs.getInt("ID_BUILD_PUBBLICAZIONE"));
                    ResultSet rs1 = ps1.executeQuery();
                    BuildBean build = new BuildBean();
                    while (rs1.next()){
                        build.setName(rs1.getString("NOME"));
                        build.setConsole(Console.valueOf(rs1.getString("CONSOLE")));
                        if (rs1.getString("FILE_BUILD")!=null)
                            build.setCustom(new File(rs1.getString("FILE_BUILD")));
                        build.setScreen(BuildDAO.screenList.stream().filter(screen -> {
                            try {
                                return screen.id== rs1.getInt("ID_SCHERMO");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).findAny());
                        build.setShell(BuildDAO.shellList.stream().filter(shell -> {
                            try {
                                return shell.id== rs1.getInt("ID_SCOCCA");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).findAny());
                        build.setButtons(BuildDAO.buttonsList.stream().filter(buttons -> {
                            try {
                                return buttons.id== rs1.getInt("ID_PULSANTI");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).findAny());
                        build.setPrice(rs1.getFloat("PREZZO"));
                    }
                    postList.add(new Post(build, rs.getDate("DATA_PUBBLICAZIONE"), rs.getString("USERNAME_PUBBLICAZIONE")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}