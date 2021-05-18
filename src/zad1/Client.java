package zad1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {


    String word;
    String languageCode;
    String translatedWord;
    MyWindow window;

    public void startClient(String port) throws Exception{
        window = new MyWindow();
        Thread guiThread = new Thread(() -> window.startWindow(this));
        while(true) {

            if (!guiThread.isAlive()) {
                guiThread.start();
                System.out.println("Gui started");
            }

            while(word == null) {
                Thread.sleep(100);
            }

            try {
                System.out.println("Client trying to connect to port 8080");
                Socket s = new Socket("localhost", Integer.parseInt(port));

                PrintWriter out = new PrintWriter(s.getOutputStream());
                out.print(word + ";" + languageCode + ";" + 8081 + '\n');
                out.flush();
                s.close();

                ServerSocket serverSocket = new ServerSocket(8081);
                System.out.println("Client waiting on port 8081");
                s = serverSocket.accept();
                System.out.println("Client connected Language Server on port 8081");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                translatedWord = bufferedReader.readLine();
                System.out.println("Client received \"" + translatedWord + "\" from Language Server");
                s.close();
                serverSocket.close();

            } catch (Exception ex) {
                System.out.println("Client" + ex);
            }

            word = null;
        }
    }
}
