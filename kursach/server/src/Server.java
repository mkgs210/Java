import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Server {
    private static NeuralNetwork nn;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(50005);
        ServerSocket server2 = new ServerSocket(50006);
        //ServerSocket server3 = new ServerSocket(50003);
        String connectionUrl =
                "jdbc:sqlserver://yourserver.database.windows.net:1433;"
                        + "database=AdventureWorks;"
                        + "user=yourusername@yourserver;"
                        + "password=yourpassword;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        System.out.println("Server started!");

        dots(server, server2);
    }
    private static void dots(ServerSocket server, ServerSocket server2) throws IOException {
        Socket socket = server.accept();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true) {
            new Thread(()->{
                String request = null;
                try {
                    request = reader.readLine();
                    System.out.println("Request: "+request);
                    String[] parts = request.split(">");

                    if (Objects.equals(parts[0], "NeuralNetwork")){
                        System.out.println("NeuralNetwork");
                        ArrayList<Integer> ints = new ArrayList<Integer>();
                        for (int i = 3; i < parts.length; i++){
                            ints.add(Integer.valueOf(parts[i]));
                        }
                        nn = new NeuralNetwork(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), ints);

                        //System.out.println(nn);
                    }System.out.println("2");
                    if (Objects.equals(parts[0], "backpropagation")){
                        System.out.println("backpropagation1");

                        Socket socket2 = server2.accept();
                        ObjectInputStream objReader = new ObjectInputStream(socket2.getInputStream());

                        ArrayList<Point> points = (ArrayList<Point>) objReader.readObject();
                        System.out.println("backpropagation2");
                        System.out.println("Request_2: "+points);
                        System.out.println("Request_2: "+points.size());
                        for (int k = 0; k < Integer.valueOf(parts[1]); k++) {//parts[0]=epoch

                            Point p = points.get((int) (Math.random() * points.size()));//?????????? ?????????????????? ???????????????
                            double nx = (double) p.x / Integer.valueOf(parts[3]) - 0.5;
                            double ny = (double) p.y / Integer.valueOf(parts[2]) - 0.5;
                            nn.feedForward(new double[]{nx, ny});//???????????????? ?????????????? ???????????????????? ???????? ?????????? ?????????????? ??????????????????
                            double[] targets = new double[2];
                            if (p.type == 0){
                                targets[0] = 1;
                            }else{
                                targets[1] = 1;
                            }
                            nn.backpropagation(targets);
                        }
                        writer.write("nn success");
                        writer.newLine();
                        writer.flush();
                    }System.out.println("3");
                    if (Objects.equals(parts[0], "square")){
                        System.out.println("square");
                        Integer square = Integer.valueOf(parts[1]);
                        Integer h = Integer.valueOf(parts[2]);
                        Integer w = Integer.valueOf(parts[3]);

                        ArrayList <Integer> listik = new ArrayList<>();
                        for (int i = 0; i < w / square; i++) {//?????????? ???? ????????????????
                            for (int j = 0; j < h / square; j++) {
                                double nx = (double) i / w * square - 0.5;//???????????????? ???????????????????? ????????????????????
                                double ny = (double) j / h * square - 0.5;
                                //Double output = dis.readDouble();//???????????????????? ?? ???????????? ???????????? ?????????????????????? ??????????????????
                                double[] outputs = nn.feedForward(new double[]{nx, ny});
                                double orange = Math.max(0, Math.min(1, outputs[0]-outputs[1] + 0.6));
                                double pink = 1 - orange;
                                orange = 0.3 + orange * 0.5;
                                pink = 0.5 + pink * 0.5;
                                int color = (240 << 16) | ((int)(orange * 255) << 8) | (int)(pink * 255);
                                listik.add(color);
                            }
                        }
                        Socket socket2 = server2.accept();
                        ObjectOutputStream outStream = new ObjectOutputStream(socket2.getOutputStream());

                        outStream.writeObject(listik);
                        outStream.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}






