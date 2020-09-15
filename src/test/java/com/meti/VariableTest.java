package com.meti;

import org.junit.jupiter.api.Test;

public class VariableTest extends CompileTest {
    @Test
    void testUndefined(){
        assertCompileError(UndefinedException.class, "def test() : Int => {return value;}");
    }
}
