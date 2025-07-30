package com.cloudstorage.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Files;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientId = request.getParameter("clientId");
        String fileName = request.getParameter("fileName");

        if (clientId == null || fileName == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("❌ Missing parameters.");
            return;
        }

        File file = new File("uploads/" + clientId + "/" + fileName);

        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("❌ File not found.");
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
