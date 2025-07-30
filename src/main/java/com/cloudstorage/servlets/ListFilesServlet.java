package com.cloudstorage.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/list")
public class ListFilesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientId = request.getParameter("clientId");

        if (clientId == null || clientId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("❌ Client ID is required.");
            return;
        }

        File dir = new File("uploads/" + clientId);
        File[] files = dir.exists() ? dir.listFiles() : new File[0];

        response.setContentType("text/plain");

        if (files != null && files.length > 0) {
            for (File file : files) {
                response.getWriter().println("📄 " + file.getName() + " (" + file.length() + " bytes)");
            }
        } else {
            response.getWriter().println("📁 No files found for client: " + clientId);
        }
    }
}

