import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SubTest {
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
    @DisplayName("2-4")
    void calculate1() {
        stack.add(2.0);
        stack.add(4.0);
        final Sub sub = new Sub(args, stack, def);
        try {
            sub.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(-2.0, stack.getFirst());
    }
    @Test
    @DisplayName("4-2")
    void calculate2() {
        stack.add(4.0);
        stack.add(2.0);
        final Sub sub = new Sub(args, stack, def);
        try {
            sub.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(2.0, stack.getFirst());
    }
    @Test
    @DisplayName("0-0")
    void calculate3() {
        stack.add(0.0);
        stack.add(0.0);
        final Sub sub = new Sub(args, stack, def);
        try {
            sub.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(0.0, stack.getFirst());
    }
    @Test
    @DisplayName("Пустой стек выбрасывает StackIsEmptyException")
    void calculate4() {
        final Sub sub = new Sub(args, stack, def);
        assertThrows(StackIsEmptyException.class, () -> {sub.calculate();});
    }
    @Test
    @DisplayName("Один элемент в стеке выбрасывает StackIsEmptyException")
    void calculate5() {
        stack.add(0.5);
        final Sub sub = new Sub(args, stack, def);
        assertThrows(StackIsEmptyException.class, () -> {sub.calculate();});
    }
}