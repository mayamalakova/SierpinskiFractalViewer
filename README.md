# Sierpinski Fractal Viewer

## Description
The Sierpinski Fractal Viewer displays a Sierpinski triangle in a dialog window
 and allows the user to explore it by using the controls on the toolbox and the mouse.
The following operations are supported:
    - zoom in and out by pressing the + and - buttons
    - move up, down, left, right by pressing the arrow buttons and dragging with the mouse
    - resetting the view state by pressing the 'Reset' button

## Limitations
- the coordinate system is mirrored by the horizontal axis
- zooming works relative to the 0,0 point
- zooming works only with the buttons
- zooming is not totally indefinite - it is limited by the memory of the machine and operations start getting slow
 after some amount of zooming

## Algorithm
The initial Sierpinski triangle is represented by a Triangle structure which has 3 Edge elements - left, right and top
and three child triangles - leftChild, rightChild and topChild which have side with half the length of the side
of their parent.
When drawing, the top triangle edges get calculated, then its child triangle edges and so on till the side
of the triangles get below some preset limit. The last level of triangles gets filled.
When zooming the edges of the top triangle get recalculated, then the child triangles till the preset side limit is
reached, then the canvas is redrawn.
When panning the top triangle edges get shifted, then all child triangles edges and the canvas ges redrawn.

## Possible improvements
- avoid storing out-of scope triangles when zooming and panning by "shrinking" the root triangle to the size that
fits the view.
- zoom based on a center point different than 0,0 - the user will have to click to set the zoom center and
the triangle edges will be moved based on their distance to the center.
- use the mouse when zooming.