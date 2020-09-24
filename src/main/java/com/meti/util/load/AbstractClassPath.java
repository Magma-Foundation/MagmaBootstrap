package com.meti.util.load;

import com.meti.content.Content;
import com.meti.feature.render.Empty;
import com.meti.feature.extern.Include;
import com.meti.feature.render.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractClassPath implements ClassPath {
    private final List<String> nativeLoaded = new ArrayList<>();

    @Override
    public Optional<Node> loadNative(Content header) {
        String headerString = header.toString();
        if (nativeLoaded.contains(headerString)) {
            return Optional.of(new Empty());
        } else {
            nativeLoaded.add(headerString);
            return Optional.of(new Include(header));
        }
    }
}
