package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerialization {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();
                if (SectionType.OBJECTIVE.equals(entry.getKey()) || SectionType.PERSONAL.equals(entry.getKey())) {
                    dos.writeUTF(((TextFieldSection) section).getText());
                }
                if (SectionType.ACHIEVEMENT.equals(entry.getKey()) || SectionType.QUALIFICATIONS.equals(entry.getKey())) {
                    List<String> textList = ((TextListSection) section).getTextList();
                    dos.writeInt(textList.size());
                    for (String text : textList) {
                        dos.writeUTF(text);
                    }
                }
                if (SectionType.EXPERIENCE.equals(entry.getKey()) || SectionType.EDUCATION.equals(entry.getKey())) {
                    List<Organization> organizationList = ((OrganizationSection) section).getOrganizationList();
                    dos.writeInt(organizationList.size());
                    for (Organization organization : organizationList) {
                        dos.writeUTF(organization.getHeader());
                        dos.writeUTF(organization.getLink());
                        List<EventPeriod> eventPeriodList = organization.getEventPeriodList();
                        dos.writeInt(eventPeriodList.size());
                        for (EventPeriod eventPeriod : eventPeriodList) {
                            dos.writeUTF(eventPeriod.getTitle());
                            dos.writeUTF(eventPeriod.getStartDate().toString());
                            dos.writeUTF(eventPeriod.getEndDate().toString());
                            dos.writeUTF(eventPeriod.getText());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContactType = dis.readInt();
            for (int i = 0; i < sizeContactType; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSectionType = dis.readInt();
            Section section = null;
            SectionType sectionType;
            for (int i = 0; i < sizeSectionType; i++) {
                sectionType = SectionType.valueOf(dis.readUTF());
                if (SectionType.OBJECTIVE.equals(sectionType) || SectionType.PERSONAL.equals(sectionType)) {
                    section = new TextFieldSection(dis.readUTF());
                }
                if (SectionType.ACHIEVEMENT.equals(sectionType) || SectionType.QUALIFICATIONS.equals(sectionType)) {
                    int sizeTextList = dis.readInt();
                    List<String> textList = new ArrayList<>();
                    for (int j = 0; j < sizeTextList; j++) {
                        textList.add(dis.readUTF());
                    }
                    section = new TextListSection(textList);
                }
                if (SectionType.EXPERIENCE.equals(sectionType) || SectionType.EDUCATION.equals(sectionType)) {
                    int sizeOrganizationList = dis.readInt();
                    List<Organization> organizationList = new ArrayList<>();
                    Organization organization = null;
                    for (int k = 0; k < sizeOrganizationList; k++) {
                        String header = dis.readUTF();
                        String list = dis.readUTF();
                        List<EventPeriod> eventPeriodList = new ArrayList<>();
                        int sizeEventPeriod = dis.readInt();
                        for (int l = 0; l < sizeEventPeriod; l++) {
                            eventPeriodList.add(new EventPeriod(dis.readUTF(), LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF()));
                        }
                        organization = new Organization(header, list, eventPeriodList);
                        organizationList.add(organization);
                    }
                    section = new OrganizationSection(organizationList);
                }
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }
}