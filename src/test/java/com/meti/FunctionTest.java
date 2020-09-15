package com.meti;

import org.junit.jupiter.api.Test;

class FunctionTest extends CompileTest {
    @Test
    void returnParameter(){
        assertCompile("int pass(int value){return value;}", "def pass(value : I16) : I16 => {return value;}");
    }

    @Test
    void singleParameter() {
        assertCompile("void pass(int value){}", "def pass(value : I16) : Void => {}");
    }

    @Test
    void multipleParameters() {
        assertCompile("int apply(int start,int end){}", "def apply(start : I16, end : I16) : I16 => {}");
    }

    @Test
    void empty() {
        assertCompile("void test(){}", "def test() : Void => {}");
    }

    @Test
    void testMain() {
        assertCompile("int main(){return 0;}", "def main() : I16 => {return 0;}");
    }

    @Test
    void value() {
        assertCompile("void main(){return 0;}", "def main() : Void => {return 0;}");
    }

    @Test
    void name() {
        assertCompile("int test(){return 0;}", "def test() : I16 => {return 0;}");
    }
}