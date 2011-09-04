

package me.escapeNT.pail.Util;

/**
 * Class representing the location of a section of text in a String.
 * @author escapeNT
 */
public class TextLocation {

    private int start;
    private int end;

    public TextLocation(int start, int end) {
        this.start = start;
        this.end = end;
    }


    /**
     * Gets the start of the section.
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * Gets the end of the section.
     * @return the end
     */
    public int getEnd() {
        return end;
    }
}