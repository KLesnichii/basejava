package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.TextFieldSection;
import ru.javawebinar.basejava.util.JsonParser;

import static ru.javawebinar.basejava.model.ResumeTestData.resume1;

public class JsonParserTest {
    @Test
    public void testResume() {
        String json = JsonParser.write(resume1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(resume1, resume);
    }

    @Test
    public void write() {
        Section section1 = new TextFieldSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}
