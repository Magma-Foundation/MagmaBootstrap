package com.meti.evaluate;

import com.meti.content.StringContent;
import com.meti.feature.evaluate.Evaluator;
import com.meti.feature.render.Field;
import com.meti.feature.evaluate.FieldEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldNodeEvaluatorTest {

    @Test
    void testConst() {
        Evaluator<Field> evaluator = new FieldEvaluator(new StringContent("const x : Int"));
        Optional<Field> optional = evaluator.evaluate();
        assertTrue(optional.isPresent());
        Field field = optional.get();
        field.name()
                .append("x").swap()
                .accept(Assertions::assertEquals);
    }
}