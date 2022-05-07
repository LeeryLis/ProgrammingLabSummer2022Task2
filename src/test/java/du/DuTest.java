package du;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class DuTest {
    Launcher launcher = new Launcher();

    @Test
    void normalTest() {
        assertEquals("1,24 KB\n" +"1012,00 B\n" +"24,80 KB",
                new Du(true, false, false, Arrays.asList("3.txt", "dir", "4.txt")).start());
        assertEquals("1268\n" +"1012\n" +"25398",
                new Du(false, false, false, Arrays.asList("3.txt", "dir", "4.txt")).start());
        assertEquals("1,27 KB\n" +"1,01 KB\n" +"25,40 KB",
                new Du(true, false, true, Arrays.asList("3.txt", "dir", "4.txt")).start());
        assertEquals("27,68 KB",
                new Du(true, true, true, Arrays.asList("3.txt", "dir", "4.txt")).start());
    }

    @Test
    void throwTest() {
        assertThrows(IllegalArgumentException.class, () ->
                launcher.launch(new String[]{"-h", "NoFile.txt"}));
        assertThrows(IllegalArgumentException.class, () ->
                launcher.launch(new String[]{"-h"}));
    }
}