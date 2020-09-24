package com.meti.feature.block.invoke;

import com.meti.content.StringContent;
import com.meti.feature.block.function.Abstraction;
import com.meti.feature.block.function.FunctionType;
import com.meti.feature.evaluate.Evaluator;
import com.meti.feature.render.Field;
import com.meti.feature.render.InlineField;
import com.meti.feature.render.Node;
import com.meti.feature.scope.variable.Variable;
import com.meti.feature.type.primitive.PrimitiveType;
import com.meti.process.InlineState;
import com.meti.process.State;
import com.meti.stack.CallStack;
import com.meti.stack.ImmutableCallStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProcedureFixerTest {

    @Test
    void evaluate() {
        //native def testName() : Void;
        Node caller = new Variable(new StringContent("caller"));
        Node asMapping = new Mapping(caller);
        Node asProcedure = new Procedure(caller);

        CallStack stack = new ImmutableCallStack().define(new InlineField("caller", new FunctionType(PrimitiveType.VOID), Collections.emptyList()));
        State previous = new InlineState(asMapping, stack);
        Evaluator<State> fixer = new ProcedureFixer(previous);
        State actual = fixer.evaluate().orElseThrow();
        State expected = new InlineState(asProcedure, stack);
        assertEquals(expected, actual);
    }
}