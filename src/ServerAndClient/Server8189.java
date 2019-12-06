package ServerAndClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server8189 {
    static ServerSocket server = null;

    public static void main(String[] args) {


        try {
            server = new ServerSocket(8189);
            System.out.println("Server started!!!");

            Client8189.socket = server.accept();
            System.out.println("Client connected!");

            if (Client8189.socket!=null) {
                Scanner in = new Scanner(Client8189.socket.getInputStream());
                PrintWriter out = new PrintWriter(Client8189.socket.getOutputStream(), true);
                Scanner console = new Scanner(System.in);

                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            String str = in.nextLine();
                            if ((str.equals("/end"))) {
                                out.println("/end");
                                 break;
                            }
                            System.out.println("Client: " + str);
                        }
                    }
                });
                t1.start();

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            System.out.println("Type the server message:");
                            String str = console.nextLine();
                            System.out.println("Server message has been sent!");
                            out.println(str);
                        }
                    }
                });
                t2.setDaemon(true);
                t2.start();

                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (Client8189.socket!=null) {Client8189.socket.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
               if (server!=null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
