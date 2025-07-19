import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Main class: analyzes .txt files using threads and zips them.
 *
 * @author İrem Türen, Sefa Bulut
 */
public class Main {
    private static final int MAX_THREADS = 10;
    private static final String INPUT_FOLDER_PATH = "input";
    private static final String OUTPUT_ZIP_PATH = "output/files.zip";

    public static void main(String[] args) throws FileNotFoundException {
        File inputFolder = new File(INPUT_FOLDER_PATH);

        if (!inputFolder.exists() || !inputFolder.isDirectory()) {
            System.out.println("The input folder was not found.");
            return;
        }

        File[] allTxtFiles = inputFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (allTxtFiles == null || allTxtFiles.length == 0) {
            System.out.println("The .txt file was not found.");
            return;
        }

        int fileCount = Math.min(allTxtFiles.length, MAX_THREADS);

        File[] selectedFiles = new File[fileCount];
        System.arraycopy(allTxtFiles, 0, selectedFiles, 0, fileCount);

        ConcurrentHashMap<String, FileStats> resultMap = new ConcurrentHashMap<>();
        List<Thread> threadList = new ArrayList<>();

        for (File file : selectedFiles) {
            Thread t = new Thread(new FileAnalyzer(file, resultMap));
            threadList.add(t);
            t.start();
        }

        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Map içeriğini yazdırma
        System.out.println("*** Starting the file analysis. ***");
        int totalNumberOfChars = 0;
        int totalNumberOfLines = 0;
        for (Map.Entry<String, FileStats> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
            totalNumberOfChars += entry.getValue().getCharCount();
            totalNumberOfLines += entry.getValue().getLineCount();
        }
        System.out.println("Total: " + totalNumberOfLines + " line / " + totalNumberOfChars + " characters");

        Thread zipThread = new Thread(new FileZipper(selectedFiles, OUTPUT_ZIP_PATH));
        zipThread.start();

        try {
            zipThread.join();
        } catch (InterruptedException e) {
            System.err.println("Zip thread was interrupted.");
            e.printStackTrace();
        }
    }
}