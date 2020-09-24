package com.meti.feature.render;

import java.util.Optional;

public interface Renderable {
    @Deprecated
    Optional<String> renderOptionally();

    String render();
}
