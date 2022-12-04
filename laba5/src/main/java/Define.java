import java.util.HashMap;
import java.util.LinkedList;

public class Define extends CalcOperation{
    Define(String[] args, LinkedList<Double> stack, HashMap<String, Double> def, Integer line) {
        super(args, stack, def, line);
    }
    Define(String[] args, LinkedList<Double> stack, HashMap<String, Double> def) {
        super(args, stack, def, 0);
    }

    @Override
    public void calculate() {
        def.put(args[0], Double.parseDouble(args[1]));
    }
}
