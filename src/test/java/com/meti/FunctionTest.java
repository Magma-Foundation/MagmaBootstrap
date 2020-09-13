package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTest {
    @Test
    void singleParameter(){
        compile("def pass(value : Int) : Int => {return value;}", "int pass(int value){return value;}");
    }

    @Test
    void empty() {
        compile("def test() : Void => {}", "void test(){}");
    }

    @Test
    void testMain() {
        compile("def main() : Int => {return 0;}", "int main(){return 0;}");
    }

    private void compile(String source, String expectedTarget) {
        Compiler compiler = new Compiler();
        String actualTarget = compiler.compile(source);
        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    void value() {
        compile("def main() : Void => {return 0;}", "void main(){return 0;}");
    }

    @Test
    void name() {
        compile("def test() : Int => {return 0;}", "int test(){return 0;}");
    }
}