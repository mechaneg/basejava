import java.io.File;
import java.util.Objects;

public class DisplayFiles {

    public static void main(String... args) {
        File projectRoot = new File(".");
        displayFiles(projectRoot);
    }

    private static void displayFiles(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile() && !file.isHidden()) {
                System.out.println(file.getName());
            }
            if (file.isDirectory() && !file.isHidden()) {
                displayFiles(file);
            }
        }
    }
}
