package com.maya.fractal.sierpinski.model;

/**
 * A triangle edge
 */
public class Edge {
    private final double E = 0.001;
    private final double SIN_60 = Math.sqrt(3)/2;

    public double x, y;

    public Edge(double x1, double y1) {
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
    public Edge translate(double size) {
        return new Edge(x, size - y);
    }

    @Override
    public String toString() {
        return String.format(" (%f.3, %f.3) ", x, y);
    }

    public Edge toRight(double size) {
        return new Edge(x + size, y);
    }

    public Edge toTop(double size) {
        return new Edge(x + size / 2, y + size * SIN_60);
    }

    public Edge bottomMedian(Edge left, double size) {
        return new Edge(left.x + size/2, left.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge edge = (Edge) obj;
            return this.x - edge.x < E && this.x - edge.x > -E && this.y - edge.y < E && this.y - edge.y > -E;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return new Double(x).hashCode() + new Double(y).hashCode();
    }
}
