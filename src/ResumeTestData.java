import ru.mechaneg.basejava.exception.ResumeSectionNotExistException;
import ru.mechaneg.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResumeTestData {
    public static void main(String... args) {

        Resume res = new Resume("Grigoriy Kislin");

        // Resume initialization
        //
        res.setSection(Resume.SectionType.PERSONAL, new TextSection("creative guy with strong logic"));

        res.setSection(Resume.SectionType.OBJECTIVE, new TextSection("java coach"));

        res.setSection(Resume.SectionType.ACHIEVEMENT, new EnumeratedTextSection(
                Arrays.asList("protocols realization", "java EE fault-tolerant framework")));

        res.setSection(Resume.SectionType.QUALIFICATIONS, new EnumeratedTextSection(
                Arrays.asList("java8, guava", "postgresql, redis", "git, svn")));

        res.setSection(Resume.SectionType.EXPERIENCE, new ChronologicalSection(
                        Arrays.asList(
                                new TimeIntervalRecord(
                                        "YOTA",
                                        "lead specialist. design and implementation of pay systems",
                                        LocalDate.of(2008, 06, 01),
                                        LocalDate.of(2010, 12, 01)
                                ),
                                new TimeIntervalRecord(
                                        "LUXOFT",
                                        "lead developer. Deutsche Bank CRM",
                                        LocalDate.of(2010, 12, 01),
                                        LocalDate.of(2012, 04, 01)
                                )
                        )
                )
        );

        res.setSection(Resume.SectionType.EDUCATION, new ChronologicalSection(
                        Arrays.asList(
                                new TimeIntervalRecord(
                                        "LUXOFT",
                                        "OOP and UML",
                                        LocalDate.of(2011, 03, 01),
                                        LocalDate.of(2011, 04, 01)
                                ),
                                new TimeIntervalRecord(
                                        "COURSERA",
                                        "functional programming in scala",
                                        LocalDate.of(2013, 03, 01),
                                        LocalDate.of(2013, 05, 01)
                                )
                        )
                )
        );

        {
            Map<ContactsSection.ContactType, String> contacts = new HashMap<>();
            contacts.put(ContactsSection.ContactType.TELEPHONE, "+7(921) 855-0482");
            contacts.put(ContactsSection.ContactType.SKYPE, "grigory.kislin");
            contacts.put(ContactsSection.ContactType.MAIL, "gkislin@yandex.ru");
            contacts.put(ContactsSection.ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
            contacts.put(ContactsSection.ContactType.GITHUB, "https://github.com/gkislin");
            contacts.put(ContactsSection.ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
            contacts.put(ContactsSection.ContactType.HOMEPAGE, "http://gkislin.ru");

            res.setSection(Resume.SectionType.CONTACTS, new ContactsSection(contacts));
        }

        // Output contacts of resume
        //
        System.out.println(Resume.SectionType.CONTACTS + " :\n");

        ContactsSection contacts = (ContactsSection)res.getSection(Resume.SectionType.CONTACTS);
        for (ContactsSection.ContactType type : ContactsSection.ContactType.values()) {
            System.out.println(type + " : " + contacts.getContact(type));
        }

        // Output experience of resume
        //
        System.out.println("\n" + Resume.SectionType.EXPERIENCE + " :\n");

        ChronologicalSection experience = (ChronologicalSection)res.getSection(Resume.SectionType.EXPERIENCE);
        for (TimeIntervalRecord experienceRow : experience.getContent()) {
            System.out.println(experienceRow);
        }

        // Inconsistent resume
        //
        Resume resInsonsitent = new Resume("Dmitry Shaposhnikov");
        try {
            resInsonsitent.getSection(Resume.SectionType.CONTACTS);
        } catch (ResumeSectionNotExistException ex) {
            System.out.println("\n" + Resume.SectionType.CONTACTS + " section doesn't exist");
        }
    }
}
