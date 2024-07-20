package Task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean pathReady = false;
        File root = null;

        while (!pathReady) {
            System.out.println("Введи путь до папки: ");
            String rootPath = scanner.nextLine();

            root = new File(rootPath);
            if (root.exists()) {
                pathReady = true;
            } else {
                System.out.println("Путь некорректный");
            }
        }

        try {
            File resultFile = new File(root.getAbsolutePath() + "\\result.txt");
            resultFile.delete();
            resultFile.createNewFile();

            String resultString = Concatenator.concatenate(root, true);
            Files.writeString(resultFile.toPath(), resultString);
            System.out.println("\nResult:\n" + resultString);
        } catch (IOException e) {
            System.out.println("Что-то не так с путями файлов");
            e.printStackTrace();
        }
    }
}
