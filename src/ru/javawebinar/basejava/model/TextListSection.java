package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class TextListSection extends Section implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> textList;

    public TextListSection() {
    }

    public TextListSection(List<String> textList) {
        this.textList = Objects.requireNonNull(textList, "textList must not be null");
    }

    public List<String> getTextList() {
        return textList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextListSection that = (TextListSection) o;

        return textList.equals(that.textList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(textList);
    }

    @Override
    public String toString() {
        return textList.toString();
    }
}
