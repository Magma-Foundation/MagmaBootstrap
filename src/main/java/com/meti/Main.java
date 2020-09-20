package com.meti;

import com.meti.util.load.PathClassPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getAnonymousLogger();
    public static final Path ROOT = Paths.get(".");

    public static void main(String[] args) {
        Path sourceDirectory = ROOT.resolve("source");
        if (!Files.exists(sourceDirectory)) {
            try {
                Files.createDirectories(sourceDirectory);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create source directories.", e);
            }
        }
        Path mainScript = sourceDirectory.resolve("Main.magma");
        if (!Files.exists(mainScript)) {
            try {
                Files.createFile(mainScript);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create main file.", e);
            }
        }
        try {
            String source = Files.readString(mainScript);
            Compiler compiler = new Compiler(new PathClassPath(Paths.get(".", "test", "source")));
            String target = compiler.compile(source);
            write(target);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read content from main file.", e);
        }
    }

    private static void write(String content) {
        Path out = ROOT.resolve("out");
        if (!Files.exists(out)) {
            try {
                Files.createDirectories(out);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create output directory.", e);
            }
        }
        try {
            Path target = out.resolve("target.c");
            Files.writeString(target, content);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to write output.", e);
        }
    }
}
