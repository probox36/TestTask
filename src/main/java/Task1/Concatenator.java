package Task1;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

public class Concatenator {

    public static String concatenate(File root) throws IOException {
        return concatenate(root, false);
    }

    public static String concatenate(File root, boolean verbose) throws IOException {
        var texts = scanFolder(root);

        if (verbose) { System.out.println("Dependencies: "); }
        for (TextFile tf: texts) {
            tf.findDependencies(root);
            if (verbose) {
                System.out.println(tf + ": " + tf.getDependencies());
            }
        }

        //todo: не совсем правильно работает сортировка. Исправить
        Collections.sort(texts);
        if (verbose) {
            System.out.println("Sorted list: ");
            System.out.println(texts);
        }

        StringBuilder builder = new StringBuilder();
        for (TextFile tf: texts) {
            String content = Files.readString(tf.getFile().toPath());
            builder.append(content);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static ArrayList<TextFile> scanFolder(File file) {

        ArrayList<TextFile> texts = new ArrayList<>();
        for (File child: file.listFiles()) {
            if (child.isDirectory()) {
                texts.addAll(scanFolder(child));
            } else {
                var extension = "";
                var fileName = child.getName();
                int dotPos = fileName.lastIndexOf('.');
                if (dotPos > 0) { extension = fileName.substring(dotPos+1); }
                if (extension.equals("txt")) { texts.add(new TextFile(child)); }
            }
        }

        return texts;
    }

}
