package com.boysozoku.model;

import com.boysozoku.controller.ConPool;
import com.boysozoku.model.enums.Console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuildDAO {

    static List<Mod> modList = new ArrayList<>();
    static List<Shell> shellList = new ArrayList<>();
    static List<Screen> screenList = new ArrayList<>();
    static List<Buttons> buttonsList = new ArrayList<>();

    public void dao(Console console) {
        modList = new ArrayList<>();
        shellList = new ArrayList<>();
        screenList = new ArrayList<>();
        buttonsList = new ArrayList<>();

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT ID, TIPO, PREZZO, DESCRIZIONE FROM MOD_BUILD WHERE CONSOLE =?");
            ps.setString(1, console.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Mod mod = null;
                switch (rs.getString("TIPO")){
                    default:
                        break;
                    case "SCOCCA":
                        mod = new Shell(rs.getInt("ID"), console, rs.getFloat("PREZZO"), rs.getString("DESCRIZIONE"));
                        break;
                    case "SCHERMO":
                        mod = new Screen(rs.getInt("ID"), console, rs.getFloat("PREZZO"), rs.getString("DESCRIZIONE"));
                        break;
                    case "PULSANTI":
                        mod = new Buttons(rs.getInt("ID"), console, rs.getFloat("PREZZO"), rs.getString("DESCRIZIONE"));
                        break;
                }
                modList.add(mod);
            }

            shellList = modList.stream().filter(mod -> mod instanceof Shell).map(mod -> (Shell) mod).collect(Collectors.toList());
            screenList = modList.stream().filter(mod -> mod instanceof Screen).map(mod -> (Screen) mod).collect(Collectors.toList());
            buttonsList= modList.stream().filter(mod -> mod instanceof Buttons).map(mod -> (Buttons) mod).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Shell> getShellList() {
        return shellList;
    }

    public List<Screen> getScreenList() {
        return screenList;
    }

    public List<Buttons> getButtonsList(){
        return buttonsList;
    }

}
