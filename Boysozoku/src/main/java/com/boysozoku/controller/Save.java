package com.boysozoku.controller;

import com.boysozoku.model.BuildBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet(name = "save", value = "/save")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class Save extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        BuildBean build = (BuildBean) req.getSession().getAttribute("build");

        File buildfile = build.toFile();
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename="+buildfile.getName());

        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(buildfile);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > -1){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
        buildfile.delete();
    }
}