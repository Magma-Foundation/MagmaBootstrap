package com.meti.feature.scope.declare;

import com.meti.feature.render.Field;
import com.meti.feature.render.InlineField;
import com.meti.feature.render.Node;
import com.meti.feature.type.primitive.Int_;
import com.meti.feature.type.primitive.PrimitiveType;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DeclarationTest {
    @Test
    void render(){
        Field identity = new InlineField("value", PrimitiveType.I16, Collections.emptyList());
        Node value = new Int_(10);
        Node node = new Declaration(identity, value);
        assertEquals("int value=10;", node.render());
    }
}