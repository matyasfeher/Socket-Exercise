/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EchoServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class Server {

    static int port = 8080;
    static String ip = "localhost";

    public static void handleClient(Socket s) {
        String[] split;

        try {
            Scanner scan = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            String msg;

            while (scan.hasNextLine()) {
                msg = scan.nextLine();
                split = msg.split("#");

                if (msg.startsWith(ProtocolStrings.UPPER)) {
                    pw.println(split[1].toUpperCase());
                    System.out.println(split[1].toUpperCase());
                }
                if (msg.startsWith(ProtocolStrings.LOWER)) {
                    pw.println(split[1].toLowerCase());
                }
                if (msg.startsWith(ProtocolStrings.REVERSE)) {
                    pw.println(new StringBuffer(split[1]).reverse().toString());
                }
                if (msg.startsWith(ProtocolStrings.TRANSLATE)) {// ENG -> HUN
                    if (split[1].equals("dog")) {
                        pw.println("kutya");

                    }
                }

                if(!split[0].contains(ProtocolStrings.UPPER)){}
                    scan.close();
                    pw.close();
                    s.close();
                

            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);

        }
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, port));
        System.out.println("Server started" + port + "bound to " + ip);
        while (true) {
            Socket socket = ss.accept();
            System.out.println("New client connected");
            handleClient(socket);
        }

    }
}
