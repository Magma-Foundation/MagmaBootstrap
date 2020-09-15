package com.meti.evaluate;

import com.meti.content.RootContent;
import com.meti.render.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldEvaluatorTest {

    @Test
    void testConst() {
        Evaluator<Field> evaluator = new FieldEvaluator(new RootContent("const x : Int"));
        Optional<Field> optional = evaluator.evaluate();
        assertTrue(optional.isPresent());
        Field field = optional.get();
        field.name()
                .append("x").swap()
                .accept(Assertions::assertEquals);
    }
}