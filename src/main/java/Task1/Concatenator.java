package Task1;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Concatenator {

    public static String concatenate(File rootFolder) throws IOException {
        return concatenate(rootFolder, false);
    }

    public static String concatenate(File rootFolder, boolean verbose) throws IOException {
        var texts = scanFolder(rootFolder, rootFolder);

        if (verbose) {
            System.out.println("Dependencies: ");
            for (TextFile tf: texts) {
                tf.find1LDependencies();
                System.out.println(tf + ": " + tf.getDependencies());
            }
        }

        Collections.sort(texts);

        if (verbose) {
            System.out.println("Sorted list: ");
            System.out.println(texts);
        }

        StringBuilder builder = new StringBuilder();
        for (TextFile tf: texts) {
            FileReader reader = new FileReader(tf.getFile());
            Scanner inp = new Scanner(reader);
            while (inp.hasNext()) {
                String line = inp.nextLine();
                Pattern pattern = Pattern.compile("required ‘([^’]*)’");
                Matcher matcher = pattern.matcher(line);
                if (!matcher.find()) {
                    builder.append(line).append("\n");
                }
            }
        }
        return builder.toString();
    }

    public static ArrayList<TextFile> scanFolder(File file, File rootFolder) {

        ArrayList<TextFile> texts = new ArrayList<>();
        for (File child: file.listFiles()) {
            if (child.isDirectory()) {
                texts.addAll(scanFolder(child, rootFolder));
            } else {
                var extension = "";
                var fileName = child.getName();
                int dotPos = fileName.lastIndexOf('.');
                if (dotPos > 0) { extension = fileName.substring(dotPos+1); }
                if (extension.equals("txt")) { texts.add(new TextFile(child, rootFolder)); }
            }
        }

        return texts;
    }

}
