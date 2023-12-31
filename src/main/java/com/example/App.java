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

            // il main thread rimane in ascolto di message in arrivo
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (true) {
                String serverResponse = in.readLine();
                // controlla se il socket e' stato terminato dal server
                if (serverResponse == null) {
                    System.exit(1);
                }

                // controlla se la risposta ricevuto sia effettivamente un message da parte di altri client
                if (serverResponse.contains(",")) {
                    String[] serverResponseSplit = serverResponse.split(",");

                    // trova la posizione della prima virgola, e trova il contenuto del message facendo un substring di index + 1
                    int firstCommaIndex = serverResponse.indexOf(",");
                    String originalMessage = serverResponse.substring(firstCommaIndex + 1);
                    System.out.println("[" + serverResponseSplit[0] + "]: " + originalMessage);
                } else {
                    // immagazzina la risposta ricevuta dal server
                    sr.setMessage(serverResponse);
                }
            }

            /*
             * L'infinito - Giacomo Brussi
             * 
             * Sempre caro mi fu questo ermo colle,
             * e questa siepe, che da tanta parte
             * dell'ultimo orizzonte il guardo esclude.
             * Ma sedendo e mirando, interminati
             * spazi di là da quella, e sovrumani 
             * silenzi, e profonddissima quiete
             * io nel pesneir mi fingo; ove per poho
             * il cor non si spaura. E come il vento 
             * odo stormir tra queste piante, io quello
             * infinito silenzio a questa voce
             * vo comparando: e mi sovvien l'eterno,
             * e le morte stagioni, e la presente 
             * e viva, e il suon di lei. Così tra questa 
             * immensità s'annega il pensier mio:
             * e il naufragar m'è dolce in questo mare. 
             */

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }
    }
}
