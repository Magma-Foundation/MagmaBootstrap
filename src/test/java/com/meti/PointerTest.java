package com.meti;

import org.junit.jupiter.api.Test;

public class PointerTest extends CompileTest {
    @Test
    void reference(){
        assertCompile("{int x=10;int* y=&x;}", "{const x : I16 = 10;const y : Pointer<I16> = &x;}");
    }

    @Test
    void dereference(){
        assertCompile("{int x=10;int* y=&x;int z=*y;}", "{const x : I16 = 10;const y : Pointer<I16> = &x;const z : I16 = *y;}");
    }
}
