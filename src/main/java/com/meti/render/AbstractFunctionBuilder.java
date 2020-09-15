package com.meti.render;

import com.meti.type.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbstractFunctionBuilder implements Node.Prototype {
    private final List<Field> fields;

    public AbstractFunctionBuilder() {
        this(Collections.emptyList());
    }

    public AbstractFunctionBuilder(List<Field> fields) {
        this.fields = new ArrayList<>(fields);
    }

    public AbstractFunctionBuilder withIdentity(Field identity) {
        if (fields.isEmpty()) fields.add(identity);
        else fields.set(0, identity);
        return this;
    }

    public AbstractFunctionBuilder withParameters(List<Field> parameters) {
        this.fields.addAll(parameters);
        return this;
    }

    @Override
    public Node.Prototype withField(Field field) {
        List<Field> newFields = new ArrayList<>(this.fields);
        newFields.add(field);
        return new AbstractFunctionBuilder(newFields);
    }

    @Override
    public Node.Prototype withChild(Node child) {
        return new AbstractFunctionBuilder(fields);
    }

    @Override
    public Node build() {
        if (fields.isEmpty()) {
            throw new IllegalStateException("No return type was provided.");
        } else {
            return fields.get(0).destroy().apply(this::assemble);
        }
    }

    private AbstractFunctionNode assemble(String name, Type returnType, List<FieldFlag> flags) {
        List<Field> parameters = fields.subList(1, fields.size());
        return new AbstractFunctionNode(name, parameters, returnType, flags);
    }
}
