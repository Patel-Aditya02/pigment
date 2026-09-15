package io.github.PatelAditya02;

import java.io.PrintStream;

public class CanvasPainter implements Paintable{

    private final PrintStream canvas;
    private final Paintable paintable;

    private CanvasPainter(Paintable paintable, PrintStream canvas){
        if(paintable == null || canvas == null) {
            throw new IllegalArgumentException("Paintable and Canvas are required and cannot be null!");
        }
        this.paintable = paintable;
        this.canvas = canvas;
    }

    public static CanvasPainter of(Paintable paintable, PrintStream canvas){
        return new CanvasPainter(paintable, canvas);
    }

    public PrintStream getCanvas(){
        return canvas;
    }

    public Paintable getPaintable(){
        return paintable;
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
