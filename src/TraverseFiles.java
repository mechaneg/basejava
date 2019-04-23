import java.io.File;

public class TraverseFiles {

    public static void main(String... args) {
        File projectRoot = new File(".");
        displayFiles(projectRoot);
    }

    private static void displayFiles(File currentDir) {
        if (!currentDir.isDirectory()) {
            throw new AssertionError("currentDir is not a directory");
        }

        File[] files = currentDir.listFiles(file -> file.isFile() && !file.isHidden());

        for (File file : files) {
            System.out.println(file.getName());
        }

        File[] dirs = currentDir.listFiles(file -> file.isDirectory() && !file.isHidden());

        for (File dir : dirs) {
            displayFiles(dir);
        }
    }
}
