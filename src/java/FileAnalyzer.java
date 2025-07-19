import java.io.*;
import java.util.HashMap;
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

    public FileAnalyzer(File file,ConcurrentHashMap<String, FileStats> map) {
        this.map = map;
        this.file = file;
    }

    @Override
    public void run() {
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
    }
}
