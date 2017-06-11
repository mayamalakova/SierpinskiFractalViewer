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

    private Triangle rootTriangle;
    private int zoomLevel = 100;
    private Label zoomLabel;

    public SierpinskiViewerApplication(Shell shell) {
        this.shell = shell;
        shell.setText("Sierpinski Fractal Viewer");
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

        addZoomInButton();
        addZoomOutButton();
        addZoomLevelIndicator();

        addButtonUp();
        addButtonDown();
        addButtonLeft();
        addButtonRight();

        rootTriangle = new Triangle(0, 30, 400, 20);
        final PaintListener paintListener = event -> {
            event.gc.setBackground(event.display.getSystemColor(SWT.COLOR_RED));
            rootTriangle.draw(event.gc);
        };

		canvas.addPaintListener(paintListener);
	}

    private void addZoomLevelIndicator() {
        zoomLabel = new Label(canvas, SWT.CENTER);
        zoomLabel.setBounds(80, 5, 120, 30);
        updateZoomLevel(0);
    }

    private void addZoomOutButton() {
        Button zoomOut = new Button(canvas, SWT.PUSH);
        zoomOut.setBounds(40, 0, 30, 30);
        zoomOut.setText("-");
        zoomOut.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateZoomLevel(-20);
                rootTriangle.zoom(-20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }

    private void addZoomInButton() {
        Button zoomIn = new Button(canvas, SWT.PUSH);
        zoomIn.setBounds(0, 0, 30, 30);
        zoomIn.setText("+");
        zoomIn.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateZoomLevel(20);
                rootTriangle.zoom(20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }

    private void updateZoomLevel(int adjustment) {
        zoomLevel = zoomLevel + adjustment;
        zoomLabel.setText("Zoom level: " + zoomLevel + "%");
    }

    private void addButtonUp() {
        Button up = new Button(canvas, SWT.PUSH);
        up.setBounds(200, 0, 30, 30);
        up.setText("↑");
        up.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                rootTriangle.shiftY(-20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }

    private void addButtonDown() {
        Button down = new Button(canvas, SWT.PUSH);
        down.setBounds(230, 0, 30, 30);
        down.setText("↓");
        down.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                rootTriangle.shiftY(20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

    }

    private void addButtonLeft() {
        Button left = new Button(canvas, SWT.PUSH);
        left.setBounds(260, 0, 30, 30);
        left.setText("←");
        left.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                rootTriangle.shiftX(-20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }

    private void addButtonRight() {
        Button right = new Button(canvas, SWT.PUSH);
        right.setBounds(290, 0, 30, 30);
        right.setText("→");
        right.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                rootTriangle.shiftX(20);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }
}
