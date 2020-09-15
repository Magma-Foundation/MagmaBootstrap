package com.meti.type;

public class VoidType extends FormattedType {
    public static final Type VoidType = new VoidType();

    @Override
    protected String createFormat() {
        return "void %s";
    }
}
