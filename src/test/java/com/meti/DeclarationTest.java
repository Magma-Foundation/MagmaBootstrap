package com.meti;

import org.junit.jupiter.api.Test;

public class DeclarationTest extends CompileTest{
    @Test
    void define(){
        assertCompile("int main(){int x=5;return 0;}", "def main() : I16 => {const x : I16 = 5;return 0;}");
    }

    @Test
    void defineInScope(){
        assertCompile("int main(){int value=420;return value;}", "def main() : I16 => {const value : I16 = 420; return value;}");
    }
}
