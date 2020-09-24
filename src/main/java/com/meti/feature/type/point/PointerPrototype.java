package com.meti.feature.type.point;

import com.meti.feature.render.Field;
import com.meti.feature.render.Type;

class PointerPrototype implements Type.Prototype {
    private final Type child;

    public PointerPrototype() {
        this(null);
    }

    public PointerPrototype(Type child) {
        this.child = child;
    }

    @Override
    public Type.Prototype withChild(Type child) {
        return new PointerPrototype(child);
    }

    @Override
    public Type.Prototype withField(Field field) {
        return this;
    }

    @Override
    public Type build() {
        if (child == null) throw new IllegalStateException("Pointer child is null.");
        return Pointer.to(child);
    }
}
