package com.meti.stack;

import com.meti.render.Field;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ImmutableCallStack implements CallStack {
    private final Deque<Frame> frames;

    public ImmutableCallStack() {
        this(new LinkedList<>());
    }

    public ImmutableCallStack(Deque<Frame> frames) {
        this.frames = frames;
    }

    @Override
    public String toString() {
        return frames.stream()
                .map(Frame::toString)
                .collect(Collectors.joining(",", "[", "]"));
    }

    private Frame popLast(){
        if(!frames.isEmpty()) {
           return frames.pop();
        } else {
            return new ImmutableFrame();
        }
    }

    @Override
    public CallStack define(Field field) {
        Frame frame = popLast().define(field);
        frames.push(frame);
        return new ImmutableCallStack(frames);
    }

    @Override
    public CallStack enter() {
        frames.add(new ImmutableFrame());
        return new ImmutableCallStack(frames);
    }

    @Override
    public CallStack defineAll(List<Field> fields) {
        return fields.stream().reduce(this, CallStack::define, (oldStack, newStack) -> newStack);
    }

    @Override
    public boolean isDefined(String name){
        return frames.stream().anyMatch(frame -> frame.isDefined(name));
    }
}
