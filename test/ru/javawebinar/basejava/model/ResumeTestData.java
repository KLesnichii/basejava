package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ResumeTestData {
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String DUMMY = "dummy";
    public static final String NAME1 = "B";
    public static final String NAME2 = "A";
    public static final String NAME3 = "A";
    public static final String NAME4 = "C";
    public static final Resume resume1;
    public static final Resume resume2;
    public static final Resume resume3;
    public static final Resume resume4;

    static {
        resume1 = new Resume(UUID_1, NAME1);
        resume2 = new Resume(UUID_2, NAME2);
        resume3 = new Resume(UUID_3, NAME3);
        resume4 = new Resume(DUMMY, NAME4);

        addContacts(resume1, "+7(921) 111", "111@yandex.ru", "https://github.com/111", "111", "http://111.ru/", "https://stackoverflow.com/users/548473/111");
        addContacts(resume2, "+7(921) 222", "222@yandex.ru", "https://github.com/222", "222", "http://222.ru/", "https://stackoverflow.com/users/548473/222");
        addContacts(resume3, "+7(921) 333", "3331@yandex.ru", "https://github.com/333", "333", "http://333.ru/", "https://stackoverflow.com/users/548473/333");
//
//        addTextFieldSection(resume1, SectionType.OBJECTIVE, "resume1 objective");
//        addTextFieldSection(resume2, SectionType.OBJECTIVE, "resume2 objective");
//        addTextFieldSection(resume3, SectionType.OBJECTIVE, "resume3 objective");
//        addTextFieldSection(resume1, SectionType.PERSONAL, "resume1 personal");
//        addTextFieldSection(resume2, SectionType.PERSONAL, "resume2 personal");
//        addTextFieldSection(resume3, SectionType.PERSONAL, "resume3 personal");
//        addTextListSection(resume1, SectionType.ACHIEVEMENT, "resume1 achievement1", "resume1 achievement2");
//        addTextListSection(resume2, SectionType.ACHIEVEMENT, "resume2 achievement1", "resume2 achievement2", "resume2 achievement3");
//        addTextListSection(resume3, SectionType.ACHIEVEMENT, "resume3 achievement1", "resume3 achievement2", "resume3 achievement3", "resume3 achievement4");
//        addTextListSection(resume1, SectionType.QUALIFICATIONS, "resume1 qualification1", "resume1 qualification2");
//        addTextListSection(resume2, SectionType.QUALIFICATIONS, "resume2 qualification1", "resume2 qualification2", "resume2 qualification3");
//        addTextListSection(resume3, SectionType.QUALIFICATIONS, "resume3 qualification1", "resume3 qualification2", "resume3 qualification3", "resume3 qualification4");
//        addOrganizationSection(resume1, SectionType.EXPERIENCE, "resume1 header", "resume1 link",
//                new EventPeriod("resume1 title", DateUtil.of(2001, Month.JANUARY), LocalDate.now(), "resume1 experience"));
//        addOrganizationSection(resume2, SectionType.EXPERIENCE, "resume2 header", "resume2 link",
//                new EventPeriod("resume2 title", DateUtil.of(2002, Month.FEBRUARY), LocalDate.now(), "resume2 experience"));
//        addOrganizationSection(resume3, SectionType.EXPERIENCE, "resume3 header", "resume3 link",
//                new EventPeriod("resume3 title1", DateUtil.of(2003, Month.MARCH), LocalDate.now(), "resume3 experience1"),
//                new EventPeriod("resume3 title2", DateUtil.of(2003, Month.APRIL), LocalDate.now(), "resume3 experience2"));
//        addOrganizationSection(resume1, SectionType.EDUCATION, "resume1 header", "resume1 link",
//                new EventPeriod("resume1 title", DateUtil.of(2001, Month.JANUARY), LocalDate.now(), "resume1 education"));
//        addOrganizationSection(resume2, SectionType.EDUCATION, "resume2 header", "resume2 link",
//                new EventPeriod("resume2 title", DateUtil.of(2002, Month.FEBRUARY), LocalDate.now(), "resume2 education"));
//        addOrganizationSection(resume3, SectionType.EDUCATION, "resume3 header", "resume3 link",
//                new EventPeriod("resume3 title1", DateUtil.of(2003, Month.MARCH), LocalDate.now(), "resume3 education1"),
//                new EventPeriod("resume3 title2", DateUtil.of(2003, Month.APRIL), LocalDate.now(), "resume3 education2"));
    }

    public static void main(String[] args) {
        final String name = "Григорий Кислин";
        Resume grigoriyResume = new Resume(name);

        Map<ContactType, String> contacts = grigoriyResume.getContacts();
        contacts.put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");
        contacts.put(ContactType.OTHER_PAGE, "https://stackoverflow.com/users/548473/gkislin");

        Map<SectionType, Section> sections = grigoriyResume.getSections();
        sections.put(SectionType.OBJECTIVE, new TextFieldSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sections.put(SectionType.PERSONAL, new TextFieldSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        sections.put(SectionType.ACHIEVEMENT, new TextListSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), " +
                "Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, " +
                "Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        sections.put(SectionType.QUALIFICATIONS, new TextListSection(qualifications));

        List<Organization> experience = new ArrayList<>();
        List<EventPeriod> eventPeriods = new ArrayList<>();
        eventPeriods.add(new EventPeriod("Автор проекта.", DateUtil.of(2013, Month.OCTOBER), LocalDate.now(), "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experience.add(new Organization("Java Online Projects", "http://javaops.ru/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Старший разработчик (backend)", DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Проектирование и разработка онлайн платформы " +
                "управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experience.add(new Organization("Wrike", "https://www.wrike.com/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Java архитектор", DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.JANUARY), "Организация процесса разработки системы ERP " +
                "для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части" +
                " системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        experience.add(new Organization("RIT Center", null, new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Ведущий программист", DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL), "Участие в проекте Deutsche Bank CRM (WebLogic, " +
                "Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области" +
                " алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        experience.add(new Organization("Luxoft (Deutsche Bank)", "https://www.luxoft.com/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Ведущий специалист", DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER), "Дизайн и имплементация Java EE " +
                "фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. " +
                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        experience.add(new Organization("Yota", "https://www.yota.ru/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Разработчик ПО", DateUtil.of(2007, Month.MAY), DateUtil.of(2008, Month.JUNE), "Реализация клиентской (Eclipse RCP) и " +
                "серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)"));
        experience.add(new Organization("Enkata", "https://www.pega.com/products/pega-platform/robotic-automation", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Разработчик ПО", DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY), "Разработка информационной модели, " +
                "проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        experience.add(new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Инженер по аппаратному и программному тестированию", DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY), "Тестирование, " +
                "отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        experience.add(new Organization("Alcatel", "http://www.alcatel.ru/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        sections.put(SectionType.EXPERIENCE, new OrganizationSection(experience));

        List<Organization> education = new ArrayList<>();
        eventPeriods.add(new EventPeriod("\"Functional Programming Principles in Scala\" by Martin Odersky", DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), null));
        education.add(new Organization("Coursera", "https://www.coursera.org/learn/progfun1", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", DateUtil.of(2011, Month.MAY), DateUtil.of(2011, Month.APRIL), null));
        education.add(new Organization("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("3 месяца обучения мобильным IN сетям (Берлин)", DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL), null));
        education.add(new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("6 месяцев обучения цифровым телефонным сетям (Москва)", DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH), null));
        education.add(new Organization("Alcatel", "http://www.alcatel.ru/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Аспирантура (программист С, С++)", DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), null));
        eventPeriods.add(new EventPeriod("Инженер (программист Fortran, C)", DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), null));
        education.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/ru/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        eventPeriods.add(new EventPeriod("Закончил с отличием", DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE), null));
        education.add(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/", new ArrayList<>(eventPeriods)));
        eventPeriods.clear();
        sections.put(SectionType.EDUCATION, new OrganizationSection(education));

        resumePrint(grigoriyResume);
    }

    private static void resumePrint(Resume resume) {
        System.out.println("uuid: " + resume.getUuid());
        System.out.println("Имя Фамилия: " + resume.getFullName());
        System.out.println("Контакты:");
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType + ": " + resume.getContactByType(contactType));
        }
        System.out.println();
        for (SectionType sectionType : SectionType.values()) {
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    System.out.println(sectionType + ": " + resume.getSectionByType(sectionType));
                    System.out.println();
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    System.out.println(sectionType + ":");
                    for (String s : ((TextListSection) resume.getSectionByType(sectionType)).getTextList()) {
                        System.out.println(s);
                    }
                    System.out.println();
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    System.out.println(sectionType + ":");
                    for (Organization o : ((OrganizationSection) resume.getSectionByType(sectionType)).getOrganizationList()) {
                        System.out.println("header: " + o.getHeader());
                        System.out.println("link: " + o.getLink());
                        for (EventPeriod p : o.getEventPeriodList()) {
                            System.out.println(p.getTitle());
                            System.out.println(p.getStartDate());
                            System.out.println(p.getEndDate());
                            String text = p.getText();
                            if (text != null) System.out.println(text);
                            System.out.println();
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType);
            }
        }
    }

    private static void addContacts(Resume resume, String... contacts) {
        Map<ContactType, String> mapContact = resume.getContacts();
        mapContact.put(ContactType.PHONE_NUMBER, contacts[0]);
        mapContact.put(ContactType.EMAIL, contacts[1]);
        mapContact.put(ContactType.GITHUB, contacts[2]);
        mapContact.put(ContactType.SKYPE, contacts[3]);
        mapContact.put(ContactType.HOME_PAGE, contacts[4]);
        mapContact.put(ContactType.OTHER_PAGE, contacts[5]);
    }

    private static void addTextFieldSection(Resume resume, SectionType type, String text) {
        Map<SectionType, Section> sections = resume.getSections();
        sections.put(type, new TextFieldSection(text));
    }

    private static void addTextListSection(Resume resume, SectionType type, String... text) {
        Map<SectionType, Section> sections = resume.getSections();
        sections.put(type, new TextListSection(Arrays.asList(text)));
    }

    private static void addOrganizationSection(Resume resume, SectionType type, String header, String link, EventPeriod... eventPeriods) {
        Map<SectionType, Section> sections = resume.getSections();
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(header, link, Arrays.asList(eventPeriods)));
        sections.put(type, new OrganizationSection(organizationList));
    }
}