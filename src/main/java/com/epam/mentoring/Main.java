package com.epam.mentoring;

import com.epam.mentoring.server.WebbitServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aleksandr_Ruzanov on 24.04.2017.
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new WebbitServer();
    }
}
