import java.io.*;
class Laba3 {
    public static void rewrite(String fname1,String fname2){
        try (FileReader fr = new FileReader(fname1);
             FileWriter fw = new FileWriter(fname2)) {
            int c = fr.read();
            while(c!=-1) {
                fw.write(c);
                c = fr.read();
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String args[]) {
        String fname1 = "f1.txt";
        String fname2 = "f2.txt";
        String n = "n.txt";

        rewrite(fname1, n);
        rewrite(n, fname2);
    }
}