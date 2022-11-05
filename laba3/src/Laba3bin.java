import java.io.*;
import java.nio.charset.StandardCharsets;

public class Laba3bin {
    public static void write(String filename, String filesFound){
        File file = new File (filename);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(filesFound.getBytes());
            fos.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        System.out.println("Путь к созданному файлу: " + file.getAbsolutePath());
    }

    public static void read(String filename){
        File file = new File (filename);
        try{
            FileInputStream fos = new FileInputStream(file);
            String text = new String(fos.readAllBytes(), StandardCharsets.UTF_8);

            int indexA = text.indexOf("Автор");
            int indexB = text.indexOf("Книга");
            int indexT = text.indexOf("Тираж");
            int indexY = text.indexOf("Убийство");
            int indexy = text.indexOf("убийство");

            while (indexY != -1 || indexy != -1) {
                if (indexB<indexY & indexY<indexT){
                    char[] dst=new char[indexB - indexA-1];
                    text.getChars(indexA, indexB-1, dst, 0);
                    System.out.println(dst);
                    indexY = text.indexOf("Убийство", indexY+1);
                }else if (indexB<indexy & indexy<indexT){
                    char[] dst=new char[indexB - indexA-1];
                    text.getChars(indexA, indexB-1, dst, 0);
                    System.out.println(dst);
                    indexy = text.indexOf("убийство", indexy+1);
                }
                indexA = text.indexOf("Автор", indexA + 1);
                indexB = text.indexOf("Книга",indexB+1);
                indexT = text.indexOf("Тираж",indexT+1);
            }

            fos.close();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        //System.out.println("Путь к созданному файлу: " + file.getAbsolutePath());
    }


    public static void main(String args[]) {
        String bintext = "Автор: Березин Александр Юрьевич\n" +
                "Книга: убийство программ учащихся\n" +
                "Тираж: 10000\n" +
                "Цена: 1000\n" +
                "Год издания: 2022\n\n"+
                "Автор: Кузнецов Максим Сергеевич\n" +
                "Книга: Убийство убийц\n" +
                "Тираж: 100\n" +
                "Цена: 100000\n" +
                "Год издания: 2035\n\n";
        String filename = "Files.bin";
        write(filename, bintext);
        read(filename);

    }
}
