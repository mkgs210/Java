import java.io.*;

public class Client {
    private static void dots(Integer port1,Integer port2,Integer port3) throws IOException {
        FormDots f = new FormDots(port1,port2,port3);
        new Thread(f).start();
    }

    public static void main(String[] args) throws IOException {
        Integer port1 = 50005;
        Integer port2 = 50006;
        Integer port3 = 50003;

        System.out.println("connect to server");

        dots(port1,port2,port3);
    }
}