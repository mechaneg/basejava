import java.io.File;
import java.util.Objects;
import java.util.Stack;

public class DisplayFiles {

    public static void main(String... args) {
        File projectRoot = new File(".");

        Stack<File> directoryStack = new Stack<>();
        directoryStack.push(projectRoot);

        while(!directoryStack.empty()) {
            File currentDir = directoryStack.pop();

            File[] files = currentDir.listFiles(file -> file.isFile() && !file.isHidden());

            for (File file : Objects.requireNonNull(files)) {
                System.out.println(file.getName());
            }

            File[] dirs = currentDir.listFiles(file -> file.isDirectory() && !file.isHidden());

            for (File dir : Objects.requireNonNull(dirs)) {
                directoryStack.push(dir);
            }
        }
    }
}
