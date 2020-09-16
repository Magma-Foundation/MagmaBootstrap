package com.meti;

import com.meti.content.Content;
import com.meti.content.RootContent;
import com.meti.evaluate.tokenizer.MagmaNodeTokenizer;
import com.meti.process.MagmaProcessor;
import com.meti.process.Processor;
import com.meti.render.ContentNode;
import com.meti.render.Field;
import com.meti.render.Node;
import com.meti.resolve.MagmaTypeTokenizer;
import com.meti.type.Type;
import com.meti.type.TypeGroup;

import java.util.function.Function;
import java.util.function.Supplier;

public class Compiler {
    String compile(String content) {
        Node root = new ContentNode(new RootContent("{" + content + "}"));
        Node tree = tokenize(root);
        Processor processor = new MagmaProcessor();
        String output = processor.process(tree)
                .apply(Node::render)
                .orElseThrow();
        return output.substring(1, output.length() - 1);
    }

    private Field resolveField(Field field) {
        Type newType = field.applyToType(this::resolve);
        return field.copy(newType);
    }

    private Node tokenize(Node previous) {
        Node node = previous.applyToContent(this::parseContent).orElseThrow();
        Node.Prototype prototype = node.createPrototype();
        Node.Prototype withFields = node.streamFields()
                .map(this::resolveField)
                .reduce(prototype, Node.Prototype::withField, (previous1, next) -> next);
        Node.Prototype withChildren = node.streamChildren()
                .map(this::tokenize)
                .reduce(withFields, Node.Prototype::withChild, (previous1, next) -> next);
        return withChildren.build();
    }

    private Node parseContent(Content content) {
        return new MagmaNodeTokenizer(content)
                .evaluate()
                .orElseThrow(supplyInvalidParse(content));
    }

    private Supplier<IllegalArgumentException> supplyInvalidParse(Content content) {
        return () -> content.value().append("Cannot parse: %s")
                .swap().apply(String::format)
                .transform(IllegalArgumentException::new);
    }

    private Type resolve(Type previous) {
        Type parent;
        //TODO: simplify condition
        if (previous.applyToContent(Function.identity()).isPresent() && previous.group().test(TypeGroup.Content.matches())) {
            parent = new MagmaTypeTokenizer(previous).tokenize().orElseThrow(() -> invalidateType(previous));
        } else {
            parent = previous;
        }
        Type.Prototype prototype = parent.createPrototype();
        Type.Prototype withFields = parent.streamFields()
                .map(this::resolveField)
                .reduce(prototype, Type.Prototype::withField, (oldPrototype, newPrototype) -> newPrototype);
        Type.Prototype withChildren = parent.streamChildren()
                .map(this::resolve)
                .reduce(withFields, Type.Prototype::withChild, (oldPrototype, newPrototype) -> newPrototype);
        return withChildren.build();
    }

    private CompileException invalidateType(Type previous) {
        return previous.applyToContent(this::invalidateType).orElseThrow();
    }

    private CompileException invalidateType(Content content) {
        return content.value().apply(Compiler.this::invalidateType);
    }

    private CompileException invalidateType(String s) {
        String message = String.format("Unable to resolve type: %s", s);
        return new CompileException(message);
    }
}