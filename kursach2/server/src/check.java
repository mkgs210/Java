import java.io.*;
import java.util.ArrayList;
import java.util.Base64;

public class check {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList ints = new ArrayList<Integer>();
        ints.add(2);
        ints.add(5);
        ints.add(5);
        ints.add(2);
        NeuralNetwork nn1 = new NeuralNetwork(0.001, 0, ints);
        NeuralNetwork nn2 = new NeuralNetwork(0.001, 0, ints);
        String b = new String("bbbb");
        System.out.println(fromString(toString(nn1)));
        System.out.println(fromString(toString(nn2)));
        System.out.println(toString(nn2).equals(toString(nn1)));
    }
    private static Object fromString(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Write the object to a Base64 string.
     */
    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.flush();
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}