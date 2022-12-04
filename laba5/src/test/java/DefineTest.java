import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DefineTest {
    static String[] args;
    static LinkedList<Double> stack = new LinkedList<>();
    static HashMap<String, Double> def = new HashMap<>();

    @BeforeEach
    void init() {
        args = new String[2];
        stack.clear();
        def.clear();
    }
    @Test
    @DisplayName("Define A 256")
    void calculate1() {
        args[0] = "A";
        args[1] = "256";
        def.put(args[0], Double.parseDouble(args[1]));
        assertEquals(256.0, def.get(args[0]));
    }
    @Test
    @DisplayName("Define A BC выбрасывает NumberFormatException")
    void calculate2() {
        args[0] = "A";
        args[1] = "BC";
        assertThrows(NumberFormatException.class, () -> {def.put(args[0], Double.parseDouble(args[1]));} );
    }
    @Test
    @DisplayName("Define A 25.052")
    void calculate3() {
        args[0] = "A";
        args[1] = "25.052";
        def.put("A", Double.parseDouble(args[1]));
        assertEquals(25.052, def.get("A") );
    }
}