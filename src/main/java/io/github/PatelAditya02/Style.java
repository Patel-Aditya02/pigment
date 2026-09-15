package io.github.PatelAditya02;

import java.util.Objects;

/**
 * A text style (bold, italic, underline, etc.) as an ANSI on/off code pair.
 * <p>
 * Predefined constants below cover the common SGR text styles. Custom
 * styles can be created via {@link #of(String, String)} for ANSI codes
 * not covered by the constants.
 */
public final class Style implements Paintable {

    public static final Style BOLD           = new Style("\033[1m", "\033[22m");
    public static final Style DIM             = new Style("\033[2m", "\033[22m");
    public static final Style ITALIC          = new Style("\033[3m", "\033[23m");
    public static final Style UNDERLINE       = new Style("\033[4m", "\033[24m");
    public static final Style BLINK_SLOW      = new Style("\033[5m", "\033[25m");
    public static final Style BLINK_FAST      = new Style("\033[6m", "\033[25m");
    public static final Style INVERT          = new Style("\033[7m", "\033[27m");
    public static final Style HIDDEN          = new Style("\033[8m", "\033[28m");
    public static final Style STRIKE_THROUGH  = new Style("\033[9m", "\033[29m");

    // Lowercase aliases — same objects as above, not separate instances.
    // (Placed after the uppercase block: referencing a static field before
    // its own declaration point in the same class is illegal in Java, so
    // order here isn't just style, it's required.)
    public static final Style bold          = BOLD;
    public static final Style dim           = DIM;
    public static final Style italic        = ITALIC;
    public static final Style underline     = UNDERLINE;
    public static final Style blinkSlow     = BLINK_SLOW;
    public static final Style blinkFast     = BLINK_FAST;
    public static final Style invert        = INVERT;
    public static final Style hidden        = HIDDEN;
    public static final Style strikeThrough = STRIKE_THROUGH;

    public final String ansi;
    public final String reset;

    private Style(String ansi, String reset) {
        this.ansi = ansi;
        this.reset = reset;
    }

    /** Creates a custom style from a raw ANSI on-code and its matching off-code. */
    public static Style of(String ansi, String reset) {
        return new Style(ansi, reset);
    }

    @Override
    public String paint(String str) {
        return this.ansi + str + this.reset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof Style other){
            return this.ansi.equals(other.ansi) && this.reset.equals(other.reset);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ansi, reset);
    }

    @Override
    public String toString() {
        return "Style <ansi: " + ansi.replace("\033", "\\033") +
                ", reset: " + reset.replace("\033", "\\033") + ">";
    }
}