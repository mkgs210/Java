import java.net.Socket;
import java.io.*;
import java.util.ArrayList;

public class Client {
    private static ArrayList<Point> points = new ArrayList<Point>();

    public static void main(String[] args) {

        while(true){
            System.out.println("start client");
            try {
                System.out.println("start connect...");
                Socket socket = new Socket("127.0.0.1", 50001);
                Socket socket2 = new Socket("127.0.0.1", 50002);
                System.out.println("connect to server");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
                ObjectOutputStream outStream = new ObjectOutputStream(socket2.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket2.getInputStream());

//                String request = "Asda";

                writer.write("backpropagation>");
                writer.newLine();
                writer.flush();
                points.add(new Point(16, 38, 0));
                points.add(new Point(26, 48, 1));
                points.add(new Point(6, 8, 1));

                System.out.println("from client to rerver: "+points);
                outStream.writeObject(points);
                System.out.println("success");
                outStream.flush();
        //        writer.write(request);
        //        writer.newLine();
        //        writer.flush();

                String response = reader.readLine();
                System.out.println("from server to client: "+response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}