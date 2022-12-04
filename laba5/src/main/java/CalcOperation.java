import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

abstract public class CalcOperation {
    static Logger log = Logger.getLogger(CalcOperation.class.getName());
    String[] args;
    LinkedList<Double> stack;
    HashMap<String, Double> def;
    Integer line;
    CalcOperation(String[] args, LinkedList<Double> stack, HashMap<String, Double> def, Integer line) {
        this.args = args;
        this.stack = stack;
        this.def = def;
        this.line = line;
    };
    abstract public void calculate() throws Exception;
}
