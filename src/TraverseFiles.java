import java.io.File;

public class TraverseFiles {

    public static void main(String... args) {
        TraverseFiles traverser = new TraverseFiles();
        File projectRoot = new File(".");
        traverser.traverse(projectRoot);
    }

    public void traverse(File currentDir) {

        File[] files = currentDir.listFiles((file) -> {
            return file.isFile() && !file.isHidden();
        });

        for (File file : files) {
            System.out.println(file.getPath());
        }

        File[] dirs = currentDir.listFiles((file) -> {
            return file.isDirectory() && !file.isHidden();
        });

        for (File dir : dirs) {
            traverse(dir);
        }
    }
}
