package du;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Launcher {
    @Option(name="-h", usage="human-readable format")
    private boolean isHuman;
    @Option(name="-c", usage="total size")
    private boolean isTotalLength;
    @Option(name="--si",usage="hidden option")
    private boolean isSi;
    @Argument(metaVar = "file1 file2...", usage = "File names")
    private List<String> fileNames = new ArrayList<>();

    public static void main(String[] args) {
        new Launcher().launch(args);
    }

    protected int launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (fileNames.isEmpty())
                throw new IllegalArgumentException("the file list is empty");
        } catch (CmdLineException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar du.jar [-h] [-c] [--si] file1 file2 file3...");
            parser.printUsage(System.err);
            return 1;
        }
        Du du = new Du(isHuman, isTotalLength, isSi, fileNames);
        du.start();

        return 0;
    }
}
