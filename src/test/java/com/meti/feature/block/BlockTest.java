package com.meti.feature.block;

import com.meti.feature.render.Node;
import com.meti.feature.type.primitive.Int_;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    @Test
    void render(){
        Node node = new Block(new Int_(10), new Int_(20));
        assertEquals("{1020}", node.render());
    }
}