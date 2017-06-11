package com.maya.fractal.sierpinski.model;

import org.eclipse.swt.graphics.GC;

/**
 * Created by maja on 10/06/17.
 */
public class Triangle {
    private final double SIN_60 = Math.sqrt(3)/2;
    Edge top, left, right;
    int size;
    Triangle topChild, leftChild, rightChild;
    Triangle parent;
    int level;

    public Triangle(int x1, int y1, int length, int level) {
        int x2 = x1 + length;
        int y2 = (int) (y1 + length * SIN_60);
        this.size = length;
        this.left = new Edge(x1, y1);
        this.right = new Edge(x2, y1);
        this.top = new Edge(x1 + size/2, y2);
        this.level = level;

        if (level > 0) {
            createChildren();
        }
    }

    private void createChildren() {
        leftChild = new Triangle(new Edge(left), size / 2, level - 1);
        rightChild = new Triangle(left.bottomMedian(left, size), size / 2, level - 1);
        topChild = new Triangle(new Edge(leftChild.top), size / 2, level - 1);
    }

    public Triangle(Edge left, int size, int level) {
        this.size = size;
        this.left = left;
        this.right = left.leftToRight(left, size);
        this.top = left.leftToTop(left, size);
        this.level = level;

        if (level > 0) {
            createChildren();
        }
    }

    public void zoom(int percent) {
        float quotient = (100 + percent)/100;
        size = (int) (size * quotient);
        right = left.leftToRight(left, size);
        top = left.leftToTop(left, size);
        if (leftChild != null) {
            createChildren();
        }
    }

    private void shiftX(int by) {
        left.x = left.x + by;
        right.x = right.x + by;
        top.x = top.x + by;
    }

    public void draw(GC graphics) {
        graphics.drawPolygon(new int[]{left.x, left.y, top.x, top.y, right.x, right.y});
        if (level == 0) {
            graphics.fillPolygon(new int[]{left.x, left.y, top.x, top.y, right.x, right.y});
        }

        if (leftChild != null) {
            leftChild.draw(graphics);
            rightChild.draw(graphics);
            topChild.draw(graphics);
        }
    }

}
