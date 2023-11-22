package com.example;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientHandler {
    private Socket connection;
    private DataOutputStream out;

    public ClientHandler(Socket connection) throws IOException {
        this.connection = connection;
        out = new DataOutputStream(connection.getOutputStream());
    }

}
