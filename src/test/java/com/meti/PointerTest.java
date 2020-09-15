package com.meti;

import org.junit.jupiter.api.Test;

public class PointerTest extends CompileTest {
    @Test
    void reference(){
        assertCompile("{int x=10;int* y=&x;}", "{const x : Int = 10;const y : Pointer<Int> = &x;}");
    }
}
