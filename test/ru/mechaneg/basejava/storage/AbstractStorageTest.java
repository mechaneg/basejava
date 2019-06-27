package ru.mechaneg.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.*;
import ru.mechaneg.basejava.util.Config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final String STORAGE_DIR = Config.get().getStorageDir();

    private static final String UUID = "some uuid";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private final Resume resume = new Resume(UUID, "john");
    private final Resume resume1 = new Resume(UUID_1, "clint");
    private final Resume resume2 = new Resume(UUID_2, "briant");
    private final Resume resume3 = new Resume(UUID_3, "andrew");
    protected IStorage storage;

    {
        // Contacts initialization
        //
        resume1.setContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume1.setContact(ContactType.SKYPE, "grigory.kislin");
        resume1.setContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume1.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume1.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume1.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        resume1.setContact(ContactType.HOMEPAGE, "http://gkislin.ru");


        // Sections initialization
        //
        resume1.setSection(SectionType.PERSONAL, new TextSection("creative guy with strong logic"));

        resume1.setSection(SectionType.OBJECTIVE, new TextSection("java coach"));

        resume1.setSection(SectionType.ACHIEVEMENT, new MarkedTextSection(
                Arrays.asList("protocols realization", "java EE fault-tolerant framework")));

        resume1.setSection(SectionType.QUALIFICATIONS, new MarkedTextSection(
                Arrays.asList("java8, guava", "postgresql, redis", "git, svn")));

        resume1.setSection(SectionType.EXPERIENCE, new OrganizationSection(
                        Arrays.asList(
                                new Organization(
                                        "YOTA",
                                        "https://www.yota.ru/",
                                        Arrays.asList(
                                                new Position("lead specialist",
                                                        "design and implementation of pay systems",
                                                        LocalDate.of(2008, 06, 01),
                                                        LocalDate.of(2010, 12, 01)
                                                )
                                        )

                                ),
                                new Organization(
                                        "LUXOFT",
                                        "https://www.luxoft.com/",
                                        Arrays.asList(
                                                new Position(
                                                        "lead developer",
                                                        "Deutsche Bank CRM",
                                                        LocalDate.of(2010, 12, 01),
                                                        LocalDate.of(2012, 04, 01)
                                                )
                                        )
                                )
                        )
                )
        );

        resume1.setSection(SectionType.EDUCATION, new OrganizationSection(
                        Arrays.asList(
                                new Organization(
                                        "LUXOFT",
                                        "https://www.luxoft.com/",
                                        Arrays.asList(
                                                new Position(
                                                        null,
                                                        "OOP and UML",
                                                        LocalDate.of(2011, 03, 01),
                                                        LocalDate.of(2011, 04, 01)
                                                )
                                        )
                                ),
                                new Organization(
                                        "COURSERA",
                                        "https://www.coursera.org/",
                                        Arrays.asList(
                                                new Position(
                                                        null,
                                                        "functional programming in scala",
                                                        LocalDate.of(2013, 03, 01),
                                                        LocalDate.of(2013, 05, 01)
                                                )
                                        )
                                )
                        )
                )
        );
    }

    protected AbstractStorageTest(IStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        storage.save(resume);
        assertEquals(resume, storage.get(UUID));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(resume3, resume2, resume1);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume current = new Resume(UUID_1, "dimon");
        storage.update(current);

        assertEquals(current, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}