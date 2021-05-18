package zad1.languagesServers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

abstract public class LanguageServer {

    abstract public String translate(String word) throws Exception;

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8090);

        System.out.println("Language Server waiting for Main Server on 8090");
        Socket socket = serverSocket.accept();
        System.out.println("Language Server connected Main Server on 8090");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String rawData = bufferedReader.readLine();
        bufferedReader.close();
        socket.close();
        serverSocket.close();

        System.out.println("Language Server received \"" + rawData + "\" from Main Server");
        String[] data = rawData.split(";");
        String translatedWord = translate(data[0]);

        System.out.println("Language Server trying to connect to port " + data[1]);
        socket = new Socket("localhost", Integer.parseInt(data[1]));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.print(translatedWord + '\n');
        out.flush();
        out.close();
        socket.close();
    }
}
