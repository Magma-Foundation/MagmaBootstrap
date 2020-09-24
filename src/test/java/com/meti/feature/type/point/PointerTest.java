package com.meti.feature.type.point;

import com.meti.feature.render.Type;
import com.meti.feature.type.primitive.PrimitiveType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointerTest {

    @Test
    void render() {
        Type type = Pointer.to(PrimitiveType.I32);
        assertEquals("long* test", type.render("test"));
    }
}