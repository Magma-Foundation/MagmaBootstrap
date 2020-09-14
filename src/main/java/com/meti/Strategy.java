package com.meti;

import java.util.stream.Stream;

public interface Strategy {
    Stream<Content> split();
}
