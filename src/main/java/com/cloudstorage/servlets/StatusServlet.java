package com.cloudstorage.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/status")
public class StatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");

        // Simulated system status values
        String awsStatus = "✅ Online";
        String azureStatus = "✅ Online";
        String gcpStatus = "✅ Online";

        // Simulated file/metadata data — you can connect to a DB later to make this dynamic
        int activeLeases = 0;
        int totalFiles = 1;
        int metadataEntries = 1;

        // Build and write the response
        StringBuilder status = new StringBuilder();
        status.append("AWS: ").append(awsStatus).append("\n");
        status.append("Azure: ").append(azureStatus).append("\n");
        status.append("GCP: ").append(gcpStatus).append("\n");
        status.append("Active Leases: ").append(activeLeases).append("\n");
        status.append("Total files: ").append(totalFiles).append("\n");
        status.append("Metadata entries: ").append(metadataEntries);

        resp.getWriter().write(status.toString());
    }
}
