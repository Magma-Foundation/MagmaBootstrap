package com.meti;

public abstract class FormattedType implements Type {
    @Override
    public String render(String name) {
        return String.format(createFormat(), name);
    }

    protected abstract String createFormat();
}
