package com.meti.feature.render;

import com.meti.content.StringContent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTypeTest {

    @Test
    void render() {
        Type type = new ContentType(new StringContent("test"));
        assertThrows(UnrenderableException.class, type::render);
    }
}