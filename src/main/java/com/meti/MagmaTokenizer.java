package com.meti;

import com.meti.content.Content;
import com.meti.feature.CompileException;
import com.meti.feature.evaluate.tokenize.MagmaNodeTokenizer;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.feature.render.Type;
import com.meti.resolve.MagmaTypeTokenizer;
import com.meti.util.load.ClassPath;

import java.util.function.Supplier;

public class MagmaTokenizer implements Tokenizer {
    private final ClassPath classPath;

    public MagmaTokenizer(ClassPath classPath) {
        this.classPath = classPath;
    }

    @Override
    public Node tokenize(Node previous) {
        return previous.applyToContent(this::parseContent)
                .map(this::tokenizeParent)
                .orElse(previous);
    }

    private Node tokenizeParent(Node node) {
        return node.transformFields(this::tokenize)
                .transformChildren(this::tokenize);
    }

    private CompileException invalidateType(Type previous) {
        return previous.applyToContent(this::invalidateType).orElseThrow();
    }

    private CompileException invalidateType(Content content) {
        return content.value().apply(this::invalidateType);
    }

    private CompileException invalidateType(String s) {
        String message = String.format("Unable to matches type: %s", s);
        return new CompileException(message);
    }

    Type resolve(Type previous) {
        return tokenizeType(previous)
                .transformField(this::tokenize)
                .transformChildren(this::resolve);
    }

    private Field tokenize(Field field) {
        return field.transformByType(this::resolve);
    }

    private Type tokenizeType(Type previous) {
        return previous.is(Type.Group.Content) ?
                new MagmaTypeTokenizer(previous).tokenize().orElseThrow(() -> this.invalidateType(previous)) : previous;
    }

    private Node parseContent(Content content) {
        return new MagmaNodeTokenizer(content, classPath)
                .evaluate()
                .orElseThrow(supplyInvalidParse(content));
    }

    private Supplier<IllegalArgumentException> supplyInvalidParse(Content content) {
        return () -> content.value().append("Cannot parse: %s")
                .swap().apply(String::format)
                .transform(IllegalArgumentException::new);
    }
}