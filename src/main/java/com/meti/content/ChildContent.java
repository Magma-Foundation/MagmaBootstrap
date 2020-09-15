package com.meti.content;

import com.meti.util.Monad;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Stream;

public class ChildContent implements Content {
    private final Content parent;
    private final String value;
    private final int start;
    private final int end;

    public ChildContent(Content parent, String value, int from, int end) {
        this.parent = parent;
        this.value = value;
        this.start = from;
        this.end = end;
    }

    @Override
    public String toString() {
        return prepare();
    }

    @Override
    public Content slice(int start, int end) {
        String child = prepare().substring(start, end);
        String formatted = child.trim();
        return new ChildContent(this, formatted, start, end);
    }

    @Override
    public OptionalInt index(String sequence) {
        int index = prepare().indexOf(sequence);
        if (index == -1) return OptionalInt.empty();
        else return OptionalInt.of(index);
    }

    @Override
    public Content sliceToEnd(int start) {
        return slice(start, prepare().length());
    }

    @Override
    public OptionalInt indexFrom(String sequence, int end) {
        int index = prepare().indexOf(sequence, end);
        if (index == -1) return OptionalInt.empty();
        else return OptionalInt.of(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Content that = (Content) o;
        return that.value().apply(prepare()::equals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, prepare(), start, end);
    }

    @Override
    public boolean isPresent() {
        return !prepare().isBlank();
    }

    @Override
    public int length() {
        return prepare().length();
    }

    @Override
    public char apply(int index) {
        return prepare().charAt(index);
    }

    @Override
    public Stream<Content> split(Function<Content, Strategy> constructor) {
        return constructor.apply(this).split();
    }

    @Override
    public Monad<String> value() {
        return new Monad<>(prepare());
    }

    @Override
    public boolean startsWith(String sequence) {
        return prepare().startsWith(sequence);
    }

    @Override
    public boolean endsWith(String sequence) {
        return prepare().endsWith(sequence);
    }

    @Override
    public OptionalInt lastIndex(String sequence){
        int index = prepare().lastIndexOf(sequence);
        if(index == -1) return OptionalInt.empty();
        else return OptionalInt.of(index);
    }

    public String prepare() {
        return value.trim();
    }
}
