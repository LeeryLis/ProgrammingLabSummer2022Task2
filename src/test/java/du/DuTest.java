package du;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

public class DuTest {
    Launcher launcher = new Launcher();

    private String chooseMeasurement(long length, boolean isSi) {
        double finalLength = length;

        int divider = 1024;
        if (isSi) divider = 1000;

        String[] measure = {"B", "KB", "MB", "GB"};
        for (int i = 0; i < 3; i++) {
            finalLength /= divider;
            if (finalLength < 1) {
                finalLength *= divider;
                return new DecimalFormat("#0.00").format(finalLength) + " " + measure[i];
            }
        }
        return new DecimalFormat("#0.00").format(finalLength) + " GB";
    }

    private long folderSize(File dir) {
        long length = 0;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) length += file.length();
            else length += folderSize(file);
        }
        return length;
    }

    @Test
    void normalTest() {
        assertEquals(chooseMeasurement(new File("Input/3.txt").length(), false) + "\n" +
                        chooseMeasurement(folderSize(new File("Input/dir")), false) + "\n" +
                        chooseMeasurement(new File("Input/4.txt").length(), false),
                new Du(true, false, false, Arrays.asList("3.txt", "dir", "4.txt")).start());


        assertEquals(new File("Input/3.txt").length() + "\n" +
                        folderSize(new File("Input/dir")) + "\n" +
                        new File("Input/4.txt").length(),
                new Du(false, false, false, Arrays.asList("3.txt", "dir", "4.txt")).start());


        assertEquals(chooseMeasurement(new File("Input/3.txt").length(), true) + "\n" +
                        chooseMeasurement(folderSize(new File("Input/dir")), true) + "\n" +
                        chooseMeasurement(new File("Input/4.txt").length(), true),
                new Du(true, false, true, Arrays.asList("3.txt", "dir", "4.txt")).start());


        assertEquals(chooseMeasurement(new File("Input/3.txt").length() +
                folderSize(new File("Input/dir")) +
                new File("Input/4.txt").length(), true),
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