package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

public class InvocationTest extends CompileTest{
    @Test
    void procedure(){
        assertCompile("void procedure(){}int main(){procedure();return 0;}", "def procedure() : Void => {} def main() : I16 => {procedure();return 0;}");
    }

    @Test
    void supply(){
        assertCompile("int supply(){return 0;}int main(){return supply();}", "def supply() : I16 => {return 0;} def main() : I16 => {return supply();}");
    }

    @Test
    void consume(){
        assertCompile("void consume(int value){}int main(){consume(420);return 0;}", "def consume(value : I16) : Void => {} def main() : I16 => {consume(420);return 0;}");
    }

    @Test
    void apply(){
        assertCompile("int pass(int value){return value;}int main(){return pass(0);}", "def pass(value : I16) : I16 => {return value;} def main() : I16 => {return pass(0);}");
    }
}
