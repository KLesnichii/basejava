package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Objects;


public class TextFieldSection extends Section implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;

    public TextFieldSection() {
    }

    public TextFieldSection(String text) {
        this.text = Objects.requireNonNull(text, "text must not be null");
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextFieldSection that = (TextFieldSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
