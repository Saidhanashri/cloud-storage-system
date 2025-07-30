package com.cloudstorage.servlets;

import com.cloudstorage.servlets.UploadServlet;
import com.cloudstorage.CorsFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.*;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import java.util.EnumSet;


public class JettyLauncher {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");

        // Upload servlet with multipart support
        ServletHolder uploadHolder = new ServletHolder(new UploadServlet());
        uploadHolder.getRegistration().setMultipartConfig(
                new MultipartConfigElement(System.getProperty("java.io.tmpdir")));
        servletContext.addServlet(uploadHolder, "/upload");

        // Other servlets
        servletContext.addServlet(new ServletHolder(new DownloadServlet()), "/download");
        servletContext.addServlet(new ServletHolder(new ListFilesServlet()), "/list");
        servletContext.addServlet(new ServletHolder(new ShareFileServlet()), "/share");
        servletContext.addServlet(new ServletHolder(new StatusServlet()), "/status");
        servletContext.addServlet(new ServletHolder(new CleanupServlet()), "/cleanup-temp");
        servletContext.addServlet(new ServletHolder(new ClearLeaseServlet()), "/clear-leases");
        servletContext.addServlet(new ServletHolder(new VerifyDataServlet()), "/verify-data");

        // ✅ CORS Filter added to the correct handler
        FilterHolder cors = new FilterHolder(new CorsFilter());
        servletContext.addFilter(cors, "/*", EnumSet.of(DispatcherType.REQUEST));

        // Serve static Angular files from "web" directory
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase("web");

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(servletContext);  // this now includes CORS

        server.setHandler(handlers);
        server.start();
        System.out.println("✅ Jetty started at http://localhost:8080");
        server.join();
    }
}

