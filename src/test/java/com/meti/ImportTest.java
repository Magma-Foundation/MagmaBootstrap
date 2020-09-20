package com.meti;

import com.meti.util.load.ClassPath;
import com.meti.util.load.MappedClassPath;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ImportTest extends CompileTest {
    @Test
    void singleName() {
        assertCompile("int main(){return 0;}", "import Dummy;");
    }

    @Test
    void multipleNames(){
        assertCompile("void dummy(){}", "import dummy.Dummy2");
    }

    @Test
    void conflicts(){
        assertCompile("int main(){return 0;}", "import Dummy;import Dummy;");
    }

    @Override
    protected ClassPath createClassPath() {
        return new MappedClassPath(Map.of(
                "Dummy", "def main() : I16 => {return 0;}",
                "dummy.Dummy2", "def dummy() : Void => {}"));
    }

}
