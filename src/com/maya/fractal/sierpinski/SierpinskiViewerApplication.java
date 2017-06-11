package com.maya.fractal.sierpinski;

import com.maya.fractal.sierpinski.model.Triangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
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
        RowLayout rowLayout = new RowLayout();
        rowLayout.type = SWT.VERTICAL;
        rowLayout.wrap = true;

        shell.setLayout(rowLayout);

        drawButtons(shell);
		drawCanvas(shell);

		shell.setSize(800, 800);
	}

    private void drawButtons(Composite composite) {
        Composite toolbox = new Composite(composite, SWT.NONE);
        RowLayout layout = new RowLayout();
        layout.marginHeight = 5;
        layout.spacing = 5;
        toolbox.setLayout(layout);

        addZoomInButton(toolbox);
        addZoomOutButton(toolbox);
        addZoomLevelIndicator(toolbox);

        Label separator = new Label(toolbox, SWT.VERTICAL | SWT.SEPARATOR);
        separator.setLayoutData(new RowData(10, 30));

        addButtonUp(toolbox);
        addButtonDown(toolbox);
        addButtonLeft(toolbox);
        addButtonRight(toolbox);

        separator = new Label(toolbox, SWT.VERTICAL | SWT.SEPARATOR);
        separator.setLayoutData(new RowData(10, 30));

        addResetButton(toolbox);
    }

    private void drawCanvas(Composite composite) {
        Composite canvasContainer = new Composite(composite, SWT.NONE);
        canvas = new Canvas(canvasContainer, SWT.NONE);
        canvas.setSize(700, 700);

        initRootTriangle();
        final PaintListener paintListener = event -> {
            event.gc.setBackground(event.display.getSystemColor(SWT.COLOR_RED));
            rootTriangle.draw(event.gc);
        };

		canvas.addPaintListener(paintListener);
	}

    private void initRootTriangle() {
        rootTriangle = new Triangle(0, 30, 600, 20);
    }

    private void addZoomInButton(Composite container) {
        Button zoomIn = createButton(container, " + ");
        addButtonListener(zoomIn, e -> {
            updateZoomLevel(20);
            rootTriangle.zoom(20);
        });
    }

    private void addZoomOutButton(Composite container) {
        Button zoomOut = createButton(container, " - ");
        addButtonListener(zoomOut, e -> {
            updateZoomLevel(-20);
            rootTriangle.zoom(-20);
        });
    }

    private void addZoomLevelIndicator(Composite container) {
        zoomLabel = new Label(container, SWT.CENTER);
        zoomLabel.setLayoutData(new RowData(150, 40));
        setZoomLevel(100);
    }

    private void addButtonUp(Composite container) {
        Button up = createButton(container, "↑");
        addButtonListener(up, e -> rootTriangle.shiftY(-20));
    }

    private void addButtonDown(Composite container) {
        Button down = createButton(container, "↓");
        addButtonListener(down, e -> rootTriangle.shiftY(20));
    }

    private void addButtonLeft(Composite container) {
        Button left = createButton(container, "←");
        addButtonListener(left, e -> rootTriangle.shiftX(-20));
    }

    private void addButtonRight(Composite container) {
        Button right = createButton(container, "→");
        addButtonListener(right, e -> rootTriangle.shiftX(20));
    }

    private void addResetButton(Composite container) {
        Button reset = createButton(container, "Reset", 100);
        addButtonListener(reset, e -> {
            initRootTriangle();
            setZoomLevel(100);
        });
    }

    private Button createButton(Composite container, String label) {
        Button button = createButton(container, label, 30);
        return button;
    }

    private Button createButton(Composite container, String label, int width) {
        Button button = new Button(container, SWT.PUSH);
        button.setText(label);
        button.setLayoutData(new RowData(width, 30));
        return button;
    }

    private void addButtonListener(Button button, ISelectionListener listener) {
        button.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                listener.onSelection(e);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
    }

    private void updateZoomLevel(int adjustment) {
        zoomLevel = zoomLevel + adjustment;
        zoomLabel.setText("Zoom level: " + zoomLevel + "%");
    }

    private void setZoomLevel(int level) {
        zoomLevel = level;
        zoomLabel.setText("Zoom level: " + zoomLevel + "%");
    }
}

@FunctionalInterface
interface ISelectionListener {
    void onSelection(SelectionEvent e);
}
