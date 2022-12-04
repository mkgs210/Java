import java.util.HashMap;
import java.util.LinkedList;

public class Print extends CalcOperation {

    Print(String[] args, LinkedList<Double> stack, HashMap<String, Double> def, Integer line) {
        super(args, stack, def, line);
    }
    Print(String[] args, LinkedList<Double> stack, HashMap<String, Double> def) {
        super(args, stack, def, 0);
    }

    @Override
    public void calculate() throws StackIsEmptyException {
        if(!stack.isEmpty()) {
            System.out.println(stack.getFirst());
        } else {
            throw new StackIsEmptyException("Попытка выполнить операцию при пустом стеке в команде " + this.getClass().getName() + " (line " + line + ")");
        }
    }
}
