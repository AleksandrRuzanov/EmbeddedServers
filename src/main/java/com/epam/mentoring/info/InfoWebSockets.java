package com.epam.mentoring.info;

import org.webbitserver.EventSourceConnection;
import org.webbitserver.EventSourceHandler;

/**
 * Created by Aleksandr_Ruzanov on 24.04.2017.
 */

public class InfoWebSockets implements EventSourceHandler {
    private InfoProvider infoProvider;

    public InfoWebSockets(InfoProvider infoProvider) {
        this.infoProvider = infoProvider;
    }

    @Override
    public void onOpen(EventSourceConnection connection) throws Exception {
        infoProvider.addConnection(connection);
    }

    @Override
    public void onClose(EventSourceConnection connection) throws Exception {
        infoProvider.removeConnection(connection);
    }
}