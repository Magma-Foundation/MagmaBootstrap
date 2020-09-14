package com.meti;

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
    public Content slice(int start, int end) {
        String child = value.substring(start, end);
        String formatted = child.trim();
        return new ChildContent(this, formatted, start, end);
    }

    @Override
    public OptionalInt index(String sequence){
        int index = value.indexOf(sequence);
        if(index == -1) return OptionalInt.empty();
        else return OptionalInt.of(index);
    }

    @Override
    public Content sliceToEnd(int start) {
        return slice(start, value.length());
    }

    @Override
    public OptionalInt indexFrom(String sequence, int end){
        int index = value.indexOf(sequence, end);
        if(index == -1) return OptionalInt.empty();
        else return OptionalInt.of(index);
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
    public Stream<Content> splitByStrategy(Function<Content, Strategy> constructor) {
        return constructor.apply(this).split();
    }

    @Override
    public Monad<String> value(){
        throw new UnsupportedOperationException();
    }
}
