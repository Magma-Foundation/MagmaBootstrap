package com.meti.content;

import com.meti.util.Monad;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Stream;

public class RootContent implements Content {
    private final String value;

    public RootContent(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Content that = (Content) o;
        return that.value().apply(value::equals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Content slice(int start, int end) {
        String child = value.substring(start, end);
        String formatted = child.trim();
        return new ChildContent(this, formatted, start, end);
    }

    @Override
    public OptionalInt index(String sequence) {
        int index = value.indexOf(sequence);
        if(index == -1) return OptionalInt.empty();
        else return OptionalInt.of(index);
    }

    @Override
    public Content sliceToEnd(int start) {
        int length = value.length();
        return slice(start, length);
    }

    @Override
    public OptionalInt indexFrom(String sequence, int end){
        int index = value.indexOf(sequence, end);
        if(index == -1) return OptionalInt.empty();
        return OptionalInt.of(index);
    }

    @Override
    public boolean isPresent() {
        return !value.isBlank();
    }

    @Override
    public int length(){
        return value.length();
    }

    @Override
    public char apply(int index){
        return value.charAt(index);
    }

    @Override
    public Stream<Content> split(Function<Content, Strategy> constructor) {
        return constructor.apply(this).split();
    }

    @Override
    public Monad<String> value(){
        return new Monad<>(value);
    }

    @Override
    public boolean startsWith(String sequence) {
        return value.startsWith(sequence);
    }

    @Override
    public boolean endsWith(String sequence) {
        return value.endsWith(sequence);
    }

    @Override
    public OptionalInt lastIndex(String sequence){
        throw new UnsupportedOperationException();
    }
}
