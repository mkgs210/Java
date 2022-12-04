import java.util.HashMap;
import java.util.LinkedList;

public class Sqrt extends CalcOperation{
    Sqrt(String[] args, LinkedList<Double> stack, HashMap<String, Double> def, Integer line) {
        super(args, stack, def, line);
    }
    Sqrt(String[] args, LinkedList<Double> stack, HashMap<String, Double> def) {
        super(args, stack, def, 0);
    }
    @Override
    public void calculate() throws StackIsEmptyException{
        if(!stack.isEmpty()) {
            stack.push(Math.sqrt(stack.pop()));
        } else {
            throw new StackIsEmptyException("Попытка выполнить операцию при пустом стеке в команде " + this.getClass().getName() + " (line " + line + ")");
        }
    }
}
