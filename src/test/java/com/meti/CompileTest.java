package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompileTest {
    public static final Compiler COMPILER = new Compiler();

    protected void assertCompileError(Class<? extends Throwable> clazz, String source) {
        assertThrows(clazz, () -> COMPILER.compile(source));
    }

    protected void assertCompile(String expectedTarget, String source) {
        String actualTarget = COMPILER.compile(source);
        assertEquals(expectedTarget, actualTarget);
    }
}
