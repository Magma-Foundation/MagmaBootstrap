package com.meti;

import org.junit.jupiter.api.Test;

class FunctionTest extends CompileTest {
    @Test
    void returnParameter(){
        assertCompile("int pass(int value){return value;}", "def pass(value : Int) : Int => {return value;}");
    }

    @Test
    void singleParameter() {
        assertCompile("void pass(int value){}", "def pass(value : Int) : Void => {}");
    }

    @Test
    void multipleParameters() {
        assertCompile("int apply(int start,int end){}", "def apply(start : Int, end : Int) : Int => {}");
    }

    @Test
    void empty() {
        assertCompile("void test(){}", "def test() : Void => {}");
    }

    @Test
    void testMain() {
        assertCompile("int main(){return 0;}", "def main() : Int => {return 0;}");
    }

    @Test
    void value() {
        assertCompile("void main(){return 0;}", "def main() : Void => {return 0;}");
    }

    @Test
    void name() {
        assertCompile("int test(){return 0;}", "def test() : Int => {return 0;}");
    }
}