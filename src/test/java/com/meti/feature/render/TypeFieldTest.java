package com.meti.feature.render;

import com.meti.feature.type.primitive.PrimitiveType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeFieldTest {
    @Test
    void render(){
        Type type = PrimitiveType.VOID;
        Field field = new TypeField(type);
        assertEquals("void", field.render());
    }
}