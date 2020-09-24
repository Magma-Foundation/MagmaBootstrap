package com.meti.feature.render;

import com.meti.stack.CallStack;

public abstract class Parent implements Node {
    @Override
    public boolean isParent() {
        return true;
    }

    @Override
    public boolean doesReturn(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }
}
