import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PushTest {
    static String[] args;
    static LinkedList<Double> stack = new LinkedList<>();
    static HashMap<String, Double> def = new HashMap<>();

    @BeforeEach
    void init() {
        args = new String[1];
        stack.clear();
        def.clear();
    }
    @Test
    @DisplayName("Push 123")
    void calculate1() {
        args[0] = "123";
        final Push push = new Push(args, stack, def);
        try {
            push.calculate();
        } catch (VariableNotDefinedException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(123.0, stack.getFirst());
    }
    @Test
    @DisplayName("Push A (A=123)")
    void calculate2() {
        args[0] = "A";
        def.put("A", 123.0);
        final Push push = new Push(args, stack, def);
        try {
            push.calculate();
        } catch (VariableNotDefinedException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(123.0, stack.getFirst());
    }
    @Test
    @DisplayName("Push A выбрасывает VariableNotDefinedException")
    void calculate3() {
        args[0] = "A";
        final Push push = new Push(args, stack, def);
        assertThrows(VariableNotDefinedException.class, () -> {push.calculate();});
    }
}