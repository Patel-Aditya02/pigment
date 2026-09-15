package io.github.PatelAditya02;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A reusable ANSI style definition (foreground, background, and text styles).
 * <p>
 * Unlike a one-shot formatted string, a {@code Paint} instance holds no text of
 * its own — it represents a *style* that can be applied to any number of strings,
 * so it can be defined once and reused, e.g.:
 * <pre>{@code
 *     Paint success = Paint.of(Ink.GREEN, Style.BOLD);
 *     success.println("Saved.");
 *     success.println("Done.");
 * }</pre>
 */
public class Paint implements Paintable{

    public static final String RESET_ALL = "\033[0m";

    private Ink fg;
    private Ink bg;
    private final Set<Style> styles = new HashSet<>();

    private Paint() {}

    // ---- Factories ---------------------------------------------------

    public static Paint of(){
        return new Paint();
    }

    public static Paint of(Ink fg) {
        return Paint.of().fg(fg);
    }

    public static Paint ofBg(Ink bg) {
        return Paint.of().bg(bg);
    }

    public static Paint ofBg(Ink bg, Style... styles){
        return Paint.of().bg(bg).style(styles);
    }

    public static Paint of(Style... styles) {
        return Paint.of().style(styles);
    }

    public static Paint of(Ink fg, Ink bg) {
        return Paint.of().fg(fg).bg(bg);
    }

    public static Paint of(Ink fg, Style... styles) {
        return Paint.of().fg(fg).style(styles);
    }

    public static Paint of(Ink fg, Ink bg, Style... styles) {
        return Paint.of().fg(fg).bg(bg).style(styles);
    }

    // ---- Convenience one-shot helpers ---------------------------------

    // Not added String str(String text, Ink fg) since str is not reusable
    // and if you want a fg painted String then you can call Ink.COLOR.paint("text");
    // But added str(Style... styles) because Style.STYLE.paint() will only paint one style

    public static String str(String text, Style... style){
        return Paint.of(style).paint(text);
    }

    public static String str(String text, Ink fg, Ink bg) {
        return Paint.of(fg, bg).paint(text);
    }

    public static String str(String text, Ink fg, Style... styles) {
        return Paint.of(fg, styles).paint(text);
    }

    public static String str(String text, Ink fg, Ink bg, Style... styles) {
        return Paint.of(fg, bg, styles).paint(text);
    }

    public static void print(String text, Style... styles){
        System.out.print(Paint.str(text, styles));
    }

    public static void print(String text, Ink fg, Ink bg){
        System.out.print(Paint.str(text, fg, bg));
    }

    public static void print(String text, Ink fg, Style... styles){
        System.out.print(Paint.str(text, fg, styles));
    }

    public static void print(String text, Ink fg, Ink bg, Style... styles){
        System.out.print(Paint.str(text, fg, bg, styles));
    }

    public static void println(String text, Style... styles){
        System.out.println(Paint.str(text, styles));
    }

    public static void println(String text, Ink fg, Ink bg){
        System.out.println(Paint.str(text, fg, bg));
    }

    public static void println(String text, Ink fg, Style... styles){
        System.out.println(Paint.str(text, fg, styles));
    }

    public static void println(String text, Ink fg, Ink bg, Style... styles){
        System.out.println(Paint.str(text, fg, bg, styles));
    }

    // ---- Builder methods (mutate and return this) ---------------------

    public Paint fg(Ink ink) {
        this.fg = ink;
        return this;
    }

    public Paint bg(Ink ink) {
        this.bg = ink;
        return this;
    }

    public Paint style(Style... styles) {
        Collections.addAll(this.styles, styles);
        return this;
    }

    // ---- Resets --------------------------------------------------------

    /** Clears the foreground color only. */
    public Paint resetFg() {
        this.fg = null;
        return this;
    }

    /** Clears the background color only. */
    public Paint resetBg() {
        this.bg = null;
        return this;
    }

    /** Clears both foreground and background colors. */
    public Paint resetColor() {
        return resetFg().resetBg();
    }

    /** Clears all applied text styles (bold, italic, etc.), leaving colors intact. */
    public Paint resetStyle() {
        this.styles.clear();
        return this;
    }

    /** Clears color and style state, returning this Paint to a blank slate. */
    public Paint resetAll() {
        return resetColor().resetStyle();
    }

    // ---- Application -----------------------------------------------------

    /** Applies this style to the given text, returning the formatted ANSI string. */
    @Override
    public String paint(String text) {
        StringBuilder str = new StringBuilder();
        if (fg != null){
            str.append(this.fg.fg);
        }
        if (bg != null){
            str.append(bg.bg);
        }
        for(Style style: styles) {
            str.append(style.ansi);
        }
        str.append(text);
        str.append(RESET_ALL);
        return str.toString();
    }
}