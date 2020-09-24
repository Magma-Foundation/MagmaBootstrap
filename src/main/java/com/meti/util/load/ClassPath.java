package com.meti.util.load;

import com.meti.content.Content;
import com.meti.feature.render.Node;

import java.util.Optional;
import java.util.stream.Stream;

public interface ClassPath {
    Optional<Node> read(Stream<Content> packages);

    Optional<Node> loadNative(Content header);
}
