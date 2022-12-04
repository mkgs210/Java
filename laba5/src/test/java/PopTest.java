import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PopTest {
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
    @DisplayName("Удаление последнего элемента в стеке")
    void calculate1() {
        stack.add(4.0);
        final Pop pop = new Pop(args, stack, def);
        try {
            pop.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(stack.isEmpty());
    }
    @Test
    @DisplayName("Удаление элемента")
    void calculate2() {
        stack.add(4.5);
        stack.add(2.0);
        final Pop pop = new Pop(args, stack, def);
        try {
            pop.calculate();
        } catch (StackIsEmptyException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(2.0, stack.getFirst());
    }
    @Test
    @DisplayName("Пустой стек")
    void calculate4() {
        final Pop pop = new Pop(args, stack, def);
        assertThrows(StackIsEmptyException.class, () -> {pop.calculate();});
    }
}