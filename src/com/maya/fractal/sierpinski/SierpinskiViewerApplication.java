package com.maya.fractal.sierpinski;

import com.maya.fractal.sierpinski.model.Triangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class SierpinskiViewerApplication {
    private final Shell shell;
    private Canvas canvas;
    private boolean drag = false;
    private static int startX;
    private static int startY;
    private static int endX;
    private static int endY;
    private Triangle triangle;
    private int zoomLevel = 100;
    private Label zoomLabel;

    public SierpinskiViewerApplication(Shell shell) {
        this.shell = shell;
        shell.setText("Sierpinski Viewer");
    }

    public static void main(String[] args) {
		final Display display = new Display();
        final Shell shell = new Shell(display);

        SierpinskiViewerApplication sierpinskiViewerApplication = new SierpinskiViewerApplication(shell);
        sierpinskiViewerApplication.run();
	}

    private void run() {
        renderUi(shell);

        shell.open();

        while (!shell.isDisposed()) {
            if (!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
        shell.dispose();
    }

    private void renderUi(Shell shell) {
		shell.setLayout(new FillLayout());

		drawCanvas(shell);

		shell.setSize(500, 500);
	}

	private void drawCanvas(Shell shell) {
        canvas = new Canvas(shell, SWT.NONE);

        zoomLabel = new Label(canvas, SWT.CENTER);
        zoomLabel.setBounds(80, 5, 100, 30);
        zoomLabel.setText(zoomLevel + "%");

        Button zoomIn = new Button(canvas, SWT.PUSH);
        zoomIn.setBounds(0, 0, 30, 30);
        zoomIn.setText("+");
        zoomIn.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateZoomLevel(20);
                triangle.zoom(20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        Button zoomOut = new Button(canvas, SWT.PUSH);
        zoomOut.setBounds(40, 0, 30, 30);
        zoomOut.setText("-");
        zoomOut.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateZoomLevel(-20);
                triangle.zoom(-20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });


        triangle = new Triangle(0, 30, 400, 20);
        final PaintListener paintListener = event -> {
            event.gc.setBackground(event.display.getSystemColor(SWT.COLOR_RED));
            triangle.draw(event.gc);
        };

		canvas.addPaintListener(paintListener);
	}

    private void updateZoomLevel(int adjustment) {
        zoomLevel = zoomLevel + adjustment;
        zoomLabel.setText(zoomLevel + "%");
    }
}
