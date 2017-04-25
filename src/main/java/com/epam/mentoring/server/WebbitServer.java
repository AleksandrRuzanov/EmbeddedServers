package com.epam.mentoring.server;

import com.epam.mentoring.info.InfoWebSockets;
import com.epam.mentoring.info.InfoProvider;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.handler.EmbeddedResourceHandler;
import org.webbitserver.handler.StaticFileHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Created by Aleksandr_Ruzanov on 24.04.2017.
 */
public class WebbitServer {
    public WebbitServer() throws ExecutionException, InterruptedException {
        final InfoProvider infoProvider = new InfoProvider();
        WebServer webServer = WebServers.createWebServer(8282)
               // .add(new StaticFileHandler("/web"))
                .add(new EmbeddedResourceHandler("web"))
                .add("/infoMemory", new InfoWebSockets(infoProvider));

        webServer.start().get();
        System.out.println("Server running at " + webServer.getUri());
        ExecutorService webThread = newSingleThreadExecutor();
        infoProvider.pushPeriodicallyOn(webThread);
    }
}
