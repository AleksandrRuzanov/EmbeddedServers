package com.epam.mentoring.info;

import org.webbitserver.EventSourceConnection;
import org.webbitserver.EventSourceMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.lang.Thread.sleep;

/**
 * Created by Aleksandr_Ruzanov on 24.04.2017.
 */
public class InfoProvider {

    private List<EventSourceConnection> connections = new ArrayList<EventSourceConnection>();
    private int count = 1;

    public void addConnection(EventSourceConnection connection) {
        connection.data("id", count++);
        connections.add(connection);
        sendInfoToSocket("Client " + connection.data("id") + " connect");
    }

    public void removeConnection(EventSourceConnection connection) {
        connections.remove(connection);
        sendInfoToSocket("Client " + connection.data("id") + " left");
    }

    private void sendInfoToSocket(String message) {
        for (EventSourceConnection connection : connections) {
            connection.send(new EventSourceMessage(message));
        }
    }

    public void pushPeriodicallyOn(ExecutorService webThread) throws InterruptedException, ExecutionException {
        while (true) {
            sleep(1000);
            webThread.submit(new Runnable() {
                @Override
                public void run() {
                    sendInfoToSocket(getData());
                }
            }).get();
        }
    }

    private String getData() {
        StringBuilder message = new StringBuilder();
        long heapSize = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        message.append("Connectons count: ").append(connections.size()).append("<br>");
        message.append("Heap Size: ").append(heapSize / 1024).append("M<br>");
        message.append("Heap Memory Usage: ").append((heapSize - freeMemory) / 1024).append("M<br>");
        return message.toString();
    }
}
