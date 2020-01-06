package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerialization {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, EventPeriod.class, Organization.class, OrganizationSection.class, TextListSection.class, TextFieldSection.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try(Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            xmlParser.marshall(r,w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader r =new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
