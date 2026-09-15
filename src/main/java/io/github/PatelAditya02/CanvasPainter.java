package io.github.PatelAditya02;

import java.io.PrintStream;

public record CanvasPainter(Paintable paintable, PrintStream canvas) implements Paintable {

    public static final CanvasPainter SUCCESS = CanvasPainter.of(Paint.SUCCESS);
    public static final CanvasPainter INFO = CanvasPainter.of(Paint.INFO);
    public static final CanvasPainter WARN = CanvasPainter.of(Paint.WARN);
    public static final CanvasPainter ERROR = CanvasPainter.of(Paint.ERROR, System.err);

    public CanvasPainter {
        if (paintable == null || canvas == null) {
            throw new IllegalArgumentException("Paintable and Canvas are required and cannot be null!");
        }
    }

    public static CanvasPainter of(Paintable paintable, PrintStream canvas) {
        return new CanvasPainter(paintable, canvas);
    }

    public static CanvasPainter of(Paintable paintable) {
        return CanvasPainter.of(paintable, System.out);
    }

    @Override
    public String paint(String text) {
        return paintable.paint(text);
    }

    @Override
    public void print(Object o) {
        this.print(o, this.canvas);
    }

    @Override
    public void println(Object o) {
        this.println(o, this.canvas);
    }
}
