package com.nullprogram.chess;

/**
 * Represents a position on a Chess board.
 */
public class Position implements Comparable<Position> {

    /**
     * The board coordinates of this position, starting at the white
     * queenside rook.
     */
    private int x, y;

    /**
     * Create a new position with given coordinates.
     *
     * @param posX x coordinate
     * @param posY y coordinate
     */
    public Position(final int posX, final int posY) {
        x = posX;
        y = posY;
    }

    /**
     * Create a new position from an existing position.
     *
     * @param pos    original position
     * @param deltax change in x
     * @param deltay change in y
     */
    public Position(final Position pos, final int deltax, final int deltay) {
        this(pos.x + deltax, pos.y + deltay);
    }

    /**
     * Get the x coordinate of the position.
     *
     * @return the x coordinate
     */
    public final int getX() {
        return x;
    }

    /**
     * Get the y coordinate of the position.
     *
     * @return the y coordinate
     */
    public final int getY() {
        return y;
    }

    /**
     * Convert the position to a string.
     *
     * @return string form of position
     */
    public final String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Check if Position objects are equal.
     *
     * @param pos position to be compared
     * @return    true if the positions are equal
     */
    public final boolean equals(final Position pos) {
        return (pos != null) && (pos.x == x) && (pos.y == y);
    }

    /**
     * Check if object is equal to this Position.
     *
     * @param o position to be compared
     * @return  true if the positions are equal
     */
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return equals((Position) o);
    }

    /**
     * Hash code of this object.
     *
     * @return hash code of this object.
     */
    public final int hashCode() {
        return ((new Integer(x)).hashCode() ^ (new Integer(y)).hashCode());
    }

    /**
     * Compare two Position objects.
     *
     * @param pos position to be compared
     * @return    positive if more, negative if less, zero if equal
     */
    public final int compareTo(final Position pos) {
        if (pos.y == y) {
            return x - pos.x;
        } else {
            return y - pos.y;
        }
    }
}
