package com.maya.fractal.sierpinski.model;

import org.eclipse.swt.graphics.GC;

/**
 * Created by maja on 10/06/17.
 */
public class Triangle {
    Edge top, left, riht;
    Triangle topChild, leftChild, rightChild;
    Triangle parent;
    int level;

    public Triangle(int x1, int y1, int length, int level) {
        int x2 = x1 + length;
        int y2 = y1 + length * 3/4;
        this.left = new Edge(x1, y1);
        this.riht = new Edge(x2, y1);
        this.top = new Edge(x1 + length/2, y2);
        System.out.format("Created %d", length);
        System.out.println("Edges " + this.left + this.riht + this.top);
        this.level = level;

        if (level > 0) {
            createChildren(length);
        }
    }

    private void createChildren(int length) {
        leftChild = new Triangle(new Edge(left), length / 2, level - 1);
        rightChild = new Triangle(new Edge(left.x + length/2, left.y), length / 2, level - 1);
        topChild = new Triangle(new Edge(leftChild.top), length / 2, level - 1);
    }

    public Triangle(Edge left, int length, int level) {
        this.left = left;
        this.riht = new Edge(left.x + length, left.y);
        this.top = new Edge(left.x + length / 2, left.y + length * 3/4);
        this.level = level;

        if (level > 0) {
            createChildren(length);
        }
    }

    public void zoomIn(int percent) {
        // recalculate edges - top = x, y*(100 + percent)/100; left = x*(100 - percent)/100, y; right = x*(100 + percent)/100, y
        // recalculate children
    }

    public void draw(GC graphics) {
        graphics.drawPolygon(new int[]{left.x, left.y, top.x, top.y, riht.x, riht.y});
        if (level == 0) {
            graphics.fillPolygon(new int[]{left.x, left.y, top.x, top.y, riht.x, riht.y});
        }

        if (leftChild != null) {
            leftChild.draw(graphics);
            rightChild.draw(graphics);
            topChild.draw(graphics);
        }
    }

}
