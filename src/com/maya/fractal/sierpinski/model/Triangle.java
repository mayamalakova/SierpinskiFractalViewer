package com.maya.fractal.sierpinski.model;

import org.eclipse.swt.graphics.GC;

/**
 * A triangle
 */
public class Triangle {
    Edge top, left, right;
    double size;
    double minSize;
    Triangle topChild, leftChild, rightChild;

    public Triangle(double x1, double y1, double size, double minSize) {
        this(new Edge(x1, y1), size, minSize);
    }

    public Triangle(Edge left, double size, double minSize) {
        this.size = size;
        calculateEdges(left, size);
        this.minSize = minSize;

        if (minSize < size) {
            createChildren();
        }
    }

    /**
     * Zoom the triangle and its children
     * @param percent the zoom percent
     */
    public void zoom(int percent) {
        double quotient = (100.0 + percent)/100;
        size = size * quotient;
        right = left.toRight(size);
        top = left.toTop(size);
        if (leftChild != null) {
            createChildren();
        }
    }

    /**
     * Draw thetriangle and its children
     * @param graphics the graphics instance to use for drawing
     */
    public void draw(GC graphics) {
        drawBorder(graphics);
        if (leftChild == null) {
            fill(graphics);
        }

        if (leftChild != null) {
            leftChild.draw(graphics);
            rightChild.draw(graphics);
            topChild.draw(graphics);
        }
    }

    /**
     * Shift vertically by the given percentage
     * @param percent of size to shift
     */
    public void shiftY(int percent) {
        double quotient = percent / 100d;
        double shift = size * quotient;
        calculateEdges(left.shiftY(shift), size);
        if (leftChild != null) {
            createChildren();
        }
    }

    /**
     * Shift horizontally by the given percentage
     * @param percent of size to shift
     */
    public void shiftX(int percent) {
        double quotient = percent / 100d;
        double shift = size * quotient;
        calculateEdges(left.shiftX(shift), size);
        if (leftChild != null) {
            createChildren();
        }
    }

    private void calculateEdges(Edge left, double size) {
        this.left = left;
        this.right = left.toRight(size);
        this.top = left.toTop(size);
    }

    private void fill(GC graphics) {
        graphics.fillPolygon(new int[]{(int) left.x, (int) left.y, (int) top.x, (int) top.y, (int) right.x, (int) right.y});
    }

    private void createChildren() {
        leftChild = new Triangle(new Edge(left), size / 2, minSize - 1);
        rightChild = new Triangle(left.bottomMedian(size), size / 2, minSize - 1);
        topChild = new Triangle(new Edge(leftChild.top), size / 2, minSize - 1);
    }

    private void drawBorder(GC graphics) {
        graphics.drawPolygon(new int[]{(int) left.x, (int) left.y, (int) top.x, (int) top.y, (int) right.x, (int) right.y});
    }

}
