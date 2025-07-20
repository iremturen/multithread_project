package fileOperations;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Analysis line and character counts for a file.
 * @author Sefa Bulut
 */
public class FileAnalyzer implements Runnable {
    private int numberOfChars = 0;
    private int numberOfLines = 0;
    private File file;
    private ConcurrentHashMap<String, FileStats> map;
    private File logFile = new File("output/analysis-log.csv");

    public FileAnalyzer(File file,ConcurrentHashMap<String, FileStats> map) {
        this.map = map;
        this.file = file;
    }

    @Override
    public void run() {
        long start = System.nanoTime();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numberOfLines++;
                numberOfChars += line.length();
            }

            // Hesaplanan değerlerin map'e konulması
            map.put(file.getName(), new FileStats(numberOfLines,numberOfChars));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long end = System.nanoTime();
        long duration = end - start;

        System.out.printf("✓ %s processed. Duration: %,d ns (%.3f ms)%n",
                file.getName(), duration, duration / 1_000_000.0);

        // ANALİZ SÜRESİNİ .CSV LOG'A YAZDIR
        synchronized (FileAnalyzer.class) {
            try (FileWriter fw = new FileWriter(logFile, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                if (logFile.length() == 0) {
                    out.println("File Name, Number of Rows, Number of Characters, Duration (ns), Duration (ms)");
                }
                out.printf("%s,%d,%d,%d,%.3f%n",
                        file.getName(), numberOfLines, numberOfChars, duration, duration / 1_000_000.0);
            } catch (IOException e) {
                System.out.println("Could not write to log file.");
            }
        }
    }
}