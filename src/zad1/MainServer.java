package zad1;

import zad1.languagesServers.DeLanguageServer;
import zad1.languagesServers.EnLanguageServer;
import zad1.languagesServers.EsLanguageServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public ServerSocket serverSocket;
    public int port;

    public void startServer(String port) throws Exception{
        serverSocket = new ServerSocket(Integer.parseInt(port));

        while(true){
            System.out.println("Main Server waiting for Client on 8080");
            Socket socket = serverSocket.accept();
            System.out.println("Main Server accepted Client");


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String rawData = bufferedReader.readLine();
            socket.close();

            String[] data = rawData.split(";");
            System.out.println("Word from client: " + data[0]);
            System.out.println("Language code from client: " + data[1]);
            System.out.println("Port from client: " + data[2]);

            String dataForLanguageServer = data[0] + ";" + data[2];
            Thread languageServerThread;

            switch (data[1]){
                case "EN":
                    languageServerThread = new Thread(() -> {
                        try {
                            new EnLanguageServer().start();
                        } catch (Exception ex){
                            System.out.println("languageServerThread" + ex);
                        }
                    });
                    languageServerThread.start();
                    System.out.println("English Language Server started");
                    break;
                case "ES":
                    languageServerThread = new Thread(() -> {
                        try {
                            new EsLanguageServer().start();
                        } catch (Exception ex){
                            System.out.println("languageServerThread" + ex);
                        }
                    });
                    languageServerThread.start();
                    System.out.println("Spanish Language Server started");
                    break;
                case "DE":
                    languageServerThread = new Thread(() -> {
                        try {
                            new DeLanguageServer().start();
                        } catch (Exception ex){
                            System.out.println("languageServerThread" + ex);
                        }
                    });
                    languageServerThread.start();
                    System.out.println("Germany Language Server started");
                    break;
            }
            sendDataToLanguageServer(dataForLanguageServer);
        }
    }

    public void sendDataToLanguageServer(String rawData) throws Exception{
        Socket s = new Socket("localhost", 8090);
        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.print(rawData + '\n');
        out.flush();
        out.close();
        s.close();
    }
}
