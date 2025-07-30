package com.cloudstorage.servlets;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/share")
public class ShareFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("✅ File shared.");
    }
}
