package com.meti.content;

import com.meti.util.Monad;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Stream;

public class StringContent implements Content {
    private final String value;

    @Override
    public String toString() {
        return formattedValue();
    }

    public StringContent(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Content that = (Content) o;
        return that.value().apply(formattedValue()::equals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formattedValue());
    }

    @Override
    public Content slice(int start, int end) {
        String child = formattedValue().substring(start, end);
        String formatted = child.trim();
        return new ChildContent(this, formatted, start, end);
    }

    @Override
    public OptionalInt index(String sequence) {
        int index = formattedValue().indexOf(sequence);
        if(index == -1) return OptionalInt.empty();
        else return OptionalInt.of(index);
    }

    @Override
    public Content sliceToEnd(int start) {
        int length = formattedValue().length();
        return slice(start, length);
    }

    @Override
    public OptionalInt indexFrom(String sequence, int end){
        int index = formattedValue().indexOf(sequence, end);
        if(index == -1) return OptionalInt.empty();
        return OptionalInt.of(index);
    }

    @Override
    public boolean isPresent() {
        return !formattedValue().isBlank();
    }

    @Override
    public int length(){
        return formattedValue().length();
    }

    @Override
    public char apply(int index){
        return formattedValue().charAt(index);
    }

    @Override
    public Stream<Content> split(Function<Content, Strategy> constructor) {
        return constructor.apply(this).split();
    }

    @Override
    public Monad<String> value(){
        return new Monad<>(formattedValue());
    }

    private String formattedValue() {
        return value.trim();
    }

    @Override
    public boolean startsWith(String sequence) {
        return formattedValue().startsWith(sequence);
    }

    @Override
    public boolean endsWith(String sequence) {
        return formattedValue().endsWith(sequence);
    }

    @Override
    public OptionalInt lastIndex(String sequence){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }
}
