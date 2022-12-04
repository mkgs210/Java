import java.util.HashMap;
import java.util.LinkedList;

public class Push extends CalcOperation{
    Push(String[] args, LinkedList<Double> stack, HashMap<String, Double> def, Integer line) {
        super(args, stack, def, line);
    }
    Push(String[] args, LinkedList<Double> stack, HashMap<String, Double> def) {
        super(args, stack, def, 0);
    }

    @Override
    public void calculate() throws VariableNotDefinedException {
        if(def.containsKey(args[0])) {
            stack.push(def.get(args[0]));
        } else {
            Double number;
            try {
                number = Double.parseDouble(args[0]);
                stack.push(number);
            } catch (NumberFormatException e) {
                throw new VariableNotDefinedException("Попытка использовать неопределенную переменную " + args[0] + " в команде " + this.getClass().getName() + " (line " + line + ")");
            }
        }
    }
}
