import java.util.HashMap;
import java.util.LinkedList;

public class Sub extends CalcOperation{
    Sub(String[] args, LinkedList<Double> stack, HashMap<String, Double> def, Integer line) {
        super(args, stack, def, line);
    }
    Sub(String[] args, LinkedList<Double> stack, HashMap<String, Double> def) {
        super(args, stack, def, 0);
    }
    @Override
    public void calculate() throws StackIsEmptyException {
        if(!stack.isEmpty()){
            double first = stack.pop();
            if(!stack.isEmpty()) {
                double second = stack.pop();
                stack.push(first-second);
            } else {
                throw new StackIsEmptyException("Попытка выполнить операцию при пустом стеке в команде " + this.getClass().getName() + " (line " + line + ")");
            }
        } else {
            throw new StackIsEmptyException("Попытка выполнить операцию при пустом стеке в команде " + this.getClass().getName() + " (line " + line + ")");
        }
    }
}