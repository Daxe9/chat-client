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

    private void nicknameHandler() throws IOException {
        String msg = sr.getMessage();
        if (msg.equals("name")) {
            System.out.println("Inserisci il nickname: ");
            String nickname = scan.nextLine();
            this.nickname = nickname;
            out.writeBytes("nickname," + nickname + "\n");
        }
    }

    private void rules() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /*
     * @all ciao a tutti
     * 
     * @pippo ciao pippo
     */
    /*
     * TODO:
     * controllo su un nickname gia' esistente
     * comunicare p2p
     * comunicare in broadcast
     * 
     */
    public void run() {
        try {
            Thread.sleep(1000);
            nicknameHandler();
            System.out.println("Benvenutə, " + nickname + "!");
            // rules();

            while (true) {
                String userInput = scan.nextLine();
                String[] userInputSplit = userInput.split(" ");
                String cmd = userInputSplit[0].trim();
                String originalMessage = "";
                for (int i = 1; i < userInputSplit.length; i++) {
                    originalMessage += userInputSplit[i] + " ";
                }

                switch (cmd) {
                    case "@all":
                        out.writeBytes("all," + originalMessage + "\n");
                        break;

                    case "@quit":

                        break;
                    default:
                        String targetNickname = cmd.substring(1);
                        out.writeBytes(targetNickname + "," + originalMessage + "\n"); 
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
