package ServerAndClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Client8189 {
    static Socket socket = null;;
    public static void main(String[] args) {
        System.out.println("Waiting for connection...");
        try {

            socket = new Socket("localhost", 8189);
            if (socket!=null) {
                System.out.println("Connected to the server!");
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
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
                            System.out.println("Server message: " + str);
                        }
                    }
                });
                t1.start();

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            System.out.print("Type the client message:");
                            String str = console.nextLine();
                            System.out.println("Message from client has been sent!");
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
        }
          finally {
            try {
                if (socket!=null) {socket.close();socket=null;}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

