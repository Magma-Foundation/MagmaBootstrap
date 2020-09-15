package com.meti;

import com.meti.content.Content;
import com.meti.content.RootContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class RootContentTest {

    @Test
    void applyToValue() {
        String expected = "test";
        Content content = new RootContent(expected);
        content.value().append(expected)
                .swap().accept(Assertions::assertSame);
    }

    @Test
    void slice() {
        Content content = new RootContent("test");
        Content child = content.slice(1, 2);
        child.value()
                .append("e")
                .swap()
                .accept(Assertions::assertEquals);
    }

    @Test
    void validateIndex() {
        Content content = new RootContent("something");
        OptionalInt optional = content.index("t");
        assertTrue(optional.isPresent());
    }

    @Test
    void index() {
        Content content = new RootContent("something");
        OptionalInt optional = content.index("t");
        assertEquals(4, optional.orElseThrow());
    }

    @Test
    void sliceToEnd() {
        Content content = new RootContent("test");
        Content child = content.sliceToEnd(1);
        child.value().append("est").swap()
                .accept(Assertions::assertEquals);
    }

    @Test
    void validateIndexFromEnd() {
        Content content = new RootContent("test");
        OptionalInt optional = content.indexFrom("t", 1);
        assertTrue(optional.isPresent());
    }

    @Test
    void indexFrom() {
        Content content = new RootContent("test");
        OptionalInt optionalInt = content.indexFrom("t", 1);
        assertEquals(3, optionalInt.orElseThrow());
    }

    @Test
    void isPresent() {
        Content content = new RootContent("test");
        assertTrue(content.isPresent());
    }
}