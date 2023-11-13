package com.boysozoku.controller;

import com.boysozoku.model.UserBean;
import com.boysozoku.model.enums.Console;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "updatePropic", value = "/update-propic")
@MultipartConfig(
        fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5,
        maxRequestSize=1024*1024*5*5)
public class UpdatePropic extends HttpServlet {
    private static final String UPLOAD_DIR = "userimg";
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
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        for (Part part : req.getParts()) {
            inputStream = req.getPart(part.getName()).getInputStream();
            int i = inputStream.available();
            byte[] b = new byte[i];
            inputStream.read(b);

            String fileName = getFileName(part);
            UserBean utente = ((UserBean) req.getSession().getAttribute("userBean"));
            String username = utente.getUsername();
            String extension = fileName.substring((fileName.lastIndexOf(".")));
            if (!fileName.isEmpty()) {
                fileOutputStream = new FileOutputStream(uploadFilePath + "/" + username + "." + extension);
                fileOutputStream.write(b);
                fileOutputStream.close();
                utente.setPropic(new File(uploadFilePath + "/" + username + "." + extension));
            }
            inputStream.close();
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps =
                        con.prepareStatement("UPDATE UTENTE SET PROPIC = ? WHERE USERNAME = ?");
                ps.setString(1, uploadFilePath + "/" + username + "." + extension);
                ps.setString(2, username);
                ps.execute();
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
            resp.sendRedirect("./userpage.jsp");
        }
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
