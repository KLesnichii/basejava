package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerialization {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            cycleWrite(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            cycleWrite(resume.getSections().entrySet(), dos, entry -> {
                SectionType key = entry.getKey();
                dos.writeUTF(key.name());
                Section section = entry.getValue();
                switch (key) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextFieldSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        cycleWrite(((TextListSection) section).getTextList(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        cycleWrite(((OrganizationSection) section).getOrganizationList(), dos, organization -> {
                            dos.writeUTF(organization.getHeader());
                            dos.writeUTF(organization.getLink());
                            cycleWrite(organization.getEventPeriodList(), dos, eventPeriod -> {
                                dos.writeUTF(eventPeriod.getTitle());
                                dos.writeUTF(eventPeriod.getStartDate().toString());
                                dos.writeUTF(eventPeriod.getEndDate().toString());
                                dos.writeUTF(eventPeriod.getText());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            cycleRead(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            cycleRead(dis, () -> {
                Section section;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        section = new TextFieldSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new TextListSection(cycleListRead(dis, dis::readUTF));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        section = new OrganizationSection(
                                cycleListRead(dis, () -> new Organization(dis.readUTF(), dis.readUTF(),
                                        cycleListRead(dis, () -> new EventPeriod(dis.readUTF(),
                                                LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF())))));
                        break;
                    default:
                        throw new IOException("IOException, section isn't found");
                }
                resume.addSection(sectionType, section);
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface CycleReader<T> {
        void read() throws IOException;
    }

    private <T> void cycleRead(DataInputStream dis, CycleReader<T> reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    @FunctionalInterface
    private interface CycleListReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> cycleListRead(DataInputStream dis, CycleListReader<T> reader) throws IOException {
        List<T> cycleList = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            cycleList.add(reader.read());
        }
        return cycleList;
    }

    @FunctionalInterface
    private interface CycleWriter<T> {
        void write(T t) throws IOException;
    }

    private <T> void cycleWrite(Collection<T> type, DataOutputStream dos, CycleWriter<T> writer) throws IOException {
        dos.writeInt(type.size());
        for (T t : type) {
            writer.write(t);
        }
    }
}