package com.meti;

import java.util.function.Supplier;

public class Compiler {

    String compile(String content) {
        Node node = parseChild(new ContentNode(new RootContent(content)));
        return node.render().orElseThrow();
    }

    private Field resolveField(Field field) {
        Type newType = field.applyToType(this::resolve);
        return field.copy(newType);
    }

    private Node parseChild(Node previous) {
        Node node = previous.applyToContent(this::parseContent).orElseThrow();
        Node.Prototype prototype = node.createPrototype();
        Node.Prototype withFields = node.streamFields()
                .map(this::resolveField)
                .reduce(prototype, Node.Prototype::withField, (previous1, next) -> next);
        Node.Prototype withChildren = node.streamChildren()
                .map(this::parseChild)
                .reduce(withFields, Node.Prototype::withChild, (previous1, next) -> next);
        return withChildren.build();
    }

    private Node parseContent(Content content) {
        return new RootTokenizer(content)
                .evaluate()
                .orElseThrow(supplyInvalidParse(content));
    }

    private Supplier<IllegalArgumentException> supplyInvalidParse(Content content) {
        return () -> content.value().append("Cannot parse: %s")
                .swap().apply(String::format)
                .transform(IllegalArgumentException::new);
    }

    private Type resolve(Type previous) {
        Type type;
        if (previous.applyToContent(this::isInt).orElseThrow()) {
            type = IntType.IntType;
        } else {
            type = VoidType.VoidType;
        }
        return type;
    }

    private boolean isInt(Content content) {
        return content.value().apply("Int"::equals);
    }
}