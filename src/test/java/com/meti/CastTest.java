package com.meti;

import org.junit.jupiter.api.Test;

public class CastTest extends CompileTest{
    @Test
    void cast(){
        assertCompile("int main(){return (int)0;}", "def main() : I16 => {return <I16>(0);}");
    }
}
