package du;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Du {
    private final boolean isHuman;
    private final boolean isTotalLength;
    private final boolean isSi;
    private final List<String> fileNames;

    private final boolean isTest;

    public Du(boolean isHuman, boolean isTotalLength, boolean isSi, List<String> fileNames, boolean isTest) {
        this.isHuman = isHuman;
        this.isTotalLength = isTotalLength;
        this.isSi = isSi;
        this.fileNames = fileNames;
        this.isTest = isTest;
    }

    public String start() {
        StringBuilder result = new StringBuilder();

        long totalLength = 0;

        for (String fileName : fileNames) {
            File file = new File(fileName);

            if(!file.exists()) {
                throw new IllegalArgumentException("file \"" + fileName + "\" was not found");
            }
            long length = file.isDirectory() ? folderSize(file) : file.length();
            if (!isTotalLength) {
                if (isTest) result.append(chooseMeasurement(length)).append("\n");
                else System.out.println(chooseMeasurement(length));
            } else {
                totalLength += length;
            }
        }

        if(isTotalLength) {
            if (isTest) result.append(chooseMeasurement(totalLength)).append("\n");
            else System.out.println(chooseMeasurement(totalLength));
        }

        return result.toString();
    }

    private long folderSize(File dir) {
        long length = 0;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) length += file.length();
            else length += folderSize(file);
        }
        return length;
    }

    String chooseMeasurement(long length) {
        double finalLength = length;
        String result;
        int measureNumber = 0;

        int divider = 1024;
        if (isSi) divider = 1000;

        String[] measure = {"B", "KB", "MB", "GB"};
        for (int i = 0; i < 3; i++) {
            finalLength /= divider;
            if (finalLength < 1) {
                finalLength *= divider;
                break;
            }
            measureNumber++;
        }
        result = String.format(Locale.ENGLISH, "%.2f", finalLength);
        if (isHuman)
            return result + " " + measure[measureNumber];
        return result;
    }
}
