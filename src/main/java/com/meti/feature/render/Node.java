package com.meti.feature.render;

import com.meti.content.Content;
import com.meti.feature.block.invoke.Invocation;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Node extends Renderable {
    boolean matches(Type value, CallStack stack);

    Node transformFields(Function<Field, Field> mapping);

    Node transformChildren(Function<Node, Node> mapping);

    Prototype create(Field field);

    Prototype create(Node child);

    default CallStack define(CallStack stack) {
        throw new UnsupportedOperationException();
    }

    <R> Optional<R> applyToContent(Function<Content, R> function);

    boolean isParent();

    Monad<Group> group();

    Stream<Field> streamFields();

    Stream<Node> streamChildren();

    Prototype createPrototype();

    Prototype createWithChildren();

    default Prototype transformByIdentity(Function<Field, Field> function) {
        Field oldIdentity = streamFields().findFirst().orElseThrow();
        Field newIdentity = function.apply(oldIdentity);
        return create(newIdentity);
    }

    @Override
    default String render() {
        return renderOptionally().orElseThrow(() -> new UnrenderableException("Not renderable."));
    }

    boolean doesReturn(Type value, CallStack stack);

    enum Group {
        Implementation,
        Variable, Block, Return, Integer, Declare, Reference, Dereference, Abstraction, Mapping, Procedure, Structure, Construction, Field, Cast, Include, Empty, Import, SizeOf, String, Content;

        public Predicate<Group> matches() {
            return (Group group) -> group == this;
        }

        public boolean matches(Node node) {
            return node.group().test(matches());
        }
    }

    interface Prototype {
        Prototype withField(Field field);

        Prototype withChild(Node child);

        Node build();

        List<Node> listChildren();

        List<Field> listFields();

        Prototype merge(Prototype other);
    }
}
