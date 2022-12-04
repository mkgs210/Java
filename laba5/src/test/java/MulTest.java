import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MulTest {
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
    @DisplayName("2*4.5")
    void calculate1() {
        stack.add(2.0);
        stack.add(4.5);
        final Mul mul = new Mul(args, stack, def);
        try {
            mul.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(9.0, stack.getFirst());
    }
    @Test
    @DisplayName("4.5*2")
    void calculate2() {
        stack.add(4.5);
        stack.add(2.0);
        final Mul mul = new Mul(args, stack, def);
        try {
            mul.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(9.0, stack.getFirst());
    }
    @Test
    @DisplayName("10000000*0")
    void calculate3() {
        stack.add(10000000.0);
        stack.add(0.0);
        final Mul mul = new Mul(args, stack, def);
        try {
            mul.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(0.0, stack.getFirst());
    }
    @Test
    @DisplayName("Пустой стек")
    void calculate4() {
        final Mul mul = new Mul(args, stack, def);
        assertThrows(StackIsEmptyException.class, () -> {mul.calculate();});
    }
    @Test
    @DisplayName("Один элемент в стеке")
    void calculate5() {
        stack.add(0.5);
        final Mul mul = new Mul(args, stack, def);
        assertThrows(StackIsEmptyException.class, () -> {mul.calculate();});
    }
}