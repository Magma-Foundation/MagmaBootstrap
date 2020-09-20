package com.meti;

import org.junit.jupiter.api.Test;

public class NativeImportTest extends CompileTest {
    @Test
    void test(){
        assertCompile("#include <stdio.h>\n", "import native stdio");
    }

    @Test
    void collision(){
        assertCompile("#include <stdio.h>\n", "import native stdio;import native stdio;");
    }
}
