import java.io.*;
import java.util.zip.*;

/**
 * The analyzed threads begin to be zipped.
 *
 * @author Meryem Özevren
 */


public class FileZipper extends Thread {

    private File[] dosya;
    private String zipYol;

    public FileZipper(File[] dosya, String zipYol) {
        this.dosya = dosya;
        this.zipYol = zipYol;
    }

    @Override
    public void run() {
        System.out.println("Creating zip...");

        try {
            // .zip dosyasını yazmak için gerekli stream
            FileOutputStream dosyaFinal = new FileOutputStream(zipYol);
            ZipOutputStream zipFinal = new ZipOutputStream(dosyaFinal);

            // Her dosya için teker teker işlemi yapıyoruz
            for (File dosya : dosya) {

                // Dosya gerçekten var mı ve bir dosya mı (klasör değil)?
                if (!dosya.exists() || !dosya.isFile()) {
                    System.out.println("Invalid file: " + dosya.getName());
                    continue;
                }

                // Dosyayı okumaya çalış
                try {
                    FileInputStream dosyaGiris = new FileInputStream(dosya);

                    // Zip dosyasına yeni giriş (dosya) ekliyoruz
                    ZipEntry zipGiris = new ZipEntry(dosya.getName());
                    zipFinal.putNextEntry(zipGiris);

                    // Dosyayı 1024 baytlık parçalarla okuyup zip'e yaz
                    byte[] buffer = new byte[1024];
                    int okunanByte;

                    while ((okunanByte = dosyaGiris.read(buffer)) != -1) {
                        zipFinal.write(buffer, 0, okunanByte);
                    }

                    // Dosya bitti, kapat
                    zipFinal.closeEntry();
                    dosyaGiris.close();

                    System.out.println(dosya.getName() + "added to the zip file.");

                } catch (IOException e) {
                    System.out.println("Error occurred:" + dosya.getName());
                }
            }

            // Son olarak zip stream'ini kapat
            zipFinal.close();
            dosyaFinal.close();

            System.out.println("✅ Zip creation completed:" + zipYol);

        } catch (IOException e) {
            System.out.println("❌ An error occurred while creating the zip.");
        }
    }
}
