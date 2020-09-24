package com.meti.feature.extern;

import com.meti.feature.render.Node;
import com.meti.feature.render.TypeField;
import com.meti.feature.type.primitive.PrimitiveType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeOfTest {
    @Test
    void render() {
        Node node = new SizeOf(new TypeField(PrimitiveType.I16));
        assertEquals("sizeof(int)", node.render());
    }
}