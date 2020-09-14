package com.meti;

import org.junit.jupiter.api.Test;

class FunctionTest extends CompileTest {
    @Test
    void singleParameter() {
        compile("void pass(int value){}", "def pass(value : Int) : Void => {}");
    }

    @Test
    void multipleParameters() {
        compile("int apply(int start,int end){}", "def apply(start : Int, end : Int) : Int => {}");
    }

    @Test
    void empty() {
        compile("void test(){}", "def test() : Void => {}");
    }

    @Test
    void testMain() {
        compile("int main(){return 0;}", "def main() : Int => {return 0;}");
    }

    @Test
    void value() {
        compile("void main(){return 0;}", "def main() : Void => {return 0;}");
    }

    @Test
    void name() {
        compile("int test(){return 0;}", "def test() : Int => {return 0;}");
    }
}