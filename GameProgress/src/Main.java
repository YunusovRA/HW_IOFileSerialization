import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        String savePath = "/Users/romanyunusov/Games/savegames/";
        List<String> files = List.of(
                savePath + "save1.dat",
                savePath + "save2.dat",
                savePath + "save3.dat"
        );

        GameProgress gp1 = new GameProgress(100, 10, 1, 0.0);
        GameProgress gp2 = new GameProgress(90, 15, 2, 10.5);
        GameProgress gp3 = new GameProgress(50, 5, 3, 5.0);

        files.forEach(file -> saveGame(file, gp1));
        files.forEach(file -> saveGame(file, gp2));
        files.forEach(file -> saveGame(file, gp3));

        String zipFile = savePath + "saves.zip";
        zipFiles(zipFile, files);

        deleteFiles(files);
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf("/") + 1));
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                } catch (IOException e) {
                    System.out.println("Ошибка при добавлении файла в архив: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании архива: " + e.getMessage());
        }
    }

    public static void deleteFiles(List<String> files) {
        for (String file : files) {
            File f = new File(file);
            if (f.delete()) {
                System.out.println("Файл " + file + " удален.");
            } else {
                System.out.println("Не удалось удалить файл " + file);
            }
        }
    }
}