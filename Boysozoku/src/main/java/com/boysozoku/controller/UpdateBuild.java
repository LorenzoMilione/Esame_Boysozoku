package com.boysozoku.controller;

import com.boysozoku.model.BuildDAO;
import com.boysozoku.model.BuildBean;
import com.boysozoku.model.enums.Console;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.Optional;

@WebServlet(name = "updateBuild", value = "/update-build")
@MultipartConfig(
        fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5,
        maxRequestSize=1024*1024*5*5)

public class UpdateBuild extends HttpServlet {
    private static final String UPLOAD_DIR = "buildimg";
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // gets absolute path of the web application
        String applicationPath = req.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + UPLOAD_DIR;
        // creates the save directory if it does not exist
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
        BuildDAO buildDAO = (BuildDAO) req.getSession().getAttribute("buildDAO");
        BuildBean build = (BuildBean) req.getSession().getAttribute("build");
        String consoleName = req.getParameter("console");
        build.setName(req.getParameter("presetname"));
        String shellName = req.getParameter("shell");
            if(shellName == null){
                shellName = "NONE";
            }
        String screenName = req.getParameter("screen");
        if(screenName == null){
           screenName = "NONE";
        }
        String buttonsName = req.getParameter("buttons");
        if(buttonsName == null){
            buttonsName = "NONE";
        }
        if (!shellName.equals("NONE")) {
            String finalShellName = shellName;
            build.setShell(Optional.of(buildDAO.getShellList().stream().filter(shell -> shell.getName().equals(finalShellName)).findAny().get()));
            if (build.getShell().isPresent() && !(build.getShell().get().getName().equals("Custom UV Printed Shell")))
                build.setCustom(null);
        }
        if (!screenName.equals("NONE")) {
            String finalScreenName = screenName;
            build.setScreen(Optional.of(buildDAO.getScreenList().stream().filter(screen -> screen.getName().equals(finalScreenName)).findAny().get()));
        }

        if (!buttonsName.equals("NONE")) {
            String finalButtonsName = buttonsName;
            build.setButtons(Optional.of(buildDAO.getButtonsList().stream().filter(buttons -> buttons.getName().equals(finalButtonsName)).findAny().get()));
        }
        build.setMount(Boolean.valueOf(req.getParameter("mount")));

            InputStream inputStream;
            FileOutputStream fileOutputStream;
            for (Part part : req.getParts()) {
                inputStream = req.getPart(part.getName()).getInputStream();
                int i = inputStream.available();
                byte[] b = new byte[i];
                inputStream.read(b);

                String fileName = getFileName(part);
                if (!fileName.isEmpty()) {
                    fileOutputStream = new FileOutputStream(uploadFilePath + "/" + fileName);
                    fileOutputStream.write(b);
                    fileOutputStream.close();
                    build.setCustom(new File(uploadFilePath + "/" + fileName));
                    build.setShell(buildDAO.getShellList().stream().filter(shell -> shell.getName().contains("Custom") && shell.getConsole().equals(Console.valueOf(consoleName))).findAny());
                }
                inputStream.close();
            }
        build.setPrice(BuildBean.calcFullPrice(build));
        req.getSession().setAttribute("build", build);
        resp.sendRedirect("./buy_page.jsp?console="+consoleName);
    }

    private String getFileName(Part part) {

        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}
