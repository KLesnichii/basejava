package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class TextListSection implements Section {
    private final List<String> textList;

    public TextListSection(List<String> textList) {
        this.textList = Objects.requireNonNull(textList, "textList must not be null");
        ;
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
