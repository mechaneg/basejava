import ru.mechaneg.basejava.exception.ResumeSectionNotExistException;
import ru.mechaneg.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;

public class ResumeTestData {
    public static void main(String... args) {

        Resume res = new Resume("Grigoriy Kislin");

        // Resume initialization
        //
        res.setSection(SectionType.PERSONAL, new TextSection("creative guy with strong logic"));

        res.setSection(SectionType.OBJECTIVE, new TextSection("java coach"));

        res.setSection(SectionType.ACHIEVEMENT, new MarkedTextSection(
                Arrays.asList("protocols realization", "java EE fault-tolerant framework")));

        res.setSection(SectionType.QUALIFICATIONS, new MarkedTextSection(
                Arrays.asList("java8, guava", "postgresql, redis", "git, svn")));

        res.setSection(SectionType.EXPERIENCE, new ExperienceSection(
                        Arrays.asList(
                                new Experience(
                                        "YOTA",
                                        "https://www.yota.ru/",
                                        "lead specialist",
                                        "design and implementation of pay systems",
                                        LocalDate.of(2008, 06, 01),
                                        LocalDate.of(2010, 12, 01)
                                ),
                                new Experience(
                                        "LUXOFT",
                                        "https://www.luxoft.com/",
                                        "lead developer",
                                        "Deutsche Bank CRM",
                                        LocalDate.of(2010, 12, 01),
                                        LocalDate.of(2012, 04, 01)
                                )
                        )
                )
        );

        res.setSection(SectionType.EDUCATION, new EducationSection(
                        Arrays.asList(
                                new Education(
                                        "LUXOFT",
                                        "https://www.luxoft.com/",
                                        "OOP and UML",
                                        LocalDate.of(2011, 03, 01),
                                        LocalDate.of(2011, 04, 01)
                                ),
                                new Education(
                                        "COURSERA",
                                        "https://www.coursera.org/",
                                        "functional programming in scala",
                                        LocalDate.of(2013, 03, 01),
                                        LocalDate.of(2013, 05, 01)
                                )
                        )
                )
        );

        {
            EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
            contacts.put(ContactType.TELEPHONE, "+7(921) 855-0482");
            contacts.put(ContactType.SKYPE, "grigory.kislin");
            contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
            contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
            contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
            contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
            contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru");

            res.setContacts(new ContactsSection(contacts));
        }

        // Output contacts of resume
        //
        System.out.println("CONTACTS\n");

        ContactsSection contacts = res.getContacts();
        for (ContactType type : ContactType.values()) {
            System.out.println(type + " : " + contacts.getContact(type));
        }

        // Output experience of resume
        //
        System.out.println("\n" + SectionType.EXPERIENCE + " :\n");

        ExperienceSection experience = (ExperienceSection)res.getSection(SectionType.EXPERIENCE);
        for (Experience experienceRow : experience.getExperiences()) {
            System.out.println(experienceRow);
        }

        // Inconsistent resume
        //
        Resume resInsonsitent = new Resume("Dmitry Shaposhnikov");
        try {
            resInsonsitent.getSection(SectionType.ACHIEVEMENT);
        } catch (ResumeSectionNotExistException ex) {
            System.out.println("\n" + SectionType.ACHIEVEMENT + " section doesn't exist");
        }
    }
}
