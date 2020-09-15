package com.meti.content;

import com.meti.util.Monad;

import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Content {
    boolean startsWith(String sequence);

    boolean endsWith(String sequence);

    int length();

    char apply(int index);

    Monad<String> value();

    Content sliceToEnd(int start);

    Content slice(int start, int end);

    OptionalInt index(String sequence);

    Stream<Content> split(Function<Content, Strategy> constructor);

    boolean isPresent();

    OptionalInt indexFrom(String sequence, int end);
}