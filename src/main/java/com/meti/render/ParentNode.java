package com.meti.render;

public abstract class ParentNode implements Node {
    @Override
    public boolean isParent() {
        return true;
    }
}
