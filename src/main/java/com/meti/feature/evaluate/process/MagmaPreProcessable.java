package com.meti.feature.evaluate.process;

import com.meti.feature.scope.declare.DeclarationFixer;
import com.meti.feature.block.function.AbstractionLoader;
import com.meti.feature.block.function.ImplementationLoader;
import com.meti.feature.type.structure.StructureLoader;
import com.meti.process.State;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaPreProcessable extends CollectiveProcessable {
    public MagmaPreProcessable(State previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<State, Processable>> streamPreprocessors() {
        return Stream.of(
                StructureLoader::new,
                ImplementationLoader::new,
                AbstractionLoader::new,
                DeclarationFixer::new
        );
    }
}
