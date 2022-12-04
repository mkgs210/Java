import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SqrtTest {
    static String[] args;
    static LinkedList<Double> stack = new LinkedList<>();
    static HashMap<String, Double> def = new HashMap<>();

    @BeforeEach
    void init() {
        args = new String[0];
        stack.clear();
        def.clear();
    }
    @Test
    @DisplayName("Корень из 4")
    void calculate1() {
        stack.add(4.0);
        final Sqrt sqrt = new Sqrt(args, stack, def);
        try {
            sqrt.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(2.0, stack.getFirst());
    }
    @Test
    @DisplayName("Корень из 144")
    void calculate2() {
        stack.add(144.0);
        stack.add(2.0);
        final Sqrt sqrt = new Sqrt(args, stack, def);
        try {
            sqrt.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(12.0, stack.getFirst());
    }
    @Test
    @DisplayName("Пустой стек")
    void calculate3() {
        final Sqrt sqrt = new Sqrt(args, stack, def);
        assertThrows(StackIsEmptyException.class, () -> {sqrt.calculate();});
    }
}