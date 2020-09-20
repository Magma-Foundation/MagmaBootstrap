package com.meti.util.load;

import com.meti.CompileException;
import com.meti.content.Content;
import com.meti.content.RootContent;
import com.meti.render.ContentNode;
import com.meti.render.EmptyNode;
import com.meti.render.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PathClassPath extends AbstractClassPath {
    private final Path root;
    private final List<Path> loaded = new ArrayList<>();

    public PathClassPath(Path root) {
        this.root = root;
    }

    @Override
    public Optional<Node> read(Stream<Content> packages) {
        Path result = packages.reduce(root,
                (path, content) -> content.value().apply(path::resolve),
                (path, path2) -> path2);
        if (!Files.exists(result)) {
            throw new CompileException(result.toAbsolutePath() + " doesn't exist.");
        }
        try {
            return read(result);
        } catch (IOException e) {
            throw new CompileException("Cannot read content of: " + result.toAbsolutePath());
        }
    }

    Optional<Node> read(Path result) throws IOException {
        Node res = !loaded.contains(result) ?
                readPathToNode(result) :
                new EmptyNode();
        return Optional.of(res);
    }

    private Node readPathToNode(Path result) throws IOException {
        String importValue = Files.readString(result);
        RootContent content = new RootContent(importValue);
        loaded.add(result);
        return new ContentNode(content);
    }
}