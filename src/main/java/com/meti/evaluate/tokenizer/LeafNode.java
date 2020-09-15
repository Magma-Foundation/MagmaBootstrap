package com.meti.evaluate.tokenizer;

import com.meti.render.Node;

public abstract class LeafNode implements Node {
    @Override
    public boolean isParent() {
        return false;
    }
}
