package com.meti.evaluate;

import com.meti.content.ChildContent;
import com.meti.content.Content;
import com.meti.content.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FlagStrategy implements Strategy {
    private final List<Content> values;
    private final Content content;
    private StringBuilder builder;
    private int start;
    private int end;

    public FlagStrategy(Content content) {
        this.content = content;
        values = new ArrayList<>();
        builder = new StringBuilder();
        start = 0;
        end = 0;
    }

    @Override
    public Stream<Content> split() {
        for (int i = 0; i < content.length(); i++) {
            char c = content.apply(i);
            if (c == ' ') {
                complete();
                builder = new StringBuilder();
                start = end;
            } else {
                builder.append(c);
                end++;
            }
        }
        complete();
        return values.stream();
    }

    private void complete() {
        values.add(new ChildContent(content, builder.toString(), start, end));
    }
}
