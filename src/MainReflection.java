import ru.mechaneg.basejava.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String... args) {
        try {
            Class resumeClass = Resume.class;
            Method resumeToString = resumeClass.getMethod("toString");

            Resume resume = new Resume("123", "johny");
            String resumeString = (String) resumeToString.invoke(resume);

            System.out.println(resumeString);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
}
