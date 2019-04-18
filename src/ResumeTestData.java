import ru.mechaneg.basejava.exception.ResumeContactNotExistException;
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

        res.setSection(SectionType.EDUCATION, new ExperienceSection(
                        Arrays.asList(
                                new Experience(
                                        "LUXOFT",
                                        "https://www.luxoft.com/",
                                        null,
                                        "OOP and UML",
                                        LocalDate.of(2011, 03, 01),
                                        LocalDate.of(2011, 04, 01)
                                ),
                                new Experience(
                                        "COURSERA",
                                        "https://www.coursera.org/",
                                        null,
                                        "functional programming in scala",
                                        LocalDate.of(2013, 03, 01),
                                        LocalDate.of(2013, 05, 01)
                                )
                        )
                )
        );

        res.setContact(ContactType.TELEPHONE, new TelephoneContact("+7(921) 855-0482"));
        res.setContact(ContactType.SKYPE, new VoipContact("grigory.kislin"));
        res.setContact(ContactType.MAIL, new MailContact("gkislin@yandex.ru"));
        res.setContact(ContactType.LINKEDIN, new SiteContact("https://www.linkedin.com/in/gkislin/"));
        res.setContact(ContactType.GITHUB, new SiteContact("https://github.com/gkislin"));
        res.setContact(ContactType.STACKOVERFLOW, new SiteContact("https://stackoverflow.com/users/548473/gkislin"));
        res.setContact(ContactType.HOMEPAGE, new SiteContact("http://gkislin.ru"));

        // Output contacts of resume
        //
        System.out.println("CONTACTS\n");

        for (ContactType type : ContactType.values()) {
            System.out.println(type + " : " + res.getContact(type));
        }

        // Output experience of resume
        //
        System.out.println("\n" + SectionType.EXPERIENCE + " :\n");

        ExperienceSection experience = (ExperienceSection)res.getSection(SectionType.EXPERIENCE);
        for (Experience experienceRow : experience.getExperiences()) {
            System.out.println(experienceRow);
        }

        // Output education of resume
        //
        System.out.println("\n" + SectionType.EDUCATION + " :\n");

        ExperienceSection education = (ExperienceSection)res.getSection(SectionType.EDUCATION);
        for (Experience experienceRow : education.getExperiences()) {
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

        try {
            resInsonsitent.getContact(ContactType.SKYPE);
        } catch (ResumeContactNotExistException ex) {
            System.out.println("\n" + ContactType.SKYPE + " contact doesn't exist");
        }
    }
}
