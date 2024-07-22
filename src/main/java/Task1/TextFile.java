package Task1;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class TextFile implements Comparable<TextFile> {

    private final File file;
    private final File rootFolder;
    @Setter
    private ArrayList<TextFile> dependencies;

    public TextFile(File file, File rootFolder) {
        this.file = file;
        this.rootFolder = rootFolder;
        this.dependencies = new ArrayList<>();
    }

    public void addDependency(TextFile f) {
        dependencies.add(f);
    }

    public void find1LDependencies() {

        ArrayList<TextFile> newDependencies = new ArrayList<>();
        FileReader fr;
        try {
            fr = new FileReader(file);
            Scanner inp = new Scanner(fr);

            while (inp.hasNext()) {
                String text = inp.nextLine();
                Pattern pattern = Pattern.compile("required ‘([^’]*)’");
                Matcher matcher = pattern.matcher(text);

                if (matcher.find()) {
                        String filePath = rootFolder.getAbsolutePath() + "\\" + matcher.group(1);
                        TextFile newDependency = new TextFile(new File(filePath), rootFolder);
                        newDependencies.add(newDependency);
                }
            }
            dependencies = newDependencies;
            fr.close();
        } catch (IOException e) {
            System.out.println("Got problems with fileReader");
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(TextFile other) {
        int result = findDependency(this, other);
        if (result == 0) { result = - findDependency(other, this); }
        return result;
    }

    public static int findDependency(TextFile of, TextFile from) {
        return findDependency(of, from, new Stack<>());
    }

    public static int findDependency(TextFile of, TextFile from, Stack<TextFile> stack) {

        if (stack.contains(of)) {
            stack.push(of);
            throw new RuntimeException("Cyclic dependency: " + stack);
        }

        stack.push(of);
        if (of.dependencies.isEmpty()) { of.find1LDependencies(); }
        int result = 0;

        if (of.dependencies.contains(from)) {
            result = 1;
        } else {
            for (TextFile tf: of.dependencies) {
                if (tf.equals(from)) {
                    return 1;
                } else {
                    result = findDependency(tf, from, stack) == 1 || result == 1 ? 1 : 0;
                }
            }
        }

        stack.pop();
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TextFile) {
            return this.file.getAbsolutePath().equals(
                    ((TextFile) object).file.getAbsolutePath()
            );
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.file.getName();
    }

}
