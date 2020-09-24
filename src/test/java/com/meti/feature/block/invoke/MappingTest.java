package com.meti.feature.block.invoke;

import com.meti.content.StringContent;
import com.meti.feature.extern.SizeOf;
import com.meti.feature.render.Node;
import com.meti.feature.render.TypeField;
import com.meti.feature.scope.variable.Variable;
import com.meti.feature.type.primitive.PrimitiveType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MappingTest {
    @Test
    void render(){
        Node caller = new Variable(new StringContent("malloc"));
        Node argument = new SizeOf(new TypeField(PrimitiveType.I16));
        Node node = new Mapping(caller, argument);
        assertEquals("malloc(sizeof(int))", node.render());
    }
}