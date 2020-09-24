package com.meti;

import org.junit.jupiter.api.Test;

public class InvocationTest extends CompileTest {
    @Test
    void invokeNative(){
        assertCompile("#include <stdio.h>\n" +
                "int main(){printf(\"%s\",\"Hello World!\");return 0;}", "import native stdio;native def printf(format : String, value : Any) : Void;def main() : I16 => {printf(\"%s\", \"Hello World!\");return 0;}");
    }

    @Test
    void procedure() {
        assertCompile("void procedure(){}int main(){procedure();return 0;}", "def procedure() : Void => {} def main() : I16 => {procedure();return 0;}");
    }

    @Test
    void supply() {
        assertCompile("int supply(){return 0;}int main(){return supply();}", "def supply() : I16 => {return 0;} def main() : I16 => {return supply();}");
    }

    @Test
    void consume() {
        assertCompile("void consume(int value){}int main(){consume(420);return 0;}", "def consume(value : I16) : Void => {} def main() : I16 => {consume(420);return 0;}");
    }

    @Test
    void apply() {
        assertCompile("int pass(int value){return value;}int main(){return pass(0);}", "def pass(value : I16) : I16 => {return value;} def main() : I16 => {return pass(0);}");
    }
}
