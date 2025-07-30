package com.cloudstorage.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String clientId = request.getParameter("clientId");
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();

        String uploadPath = "C:/uploaded_files/" + fileName; // 🛠 Change to your directory

        try (InputStream fileContent = filePart.getInputStream();
             FileOutputStream fos = new FileOutputStream(uploadPath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Upload success for client: " + clientId);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Upload failed");
        }
    }
}




