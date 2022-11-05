import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.net.Socket;

public class FormDots extends JFrame implements Runnable, MouseListener {

    private final int w = 350;//1280;
    private final int h = 350;//720;
    private final int square = 10;
    private final Integer port1;
    private final Integer port2;
    private final Integer port3;
    private boolean smart;
    private  BufferedWriter writer;
    private  ObjectOutputStream outStream;
    private  BufferedReader reader;
    private  ObjectInputStream inputStream;
    private  DataOutputStream dos;
    private  DataInputStream dis;
    private BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    private BufferedImage pimg = new BufferedImage(w / square, h / square, BufferedImage.TYPE_INT_RGB);
    private int frame = 0;
    private double learning_rate = 0.001;
    private int epoch = 10000;
    private double l2 = 0.0000001;

    //private NeuralNetwork nn;
    public ArrayList<Point> points = new ArrayList<Point>();

    public FormDots(Integer port1,Integer port2,Integer port3) throws IOException {
        Socket socket = new Socket("127.0.0.1",port1);
        //Socket socket2 = new Socket("127.0.0.1",port2);
        //Socket socket3 = new Socket("127.0.0.1",port3);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()));
        //ObjectOutputStream outStream = new ObjectOutputStream(socket2.getOutputStream());
        //ObjectInputStream inputStream = new ObjectInputStream(socket2.getInputStream());
        //DataOutputStream dos = new DataOutputStream(socket3.getOutputStream());
        //DataInputStream dis = new DataInputStream(socket3.getInputStream());

        writer.write("NeuralNetwork>"+learning_rate+">"+l2+">"+ 2 + ">"+ 5 +">"+ 5 +">" + 5 +">" + 2);
        writer.newLine();
        writer.flush();
        this.smart = true;
        this.port1 = port1;
        this.port2 = port2;
        this.port3 = port3;
        this.writer = writer;
        this.reader = reader;
        //this.outStream = outStream;
        //this.inputStream = inputStream;
        //this.dos = dos;
        //this.dis = dis;
        //nn = new NeuralNetwork(learning_rate, sigmoid, dsigmoid, 2, 5, 5, 5, 2);
        this.setSize(w + 16, h + 38);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(50, 50);
        this.add(new JLabel(new ImageIcon(img)));
        addMouseListener(this);
    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
//            try { Thread.sleep(17); } catch (InterruptedException e) {}
        }
    }

    @Override
    public void paint(Graphics g) {
        if (smart){
            try {
                System.out.println("square");
                writer.write("square>" + square + ">" + h + ">" + w);
                writer.newLine();
                writer.flush();
                System.out.println("square success!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                Socket socket2 = new Socket("127.0.0.1",port2);
                inputStream = new ObjectInputStream(socket2.getInputStream());
                ArrayList <Integer> color = (ArrayList<Integer>) inputStream.readObject();

                System.out.println("color");
                for (int i = 0; i < w / square; i++) {//делим на квадраты
                    for (int j = 0; j < h / square; j++) {
                        //System.out.println(i*j+j+": "+color.get(i*j+j));
                        pimg.setRGB(i, j, color.get(0));//j*i+j
                        color.remove(0);
                    }
                }
                System.out.println("pimg");

            } catch (IOException | ClassNotFoundException e) {throw new RuntimeException(e);}
            Graphics ig = img.getGraphics();
            ig.drawImage(pimg, 0, 0, w, h, this);//pimg
            for (Point p : points) {
                ig.setColor(Color.WHITE);
                ig.fillOval(p.x - 3, p.y - 3, 26, 26);
                if (p.type == 0) ig.setColor(Color.ORANGE);
                else ig.setColor(Color.PINK);
                ig.fillOval(p.x, p.y, 20, 20);
            }
            g.drawImage(img, 8, 30, w, h, this);
            frame++;
        }
        if(points.size() > 0) {
            try {
                smart=false;
                //writer.newLine();

                writer.write("backpropagation>"+epoch+">"+h+">"+ w);

                System.out.println("_________");
                System.out.println(points);
                System.out.println("p size: "+points.size());
                System.out.println("_________");

                Socket socket2 = new Socket("127.0.0.1",port2);
                outStream = new ObjectOutputStream(socket2.getOutputStream());
                outStream.writeObject(points);

                writer.newLine();
                writer.flush();
                outStream.flush();

                if("nn success".equals(reader.readLine())){
                    smart=true;
                }

            } catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int type = 0;
        if(e.getButton() == 1){
            points.add(new Point(e.getX()- 16, e.getY() - 38, type));
        }
        if(e.getButton() == 3){
            type = 1;
            points.add(new Point(e.getX() - 16, e.getY() - 38, type));
        }
        if(e.getButton() == 5){
            points.add(new Point(e.getX() - 16, e.getY() - 38, type));
            points.add(new Point(e.getX() - 8, e.getY() - 30, type));
            points.add(new Point(e.getX() - 24, e.getY() - 46, type));
        }
        if(e.getButton() == 4) {
            type = 1;
            points.add(new Point(e.getX() - 16, e.getY() - 38, type));
            points.add(new Point(e.getX() - 8, e.getY() - 30, type));
            points.add(new Point(e.getX() - 24, e.getY() - 46, type));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}