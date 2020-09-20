package com.meti;

import com.meti.util.load.ClassPath;
import com.meti.util.load.PathClassPath;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompileTest {
    public final Compiler COMPILER = new Compiler(createClassPath());

    protected ClassPath createClassPath() {
        return new PathClassPath(Paths.get(".", "test", "source"));
    }

    protected void assertCompileError(Class<? extends Throwable> clazz, String source) {
        assertThrows(clazz, () -> COMPILER.compile(source));
    }

    protected void assertCompile(String expectedTarget, String source) {
        String actualTarget = COMPILER.compile(source);
        assertEquals(expectedTarget, actualTarget);
    }
}
