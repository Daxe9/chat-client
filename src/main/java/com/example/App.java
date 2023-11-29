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
                if (serverResponse == null) {
                    System.exit(1);
                }
                if (serverResponse.contains(",")) {
                    String[] serverResponseSplit = serverResponse.split(",");
                    String originalMessage = "";
                    for (int i = 1; i < serverResponseSplit.length; i++) {
                        originalMessage += serverResponseSplit[i] + ",";
                    }
                    System.out.println("[" + serverResponseSplit[0] + "]: " + originalMessage);
                }
                sr.setMessage(serverResponse);
            }

            /*
             * sempre caro mi fu questo ermo colle
             * e questa siepe che,
             * da tanta parte dell'ultimo orizzontre il guardo esclude. ma sedendo e mirando
             * interminati spazi e sovrumani silenzi e profondissima quiete io nel pensier
             * mi fingo ove per poco il cor non si spaura
             */

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }
    }
}
