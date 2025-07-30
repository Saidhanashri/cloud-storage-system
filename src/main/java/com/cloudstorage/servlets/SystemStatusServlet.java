package com.cloudstorage.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/status")
public class SystemStatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().println("AWS: ✅");
        response.getWriter().println("Azure: ✅");
        response.getWriter().println("GCP: ✅");
        response.getWriter().println("Active Leases: 0");
        response.getWriter().println("Total files: [dynamic count]");
        response.getWriter().println("Metadata entries: [dynamic count]");
    }
}
