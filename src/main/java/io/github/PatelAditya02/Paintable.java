package io.github.PatelAditya02;

import java.io.PrintStream;

/**
 * Something that can render text as an ANSI-formatted string, and print
 * that result to stdout.
 * <p>
 * Implemented by {@link Ink}, {@link Style}, and {@link Paint} so all three
 * share one call surface — code that just wants "a thing I can paint text
 * with" can depend on {@code Paintable} instead of a concrete type.
 */
public interface Paintable {

    /** Applies this style/color to {@code text}, returning the ANSI-formatted result. */
    String paint(String text);


    default void print(Object o) {
        System.out.print(this.paint(o.toString()));
    }

    default void println(Object o) {
        System.out.println(this.paint(o.toString()));
    }

    default void println(Object o, PrintStream stream){
        stream.println(this.paint(o.toString()));
    }

    default void print(Object o, PrintStream stream){
        stream.print(this.paint(o.toString()));
    }
}