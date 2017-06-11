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
        final Edge right = edge.toRight(100);
        Assert.assertEquals(new Edge(100, 0), right);
    }

    @Test
    public void should_find_left_to_top() throws Exception {
        final Edge top = edge.toTop(100);
        Assert.assertEquals(new Edge(50, 86.6025), top);
    }

    @Test
    public void should_find_bottom_median() throws Exception {
        final Edge middle = edge.bottomMedian(100);
        Assert.assertEquals(new Edge(50, 0), middle);
    }

    @Test
    public void should_shift_positive_Y() throws Exception {
        Edge shifted = this.edge.shiftY(20);
        Assert.assertEquals(new Edge(0, 20), shifted);
    }

    @Test
    public void should_shift_negative_Y() throws Exception {
        Edge shifted = this.edge.shiftY(-20);
        Assert.assertEquals(new Edge(0, -20), shifted);
    }

    @Test
    public void should_shift_negative_x() throws Exception {
        Edge shifted = this.edge.shiftX(-20);
        Assert.assertEquals(new Edge(-20, 0), shifted);
    }
}