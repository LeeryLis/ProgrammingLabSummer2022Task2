package du;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class Du {
    private final boolean isHuman;
    private final boolean isTotalLength;
    private final boolean isSi;
    private final List<String> fileNames;

    public Du(boolean isHuman, boolean isTotalLength, boolean isSi, List<String> fileNames) {
        this.isHuman = isHuman;
        this.isTotalLength = isTotalLength;
        this.isSi = isSi;
        this.fileNames = fileNames;
    }

    public String start() {
        StringBuilder result = new StringBuilder();

        long totalLength = 0;

        for (String fileName : fileNames) {
            File file = new File("Input/" + fileName);

            if(file.exists()) {
                long length = file.isFile() ? file.length() : folderSize(file);
                if (!isTotalLength) {
                    if (isHuman) result.append(chooseMeasurement(length)).append((!Objects.equals(fileName, fileNames.get(fileNames.size() - 1)))? "\n" : "");
                    else result.append(length).append((!Objects.equals(fileName, fileNames.get(fileNames.size() - 1)))? "\n" : "");
                } else {
                    totalLength += length;
                }
            }
            else throw new IllegalArgumentException("file \"" + fileName + "\" was not found");
        }

        if(isTotalLength) {
            if (isHuman) result.append(chooseMeasurement(totalLength));
            else result.append((int) totalLength);
        }

        System.out.println(result);
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

    private String chooseMeasurement(long length) {
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
}
