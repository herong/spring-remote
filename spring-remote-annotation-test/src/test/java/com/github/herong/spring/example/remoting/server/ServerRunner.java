package com.github.herong.spring.example.remoting.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ServerRunner {

    private static Server server;

    @BeforeClass
    public static void startWebapp() throws Exception {
        server = new Server();

        Connector connector = new SelectChannelConnector();
        connector.setPort(8080);

        server.addConnector(connector);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/remoting");

        webAppContext.setWar("src/main/webapp");

        server.setHandler(webAppContext);

        server.start();
    }

    @AfterClass
    public static void stopWebapp() throws Exception {
        server.stop();
    }
}
