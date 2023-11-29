package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            Socket connection = new Socket("127.0.0.1", 42069);
            SharedResource sr = new SharedResource();
            ClientHandler ch = new ClientHandler(connection, sr);
            ch.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (true) {
                String serverResponse = in.readLine();
                System.out.println("[LOG]: " + serverResponse);
                sr.setMessage(serverResponse);
                // TODO: handler upcoming message
            }

            /*
             * sempre caro mi fu questo ermo colle
             * e questa siepe che,
             * da tanta parte dell'ultimo orizzontre il guardo esclude. ma sedendo e mirando
             * interminati spazi e sovrumani silenzi e profondissima quiete io nel pensier
             * mi fuggo ove per poco il cor non si spaura
             */

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }
    }
}
