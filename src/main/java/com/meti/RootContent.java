package com.meti;

import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Stream;

public class RootContent implements Content {
    private final String value;

    public RootContent(String value) {
        this.value = value;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public char apply(int index){
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Content> splitByStrategy(Function<Content, Strategy> constructor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Monad<String> value(){
        return new Monad<>(value);
    }
}
