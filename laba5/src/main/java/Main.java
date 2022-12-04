import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Не удалось прочесть конфигурацию логгера: " + e.toString());
        }
        Calculator calc = new Calculator();
        int menu;
        do {
            menu = intInput("Введите номер опции:\n1. Прочесть конфигурацию из файла\n2. Перейти к ручному вводу команд\n");
            switch (menu) {
                case 1 -> calc.doOperationsFromConfig(getConfName(args));
                case 2 -> calc.doOperationsFromCommandLine();
                default -> {
                }
            }
        } while (menu != 1 && menu != 2);
    }

    static String getConfName(String[] args) {
        File cat = new File("C://Users//Maxim//Desktop//Java//laba5//src//main//resources//");
        File f = null;
        boolean argIsCorrect = true;
        do {
            if(args.length == 1 && argIsCorrect){
                f = new File(cat, args[0]);
                if (!f.exists() || !f.isFile()) {
                    argIsCorrect = false;
                    System.out.println("Файл не существует.");
                }
            } else {
                File[] files = cat.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
                if (files.length != 0) {
                    System.out.println("Существующие файлы: ");
                    for (File file : files) {
                        System.out.println(file);
                    }
                }
                String fileName = stringInput("Введите название файла: ");
                f = new File(cat, fileName);
                if (!f.exists() || !f.isFile()) {
                    System.out.println("Файл не существует.");
                }
            }
        } while (!f.exists() || !f.isFile());
        return f.getName().substring(f.getName().lastIndexOf("\\")+1);
    }

    static String stringInput(String message) {
        String input;
        while (true) {
            System.out.print(message);
            try {
                input = sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            sc.reset();
        }
        return input;
    }

    static int intInput(String message) {
        int input;
        while(true) {
            System.out.print(message);
            try {
                input = Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            sc.reset();
        }
        return input;
    }
}