package com.example;

import java.net.Socket;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientHandler extends Thread {
    private Socket connection;
    private DataOutputStream out;
    private SharedResource sr;
    private Scanner scan;
    private String nickname;

    public ClientHandler(Socket connection, SharedResource sr) throws IOException {
        this.connection = connection;
        this.sr = sr;
        this.scan = new Scanner(System.in);
        out = new DataOutputStream(connection.getOutputStream());
    }

    private void nicknameHandler() throws Exception {
        // aspetta l'invio del messaggio da parte del server
        Thread.sleep(100);
        String msg = sr.getMessage();
        if (msg.equals("name")) {
            System.out.println("Inserisci il nickname: ");
            String nickname = scan.nextLine();
            this.nickname = nickname;
            out.writeBytes("nickname," + nickname + "\n");
            Thread.sleep(50);
            
            // se la risposta del server e' n, significa che il nickname esiste di gia'
            if (sr.getMessage().equals("n")) {
                System.out.println("Il nome esiste di gia'...");
                nicknameHandler();
            }
        }
    }

    private void rules() {
        System.out.println("-------------------");
        System.out.println("@all messaggio");
        System.out.println("@nickname messaggio");
        System.out.println("@quit");
        System.out.println("-------------------");
    }

    public void run() {
        try {
            // obbliga all'utente di inserire prima il nickname
            nicknameHandler();
            
            // stampe regoali
            System.out.println("Benvenutə, " + nickname + "!");
            rules();

            while (true) {
                String userInput = scan.nextLine();
                String[] userInputSplit = userInput.split(" ");
                String cmd = userInputSplit[0].trim();
                
                // ricostruisce il messaggio originale
                String originalMessage = "";
                for (int i = 1; i < userInputSplit.length; i++) {
                    originalMessage += userInputSplit[i] + " ";
                }

                switch (cmd) {
                    case "@all": {
                        out.writeBytes("all," + originalMessage + "\n");
                        // aspetta la risposta del server
                        Thread.sleep(500);
                        String result = sr.getMessage();
                        if (result.equals("n")) {
                            System.out.println("Server a puttane.");
                        }
                        break;
                    }
                    case "@quit":
                        out.writeBytes("quit\n");
                        System.out.println("Arrivederci!");
                        Thread.sleep(100);
                        System.exit(0);
                    default: {
                        // toglie la chiocciola
                        String targetNickname = cmd.substring(1);
                        out.writeBytes(targetNickname + "," + originalMessage + "\n"); 
                        // aspetta la risposta del server
                        Thread.sleep(500);
                        
                        // prende la risposta del server
                        String result = sr.getMessage();
                        // se e' n, vale a dire che non esiste un utente con tale nickname
                        if (result.equals("n")) {
                            System.out.println("L'utente e' andato a puttane, non e' raggiungibile."); 
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
