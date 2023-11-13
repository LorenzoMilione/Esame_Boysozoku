package com.boysozoku.controller;

import com.boysozoku.model.BuildDAO;
import com.boysozoku.model.BuildBean;
import com.boysozoku.model.enums.Console;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet(name = "load", value = "/load")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class Load extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String consoleName = req.getParameter("console");
        BuildDAO buildDAO = (BuildDAO) req.getSession().getAttribute("buildDAO");
        BuildBean result = new BuildBean();
        Part buildPart = req.getPart("load");
        String filename = buildPart.getSubmittedFileName();
        InputStream fileContent = buildPart.getInputStream();
        BufferedReader reader = new BufferedReader( new InputStreamReader(fileContent, StandardCharsets.UTF_8));
        result.setName(reader.readLine());
        String shellName = reader.readLine();
        result.setShell(Optional.of(buildDAO.getShellList().stream().filter(shell -> shell.getName().equals(shellName)).findAny().get()));
        String screenName = reader.readLine();
        result.setScreen(Optional.of(buildDAO.getScreenList().stream().filter(screen -> screen.getName().equals(screenName)).findAny().get()));
        String buttonsName = reader.readLine();
        result.setButtons(Optional.of(buildDAO.getButtonsList().stream().filter(buttons -> buttons.getName().equals(buttonsName)).findAny().get()));
        result.setConsole(Console.valueOf(consoleName));
        result.setPrice(BuildBean.calcFullPrice(result));
        String check = reader.readLine();
        if (check!=null)
            result.setCustom(new File(check));
        fileContent.close();
        reader.close();
        new File(filename).delete();
        req.getSession().setAttribute("build", result);
        resp.sendRedirect("buy_page.jsp?console="+consoleName);
    }
}
