package com.meti.feature.type.structure;

import com.meti.content.StringContent;
import com.meti.feature.render.Type;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class StructureTypeTest {

    @Test
    void render() {
        Type type = new StructureType(new StringContent("Wrapper"), Collections.emptySet());
        assertEquals("struct Wrapper test", type.render("test"));
    }
}