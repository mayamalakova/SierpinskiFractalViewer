package com.maya.fractal.sierpinski.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EdgeTest {

    private Edge edge;

    @Before
    public void setUp() {
        edge = new Edge(0, 0);
    }

    @Test
    public void should_find_left_to_right() {
        Edge right = edge.toRight(100);
        Assert.assertEquals(new Edge(100, 0), right);
    }

}