package com.maya.fractal.sierpinski.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by maja on 11/06/17.
 */
public class TriangleTest {

    private Triangle triangle;

    @Before
    public void setUp() {
        triangle = new Triangle(0, 0, 100, 20);
    }

    @Test
    public void should_find_edges() {
        Assert.assertEquals(new Edge(0, 0), triangle.left);
        Assert.assertEquals(new Edge(100, 0), triangle.right);
        Assert.assertEquals(new Edge(50, 86.6025), triangle.top);
    }

    @Test
    public void should_zoom_double() {
        triangle.zoom(100);
        Assert.assertEquals(new Edge(0, 0), triangle.left);
        Assert.assertEquals(new Edge(200, 0), triangle.right);
        Assert.assertEquals(new Edge(100, 173.205), triangle.top);
    }

    @Test
    public void should_zoom_50() {
        triangle.zoom(50);
        Assert.assertEquals(new Edge(0, 0), triangle.left);
        Assert.assertEquals(new Edge(150, 0), triangle.right);
        Assert.assertEquals(new Edge(75, 129.903), triangle.top);
    }

}