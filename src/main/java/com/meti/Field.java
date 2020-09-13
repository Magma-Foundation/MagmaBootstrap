package com.meti;

public class Field implements Node {
    private final String name;
    private final Type type;

    public Field(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String render() {
        return type.render(name);
    }
}
