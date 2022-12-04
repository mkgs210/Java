import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.FileHandler;

public class Calculator extends GenericCalculator{

    Calculator() {
        super.stack = new LinkedList<>();
        super.def = new HashMap<>();
        try {
            FileHandler fh = new FileHandler("log.txt");
            log.addHandler(fh);
            log.setUseParentHandlers(false);
        } catch (IOException e) {
        }
    }
    @Override
    protected CalcOperation createOperation(String className, String[] args, Integer line) throws CommandNotFoundException{
        CalcOperation calcOperation = null;
        Class[] context = new Class[4];
        context[0] = String[].class;
        context[1] = LinkedList.class;
        context[2] = HashMap.class;
        context[3] = Integer.class;
        try {
            calcOperation = (CalcOperation) Class.forName(className).getDeclaredConstructor(context).newInstance((Object) args, stack, def, line);
        } catch (Exception e) {
            throw new CommandNotFoundException("Неизвестная команда " + className + " (line " + line + ")");
        }
        return calcOperation;
    }
}
