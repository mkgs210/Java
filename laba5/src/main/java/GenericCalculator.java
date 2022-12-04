import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericCalculator {
    private static final Scanner sc = new Scanner(System.in);
    final static Logger log = Logger.getLogger(GenericCalculator.class.getName());
    LinkedList<Double> stack;
    HashMap<String, Double> def;
    protected InputStream confStream;

    public void doOperationsFromConfig(String confName){
        confStream = getClass().getResourceAsStream("/" + confName);
        Reader reader = new InputStreamReader(confStream, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        int i, line = 1;
        try {
            while ((i = reader.read()) != -1) {
                if (i == '#'){
                    do {
                        i = reader.read();
                    } while (i != '\n' && i != -1);
                    line++;
                } else if (i != '\n') {
                    stringBuilder.append((char) i);
                } else {
                    if(!stringBuilder.isEmpty()) {
                        String[] command = stringBuilder.toString().split("[ \\r]");
                        String className = command[0];
                        String[] args = command.length == 1 ? new String[0] : Arrays.copyOfRange(command, 1, command.length);
                        for (int j = 0; j < args.length; ++j) {
                            args[j].replace(',', '.');
                        }
                        doOperation(className, args, line);
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                    line++;
                }
            }
            if(!stringBuilder.isEmpty()) {
                String[] command = stringBuilder.toString().split(" ");
                String className = command[0];
                String[] args = command.length == 1 ? new String[0] : Arrays.copyOfRange(command, 1, command.length);
                doOperation(className, args, line);
                stringBuilder.delete(0, stringBuilder.length());
            }
            System.out.println("Файл конфигурации успешно прочитан.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    public void doOperationsFromCommandLine() {
        int line = 1;
        System.out.println("Чтобы закончить введите команду Stop.");
        while (true){
            String input = stringInput("");
            if(input.equals("Stop")) {
                System.out.println("Завершение ввода...");
                break;
            }
            String[] command = input.toString().split("[ \\r]");
            String className = command[0];
            String[] args = command.length == 1 ? new String[0] : Arrays.copyOfRange(command, 1, command.length);
            for (int j = 0; j < args.length; ++j) {
                args[j].replace(',', '.');
            }
            doOperation(className, args, line);
            line++;
        }
    }

    public void doOperation(String className, String[] args, Integer line){
        CalcOperation calcOperation = null;
        try {
            calcOperation = createOperation(className, args, line);
            calcOperation.calculate();
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }
    protected abstract CalcOperation createOperation (String className, String[] args, Integer line) throws Exception;

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
}
