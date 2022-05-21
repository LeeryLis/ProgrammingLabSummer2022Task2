package du;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class DuTest {
    @Test
    void normalTest() {
        assertEquals("1.84 MB\n147.39 KB\n38.19 KB\n",
                new Du(true, false, false, Arrays.asList("apple_large.jpg", "dir", "apple_small.jpg"), true).start());


        assertEquals("109.20\n147.39\n1.84\n",
                new Du(false, false, false, Arrays.asList("apple_normal.jpg", "dir", "apple_large.jpg"), true).start());


        assertEquals("111.82 KB\n150.92 KB\n1.93 MB\n",
                new Du(true, false, true, Arrays.asList("apple_normal.jpg", "dir", "apple_large.jpg"), true).start());


        assertEquals("2.12 MB\n",
                new Du(true, true, true, Arrays.asList("apple_small.jpg", "dir", "apple_large.jpg"), true).start());
    }

    @Test
    void throwTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new Launcher().launch((new String[]{"-h", "NoFile.txt"}), true));
        assertThrows(IllegalArgumentException.class, () ->
                new Launcher().launch((new String[]{"-h"}), true));
    }

    @Test
    void measurementTest() {
        assertEquals("9.77", new Du(false, true, false, List.of("No"), true).chooseMeasurement(10000));
        assertEquals("10.00 KB", new Du(true, true, true, List.of("No"), true).chooseMeasurement(10000));
        assertEquals("529.83 MB", new Du(true, true, false, List.of("No"), true).chooseMeasurement(555567123));
        assertEquals("93.13 GB", new Du(true, true, false, List.of("No"), true).chooseMeasurement(100000000000L));
    }
}