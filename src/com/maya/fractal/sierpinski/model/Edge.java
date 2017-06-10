package com.maya.fractal.sierpinski.model;

/**
 * Created by maja on 10/06/17.
 */
public class Edge {
    public int x, y;

    public Edge(int x1, int y1) {
        x = x1;
        y = y1;
    }

    public Edge(Edge edge) {
        x = edge.x;
        y = edge.y;
    }

    /**
     * Translates coordinates from left, top 0, 0 to left, bottom 0,0
     * @param size
     * @return
     */
    public Edge translate(int size) {
        return new Edge(x, size - y);
    }

    @Override
    public String toString() {
        return String.format(" (%d, %d) ", x, y);
    }
}
