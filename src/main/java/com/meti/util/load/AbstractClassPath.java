package com.meti.util.load;

import com.meti.content.Content;
import com.meti.render.EmptyNode;
import com.meti.render.IncludeNode;
import com.meti.render.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractClassPath implements ClassPath {
    private final List<String> nativeLoaded = new ArrayList<>();

    @Override
    public Optional<Node> loadNative(Content header) {
        String headerString = header.toString();
        if (nativeLoaded.contains(headerString)) {
            return Optional.of(new EmptyNode());
        } else {
            nativeLoaded.add(headerString);
            return Optional.of(new IncludeNode(header));
        }
    }
}
