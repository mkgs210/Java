import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static ArrayList<Point> points = new ArrayList<Point>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(50001);
        ServerSocket server2 = new ServerSocket(50002);
        System.out.println("Server started!");
        while (true) {
            Socket socket = server.accept();
            Socket socket2 = server2.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream objReader = new ObjectInputStream(socket2.getInputStream());
            ObjectOutputStream outStream = new ObjectOutputStream(socket2.getOutputStream());


            new Thread(()->{
                String request = null;
                try {
                    request = reader.readLine();
                    System.out.println("Request_1: "+request);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    points = (ArrayList<Point>) objReader.readObject();
                    System.out.println("Request_2: "+points);
                    String response = "HTTP/1.1 200 OK\nContent-type: text/html\n\n<h1>"+points+"</h1>\n";
                    Thread.sleep(4000);
                    System.out.println("Responce: " + response);
                    writer.write(response);
                    writer.newLine();
                    writer.flush();
                }
                catch (IOException |ClassNotFoundException |InterruptedException e) {throw new RuntimeException(e);}
            }).start();

        }
    }
}






