import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.sql.* ;


public class NN_server {
    //private static NeuralNetwork nn;

    public static void main(String[] args) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ServerSocket server = new ServerSocket(50005);
        ServerSocket server2 = new ServerSocket(50006);
        //ServerSocket server3 = new ServerSocket(50003);

        System.out.println("Server started!");

        String url = "jdbc:mysql://localhost:3306/java_db";
        String user = "root";
        String pass = "12345";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("MySQL connected");

            Statement stm = con.createStatement();
            stm.executeUpdate("DELETE FROM users;");
            /*ResultSet rs = stm.executeQuery("SELECT * FROM users;");
            while(rs.next()){
                System.out.println(rs.getString(1) + " " +
                        rs.getString(2));
            }*/
            while(true) {
                Thread myThread0 = new Thread(() -> {
                    try {
                        dots(server, server2, con);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                myThread0.start();
            }

        } catch (SQLException | ClassNotFoundException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }

    }

    private static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.flush();
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private static void case1(Connection con, String[] parts, Integer Thread) throws SQLException, IOException {
        System.out.println("NeuralNetwork");
        ArrayList<Integer> ints = new ArrayList<Integer>();
        for (int i = 3; i < parts.length; i++) {
            ints.add(Integer.valueOf(parts[i]));
        }
        NeuralNetwork nn = new NeuralNetwork(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), ints);
        String sql = "INSERT INTO users (thread_id, nn, address) Values (?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(Thread));
        preparedStatement.setString(2, toString(nn));
        preparedStatement.setString(3, nn.toString());
        preparedStatement.executeUpdate();
    }
    private static void case2(Connection con, ServerSocket server2, String[] parts) throws IOException, ClassNotFoundException, SQLException {
        System.out.println("backpropagation1");
        Socket socket2 = server2.accept();
        ObjectInputStream objReader = new ObjectInputStream(socket2.getInputStream());
        ArrayList<Point> points = (ArrayList<Point>) objReader.readObject();
        System.out.println("backpropagation2");
        System.out.println("Request_2: " + points);
        System.out.println("Request_2: " + points.size());

        System.out.println("Number of Thread from Client "+parts[4]);
        String sql = "SELECT * FROM users WHERE thread_id=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, parts[4]);
        ResultSet rs = preparedStatement.executeQuery();

        rs.next();
        NeuralNetwork nn = (NeuralNetwork) fromString(rs.getString(2));
        System.out.println("BP do "+nn);
        for (int k = 0; k < Integer.parseInt(parts[1]); k++) {//parts[1]==epoch

            Point p = points.get((int) (Math.random() * points.size()));//берем рандомную точку?????
            double nx = (double) p.x / Integer.parseInt(parts[3]) - 0.5;
            double ny = (double) p.y / Integer.parseInt(parts[2]) - 0.5;

            nn.feedForward(new double[]{nx, ny});//получаем нейроны последнего слоя после функции активации
            double[] targets = new double[2];
            if (p.type == 0) {
                targets[0] = 1;
            } else {
                targets[1] = 1;
            }
            nn.backpropagation(targets);
        }
        System.out.println("PD posle "+nn);
        sql = "UPDATE users SET nn=?, address=? WHERE thread_id=?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, toString( nn));
        preparedStatement.setString(3, String.valueOf(parts[4]));
        preparedStatement.setString(2, nn.toString());
        preparedStatement.executeUpdate();
    }
    private static void case3(Connection con, ServerSocket server2, String[] parts) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("square");
        Socket socket2 = server2.accept();

        String sql = "SELECT * FROM users WHERE thread_id=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, parts[4]);
        ResultSet rs = preparedStatement.executeQuery();

        rs.next();
        NeuralNetwork nn = (NeuralNetwork) fromString(rs.getString(2));
        System.out.println(nn);
        int square = Integer.parseInt(parts[1]);
        int h = Integer.parseInt(parts[2]);
        int w = Integer.parseInt(parts[3]);
        ArrayList<Integer> listik = new ArrayList<>();
        for (int i = 0; i < w / square; i++) {//делим на квадраты
            for (int j = 0; j < h / square; j++) {
                double nx = (double) i / w * square - 0.5;//получаем нормальные координаты
                double ny = (double) j / h * square - 0.5;
                //Double output = dis.readDouble();//определяем к какому классу пренадлежит квадратик
                double[] outputs = nn.feedForward(new double[]{nx, ny});
                double orange = Math.max(0, Math.min(1, outputs[0] - outputs[1] + 0.6));
                double pink = 1 - orange;
                orange = 0.3 + orange * 0.5;
                pink = 0.5 + pink * 0.5;
                int color = (240 << 16) | ((int) (orange * 255) << 8) | (int) (pink * 255);
                listik.add(color);
            }
        }
        ObjectOutputStream outStream = new ObjectOutputStream(socket2.getOutputStream());
        outStream.writeObject(listik);
        outStream.flush();
    }

    private static void dots(ServerSocket server, ServerSocket server2, Connection con) throws IOException, InterruptedException {
        var ref = new Object() {
            Socket socket = server.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        };

        while (true) {
            Thread myThread = new Thread(()->{
                try {
                    ref.socket.setSoTimeout(1000 * 60 * 3);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ref.socket.getInputStream()));
                    String request = reader.readLine();
                    System.out.println("Request: "+request);
                    String[] parts = request.split(">");
                    switch (parts[0]) {
                        case ("NeuralNetwork"):
                            case1(con, parts, (int) Thread.currentThread().getId());
                            ref.writer.write(String.valueOf(Thread.currentThread().getId()));
                            ref.writer.newLine();
                            ref.writer.flush();
                            break;
                        case ("backpropagation"):
                            case2(con, server2, parts);
                            ref.writer.write("nn success");
                            ref.writer.newLine();
                            ref.writer.flush();
                            break;
                        case ("square"):
                            case3(con, server2, parts);
                            break;
                        default :
                            System.out.println(parts[0]);
                            break;
                    }

                } catch (IOException | ClassNotFoundException | SQLException e) {
                    //throw new RuntimeException(e);
                    try {
                        ref.socket = server.accept();
                        ref.writer = new BufferedWriter(new OutputStreamWriter(ref.socket.getOutputStream()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
            myThread.start();
            Thread.sleep(100);
            myThread.interrupt();
            //myThread.isInterrupted();

        }
    }
}






