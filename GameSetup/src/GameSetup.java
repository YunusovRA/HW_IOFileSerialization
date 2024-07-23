import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameSetup {

    public static void main(String[] args) {
        String rootPath = "/Users/romanyunusov/Games";
        StringBuilder log = new StringBuilder();

        String[] directories = new String[]{
                "src", "src/main", "src/test", "res",
                "res/drawables", "res/vectors", "res/icons",
                "savegames", "temp"
        };

        for (String dir : directories) {
            File directory = new File(rootPath, dir);
            if (!directory.exists()) {
                boolean created = directory.mkdir();
                log.append(created ? "Создана дериктория: " + directory.getPath() + "\n" :
                        "Не удалось создать дерикторию: " + directory.getPath() + "\n");
            }
        }

        createFile(new File(rootPath + "/src/main/Main.java"), log);
        createFile(new File(rootPath + "/src/main/Utils.java"), log);
        File tempFile = createFile(new File(rootPath + "/temp/temp.txt"), log);

        if (tempFile != null) {
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(log.toString());
            } catch (IOException e) {
                System.out.println("Ошибка записи в файл: " + e.getMessage());
            }
        }
    }

    private static File createFile(File file, StringBuilder log) {
        try {
            boolean created = file.createNewFile();
            log.append(created ? "Создан файл: " + file.getPath() + "\n" :
                    "Не удалось создать файл: " + file.getPath() + "\n");
            return created ? file : null;
        } catch (IOException e) {
            log.append("Ошибка создания файла: " + file.getPath() + " - " + e.getMessage() + "\n");
            return null;
        }
    }
}