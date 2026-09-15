package io.github.PatelAditya02;

import java.awt.Color;
import java.util.Objects;

/**
 * A terminal color, exposed as a foreground and a matching background
 * ANSI code pair.
 * <p>
 * Predefined constants below cover the standard 16-color and one 256-color
 * (GRAY) palette entries. Custom colors — e.g. 256-color or truecolor RGB
 * codes not covered by the constants — can be created via
 * {@link #of(String, String)}.
 */
public final class Ink implements Paintable {

    public static final String RESET_FG = "\033[39m";
    public static final String RESET_BG = "\033[49m";
    public static final String RESET_INK = "\033[39;49m";

//    public static final Ink BLACK   = Ink.of("\033[30m", "\033[40m");
//    public static final Ink RED     = Ink.of("\033[31m", "\033[41m");
//    public static final Ink GREEN   = Ink.of("\033[32m", "\033[42m");
//    public static final Ink YELLOW  = Ink.of("\033[33m", "\033[43m");
//    public static final Ink BLUE    = Ink.of("\033[34m", "\033[44m");
//    public static final Ink MAGENTA = Ink.of("\033[35m", "\033[45m");
//    public static final Ink CYAN    = Ink.of("\033[36m", "\033[46m");
//    public static final Ink WHITE   = Ink.of("\033[37m", "\033[47m");
//    public static final Ink GREY    = Ink.ofColor256(245);

    public static final Ink BLACK = Ink.of(Color.BLACK);
    public static final Ink RED = Ink.of(Color.RED);
    public static final Ink GREEN = Ink.of(Color.GREEN);
    public static final Ink YELLOW = Ink.of(Color.YELLOW);
    public static final Ink BLUE = Ink.of(Color.BLUE);
    public static final Ink MAGENTA = Ink.of(Color.MAGENTA);
    public static final Ink CYAN = Ink.of(Color.CYAN);
    public static final Ink WHITE = Ink.of(Color.WHITE);
    public static final Ink LIGHT_GRAY = Ink.of(Color.LIGHT_GRAY);
    public static final Ink DARK_GRAY = Ink.of(Color.DARK_GRAY);
    public static final Ink ORANGE = Ink.of(Color.ORANGE);
    public static final Ink PINK = Ink.of(Color.PINK);

    // Lowercase aliases — same objects as above, not separate instances.
    // (Placed after the uppercase block: referencing a static field before
    // its own declaration point in the same class is illegal in Java, so
    // order here isn't just style, it's required.)
    public static final Ink black     = BLACK;
    public static final Ink red       = RED;
    public static final Ink green     = GREEN;
    public static final Ink yellow    = YELLOW;
    public static final Ink blue      = BLUE;
    public static final Ink magenta   = MAGENTA;
    public static final Ink cyan      = CYAN;
    public static final Ink white     = WHITE;
    public static final Ink lightGray = LIGHT_GRAY;
    public static final Ink darkGray  = DARK_GRAY;
    public static final Ink orange    = ORANGE;
    public static final Ink pink      = PINK;

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

    public static Ink of(Color color){
        return Ink.of(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Creates a 24-bit truecolor color from a packed hex value, e.g. 0x3498DB.
     * Equivalent to of(r, g, b) with the components extracted from the hex value.
     * @param hex packed RGB value, 0x000000-0xFFFFFF
     */
    public static Ink ofHex(int hex) {
        if (hex < 0x000000 || hex > 0xFFFFFF) {
            throw new IllegalArgumentException(
                    "hex must be 0x000000-0xFFFFFF, was 0x" + Integer.toHexString(hex));
        }
        int r = (hex >> 16) & 0xFF;
        int g = (hex >> 8) & 0xFF;
        int b = hex & 0xFF;
        return of(r, g, b);
    }

    /**
     * Creates a color from the 8-bit (256-color) ANSI palette.
     * @param code palette index, 0-255
     */
    public static Ink ofColor256(int code) {
        if (code < 0 || code > 255) {
            throw new IllegalArgumentException("256-color code must be 0-255, was " + code);
        }
        return Ink.of("\033[38;5;" + code + "m", "\033[48;5;" + code + "m");
    }

    /**
     * Creates a 24-bit truecolor color from RGB components. Support for
     * this varies by terminal — most modern terminal emulators handle it,
     * but not all.
     * @param r red, 0-255
     * @param g green, 0-255
     * @param b blue, 0-255
     */
    public static Ink of(int r, int g, int b) {
        requireByte(r, "r");
        requireByte(g, "g");
        requireByte(b, "b");
        return Ink.of(
                "\033[38;2;" + r + ";" + g + ";" + b + "m",
                "\033[48;2;" + r + ";" + g + ";" + b + "m"
        );
    }

    private static void requireByte(int value, String name) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException(name + " must be 0-255, was " + value);
        }
    }

    /** Paintable contract — foreground is Ink's canonical/default role. */
    @Override
    public String paint(String text) {
        return this.fg + text + RESET_FG;
    }

    /** Explicit background counterpart to paint(), since Ink can't offer
     *  an unambiguous default between foreground and background. */
    public String paintBg(String text) {
        return this.bg + text + RESET_BG;
    }

    public String paintAll(String text){
        return this.paintBg(this.paint(text));
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