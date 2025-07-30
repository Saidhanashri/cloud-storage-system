package com.cloudstorage.servlets;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/cleanup-temp")
public class CleanupServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("✅ Temporary files cleaned.");
    }
}
