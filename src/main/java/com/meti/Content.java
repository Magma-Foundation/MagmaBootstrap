package com.meti;

import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Content {
    int length();

    char apply(int index);

    //TODO: return monad
    <R> R applyToValue(Function<String, R> mapper);

    Content sliceToEnd(int start);

    Content slice(int start, int end);

    OptionalInt index(String sequence);

    Stream<Content> splitByStrategy(Function<Content, Strategy> constructor);

    boolean isPresent();

    OptionalInt indexFrom(String sequence, int end);
}