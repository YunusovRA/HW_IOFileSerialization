import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OpenProgress {
    public static void openZip(String zipFilePath, String savegamesPath) {
        try (
                FileInputStream fis = new FileInputStream(zipFilePath);
                ZipInputStream zis = new ZipInputStream(fis);
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                String saveFilePath = savegamesPath + File.separator + entryName;
                try (
                        FileOutputStream fos = new FileOutputStream(saveFilePath);
                ) {
                    int b;
                    while ((b = zis.read()) != -1) {
                        fos.write(b);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при разархивации: " + e.getMessage());
        }
    }

    public static GameProgress openProgress(String saveFilePath) {
        try (
                FileInputStream fis = new FileInputStream(saveFilePath);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            GameProgress progress = (GameProgress) ois.readObject();
            return progress;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при десериализации: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {

        String zipFilePath = "/Users/romanyunusov/Games/savegames/saves.zip";
        String savegamesPath = "/Users/romanyunusov/Games/savegames";
        String saveFile = "/Users/romanyunusov/Games/savegames/save1.dat";

        openZip(zipFilePath, savegamesPath);

        GameProgress progress = openProgress(saveFile);
        if (progress != null) {
            System.out.println(progress);
        }
    }
}