package com.meti.feature.type.primitive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimitiveTypeTest {

    @Test
    void renderWithBefore(){
        assertEquals("int* main", PrimitiveType.I16.render("* main"));
    }

    @Test
    void render() {
        assertEquals("int main", PrimitiveType.I16.render("main"));
    }
}