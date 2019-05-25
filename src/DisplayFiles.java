import java.io.File;
import java.util.Objects;

public class DisplayFiles {

    public static void main(String... args) {
        File projectRoot = new File(".");
        displayFiles(projectRoot, "");
    }

    private static void displayFiles(File rootDir, String blankPrefix) {
        System.out.println(blankPrefix + rootDir.getName() + ":");

        for (File file : Objects.requireNonNull(rootDir.listFiles(file -> file.isFile() && !file.isHidden()))) {
            System.out.println(blankPrefix + " -" + file.getName());
        }
        for (File dir : Objects.requireNonNull(rootDir.listFiles(file -> file.isDirectory() && !file.isHidden()))) {
            displayFiles(dir, blankPrefix + "  ");
        }
    }
}
