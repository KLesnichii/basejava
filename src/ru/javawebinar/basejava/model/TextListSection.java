package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class TextListSection implements Section {
    private final List<String> textList;

    public TextListSection(List<String> textList) {
        this.textList = textList;
    }

    public List<String> getTextList() {
        return textList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextListSection that = (TextListSection) o;

        return Objects.equals(textList, that.textList);
    }

    @Override
    public int hashCode() {
        return textList != null ? textList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return textList.toString();
    }
}
