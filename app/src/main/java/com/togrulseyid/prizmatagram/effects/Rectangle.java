package com.togrulseyid.prizmatagram.effects;

/**
 * Created by toghrul on 03.08.2016.
 */
public class Rectangle {
    /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @serial
     * @see #setLocation(int, int)
     * @see #getLocation()
     * @since 1.0
     */
    public int x;

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @serial
     * @see #setLocation(int, int)
     * @see #getLocation()
     * @since 1.0
     */
    public int y;

    /**
     * The width of the <code>Rectangle</code>.
     *
     * @serial
     * @see #setSize(int, int)
     * @see #getSize()
     * @since 1.0
     */
    public int width;

    /**
     * The height of the <code>Rectangle</code>.
     *
     * @serial
     * @see #setSize(int, int)
     * @see #getSize()
     * @since 1.0
     */
    public int height;


    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner
     * is at (0,&nbsp;0) in the coordinate space, and whose width and
     * height are both zero.
     */
    public Rectangle() {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs a new <code>Rectangle</code>, initialized to match
     * the values of the specified <code>Rectangle</code>.
     * @param r  the <code>Rectangle</code> from which to copy initial values
     *           to a newly constructed <code>Rectangle</code>
     * @since 1.1
     */
    public Rectangle(Rectangle r) {
        this(r.x, r.y, r.width, r.height);
    }

    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner is
     * specified as
     * {@code (x,y)} and whose width and height
     * are specified by the arguments of the same name.
     * @param     x the specified X coordinate
     * @param     y the specified Y coordinate
     * @param     width    the width of the <code>Rectangle</code>
     * @param     height   the height of the <code>Rectangle</code>
     * @since 1.0
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner
     * is at (0,&nbsp;0) in the coordinate space, and whose width and
     * height are specified by the arguments of the same name.
     * @param width the width of the <code>Rectangle</code>
     * @param height the height of the <code>Rectangle</code>
     */
    public Rectangle(int width, int height) {
        this(0, 0, width, height);
    }

}
