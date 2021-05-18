/**
 *
 * @author Dylewski Mateusz s18737
 *
 */

package zad1;

public class Main   {

    public static void main(String[] args) {

        Thread mainServerThread = new Thread(() -> {
            try {
                new MainServer().startServer("8080");
            } catch (Exception ex) {
                System.out.println("Thread Main Server: " + ex);
            }
        });
        mainServerThread.start();
        System.out.println("MainServer started");

        Thread clientThread = new Thread(() -> {
            try {
                new Client().startClient("8080");
            } catch (Exception ex) {
                System.out.println("Thread Client: " + ex);
            }
        });
        if(mainServerThread.isAlive()) {
            clientThread.start();
            System.out.println("Client started");
        }
    }
}