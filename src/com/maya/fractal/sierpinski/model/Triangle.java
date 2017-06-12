package com.maya.fractal.sierpinski.model;

import org.eclipse.swt.graphics.GC;

/**
 * A triangle
 */
public class Triangle {
    Edge top, left, right;
    private double size;
    private double minSize;
    private Triangle topChild, leftChild, rightChild;

    public Triangle(final double x1, final double y1, final double size, final double minSize) {
        this(new Edge(x1, y1), size, minSize);
    }

    public Triangle(final Edge left, final double size, final double minSize) {
        this.size = size;
        calculateEdges(left, size);
        this.minSize = minSize;
    }

    /**
     * Zoom the triangle and its children
     *
     * @param percent the zoom percent
     */
    public void zoom(final int percent) {
        final double quotient = (100.0 + percent) / 100;
        size = size * quotient;
        right = left.toRight(size);
        top = left.toTop(size);
    }

    /**
     * Draw the triangle and its children
     *
     * @param graphics the graphics instance to use for drawing
     */
    public void draw(final GC graphics) {
        if (minSize < size) {
            createChildren();
            leftChild.draw(graphics);
            rightChild.draw(graphics);
            topChild.draw(graphics);
        } else {
            fill(graphics);
        }
    }

    /**
     * Shift vertically by the given number of pixels
     *
     * @param by of size to shift
     */
    public void shiftY(final int by) {
        calculateEdges(left.shiftY(by), size);
    }

    /**
     * Shift horizontally by the given number of pixels
     *
     * @param by size to shift
     */
    public void shiftX(final int by) {
        calculateEdges(left.shiftX(by), size);
    }

    private void calculateEdges(final Edge left, final double size) {
        this.left = left;
        this.right = left.toRight(size);
        this.top = left.toTop(size);
    }

    private void fill(final GC graphics) {
        graphics.fillPolygon(new int[]{(int) left.x, (int) left.y, (int) top.x, (int) top.y, (int) right.x, (int) right.y});
    }

    private void createChildren() {
        leftChild = new Triangle(new Edge(left), size / 2, minSize - 1);
        rightChild = new Triangle(left.bottomMedian(size), size / 2, minSize - 1);
        topChild = new Triangle(new Edge(leftChild.top), size / 2, minSize - 1);
    }

    public Edge getLeft() {
        return left;
    }
}
