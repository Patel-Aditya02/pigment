package io.github.PatelAditya02;

import java.util.Objects;

/**
 * A terminal color, exposed as a foreground and a matching background
 * ANSI code pair.
 * <p>
 * Predefined constants below cover the standard 16-color and one 256-color
 * (GREY) palette entries. Custom colors — e.g. 256-color or truecolor RGB
 * codes not covered by the constants — can be created via
 * {@link #of(String, String)}.
 */
public final class Ink implements Paintable {

    public static final String RESET_FG = "\033[39m";
    public static final String RESET_BG = "\033[49m";
    public static final String RESET_INK = "\033[39;49m";

    public static final Ink BLACK   = new Ink("\033[30m", "\033[40m");
    public static final Ink RED     = new Ink("\033[31m", "\033[41m");
    public static final Ink GREEN   = new Ink("\033[32m", "\033[42m");
    public static final Ink YELLOW  = new Ink("\033[33m", "\033[43m");
    public static final Ink BLUE    = new Ink("\033[34m", "\033[44m");
    public static final Ink MAGENTA = new Ink("\033[35m", "\033[45m");
    public static final Ink CYAN    = new Ink("\033[36m", "\033[46m");
    public static final Ink WHITE   = new Ink("\033[37m", "\033[47m");
    public static final Ink GREY    = new Ink("\033[38;5;245m", "\033[48;5;245m");

    // Lowercase aliases — same objects as above, not separate instances.
    // (Placed after the uppercase block: referencing a static field before
    // its own declaration point in the same class is illegal in Java, so
    // order here isn't just style, it's required.)
    public static final Ink black   = BLACK;
    public static final Ink red     = RED;
    public static final Ink green   = GREEN;
    public static final Ink yellow  = YELLOW;
    public static final Ink blue    = BLUE;
    public static final Ink magenta = MAGENTA;
    public static final Ink cyan    = CYAN;
    public static final Ink white   = WHITE;
    public static final Ink grey    = GREY;

    public final String fg;
    public final String bg;

    private Ink(String fg, String bg) {
        this.fg = fg;
        this.bg = bg;
    }

    /** Creates a custom color from raw foreground/background ANSI codes. */
    public static Ink of(String fg, String bg) {
        return new Ink(fg, bg);
    }

    public String fg(String str) {
        return this.fg + str + RESET_FG;
    }

    public String bg(String str) {
        return this.bg + str + RESET_BG;
    }

    /** Paintable contract — foreground is Ink's canonical/default role. */
    @Override
    public String paint(String text) {
        return this.fg(text);
    }

    /** Explicit background counterpart to paint(), since Ink can't offer
     *  an unambiguous default between foreground and background. */
    public String paintBg(String text) {
        return this.bg(text);
    }

    public String paintAll(String text){
        return this.bg(this.fg(text));
    }

    public void printBg(Object o) {
        System.out.print(this.paintBg(o.toString()));
    }

    public void printlnBg(Object o) {
        System.out.println(this.paintBg(o.toString()));
    }

    public void printlnAll(Object o){
        System.out.println(this.paintAll(o.toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Ink other) {
            return this.fg.equals(other.fg) && this.bg.equals(other.bg);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fg, bg);
    }

    @Override
    public String toString() {
        return "Ink <fg: " + fg.replace("\033", "\\033") +
                ", bg: " + bg.replace("\033", "\\033") + ">";
    }
}