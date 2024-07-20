package Task1;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class TextFile implements Comparable<TextFile> {

    private final File file;
    @Setter
    private ArrayList<TextFile> dependencies;

    public TextFile(File file) {
        this.file = file;
        this.dependencies = new ArrayList<>();
    }

    public void addDependency(TextFile f) {
        dependencies.add(f);
    }

    public void findDependencies(File rootFolder) throws IOException {

        ArrayList<TextFile> newDependencies = new ArrayList<>();
        FileReader fr = new FileReader(file);
        Scanner inp = new Scanner(fr);

        while (inp.hasNext()) {
            String text = inp.nextLine();
            Pattern pattern = Pattern.compile("required ‘([^’]*)’");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                String filePath = rootFolder.getAbsolutePath() + "\\" + matcher.group(1);
                TextFile newDependency = new TextFile(new File(filePath));
                newDependencies.add(newDependency);
            }
        }

        dependencies = newDependencies;
        fr.close();
    }

    @Override
    public int compareTo(TextFile otherFile) {

        boolean thisContainsOther = this.dependencies.contains(otherFile);
        boolean otherContainsThis = otherFile.dependencies.contains(this);
        if (thisContainsOther && otherContainsThis) {
            // Тут стоило бы ввести кастомный exception
            throw new RuntimeException("Cyclic dependency: " + this.file.getName() + " and " + otherFile.file.getName());
        } else if (thisContainsOther) {
            return 1;
        } else if (otherContainsThis) {
            return -1;
        } else {
            return 0;
        }
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
