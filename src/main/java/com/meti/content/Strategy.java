package com.meti.content;

import com.meti.content.Content;

import java.util.stream.Stream;

public interface Strategy {
    Stream<Content> split();
}
