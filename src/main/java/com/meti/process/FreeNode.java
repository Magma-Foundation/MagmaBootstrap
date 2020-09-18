package com.meti.process;

import com.meti.content.Content;
import com.meti.render.ParentNode;

import java.util.Optional;
import java.util.function.Function;

public abstract class FreeNode extends ParentNode {
    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }
}
