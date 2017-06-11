package com.maya.fractal.sierpinski;

import com.maya.fractal.sierpinski.model.Triangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class SierpinskiViewerApplication {
    private final Shell shell;
    private Canvas canvas;

    private Triangle rootTriangle;
    private Label positionLabel;

    public SierpinskiViewerApplication(final Shell shell) {
        this.shell = shell;
        shell.setText("Sierpinski Fractal Viewer");
    }

    public static void main(final String[] args) {
		final Display display = new Display();
        final Shell shell = new Shell(display);

        final SierpinskiViewerApplication sierpinskiViewerApplication = new SierpinskiViewerApplication(shell);
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

    private void renderUi(final Shell shell) {
        final RowLayout rowLayout = new RowLayout();
        rowLayout.type = SWT.VERTICAL;
        rowLayout.wrap = true;

        shell.setLayout(rowLayout);

        drawButtons(shell);
		drawCanvas(shell);

		shell.setSize(800, 800);
	}

    private void drawButtons(final Composite composite) {
        final Composite toolbox = new Composite(composite, SWT.NONE);
        final RowLayout layout = new RowLayout();
        layout.marginHeight = 5;
        layout.spacing = 5;
        toolbox.setLayout(layout);

        addZoomInButton(toolbox);
        addZoomOutButton(toolbox);

        Label separator = new Label(toolbox, SWT.VERTICAL | SWT.SEPARATOR);
        separator.setLayoutData(new RowData(10, 30));

        addButtonUp(toolbox);
        addButtonDown(toolbox);
        addButtonLeft(toolbox);
        addButtonRight(toolbox);

        addPositionIndicator(toolbox);

        separator = new Label(toolbox, SWT.VERTICAL | SWT.SEPARATOR);
        separator.setLayoutData(new RowData(10, 30));

        addResetButton(toolbox);
    }


    private void drawCanvas(final Composite composite) {
        final Composite canvasContainer = new Composite(composite, SWT.NONE);
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
        rootTriangle = new Triangle(0, 0, 600, 20);
    }

    private void addZoomInButton(final Composite container) {
        final Button zoomIn = createButton(container, " + ");
        addButtonListener(zoomIn, e -> rootTriangle.zoom(20));
    }

    private void addZoomOutButton(final Composite container) {
        final Button zoomOut = createButton(container, " - ");
        addButtonListener(zoomOut, e -> rootTriangle.zoom(-20));
    }

    private void addButtonUp(final Composite container) {
        final Button up = createButton(container, "↑");
        addButtonListener(up, e -> {
            rootTriangle.shiftY(50);
            positionLabel.setText(rootTriangle.getLeft().toString());
        });
    }

    private void addButtonDown(final Composite container) {
        final Button down = createButton(container, "↓");
        addButtonListener(down, e -> {
            rootTriangle.shiftY(-50);
            positionLabel.setText(rootTriangle.getLeft().toString());
        });
    }

    private void addButtonLeft(final Composite container) {
        final Button left = createButton(container, "←");
        addButtonListener(left, e -> {
            rootTriangle.shiftX(50);
            positionLabel.setText(rootTriangle.getLeft().toString());
        });
    }

    private void addButtonRight(final Composite container) {
        final Button right = createButton(container, "→");
        addButtonListener(right, e -> {
            rootTriangle.shiftX(-50);
            positionLabel.setText(rootTriangle.getLeft().toString());
        });
    }

    private void addResetButton(final Composite container) {
        final Button reset = createButton(container, "Reset", 100);
        addButtonListener(reset, e -> {
            initRootTriangle();
            positionLabel.setText(rootTriangle.getLeft().toString());
        });
    }


    private void addPositionIndicator(final Composite toolbox) {
        positionLabel = new Label(toolbox, SWT.CENTER);
        positionLabel.setLayoutData(new RowData(150, 30));
        positionLabel.setText("(0.0, 0.0)");
    }

    private Button createButton(final Composite container, final String label) {
        return createButton(container, label, 30);
    }

    private Button createButton(final Composite container, final String label, final int width) {
        final Button button = new Button(container, SWT.PUSH);
        button.setText(label);
        button.setLayoutData(new RowData(width, 30));
        return button;
    }

    private void addButtonListener(final Button button, final ISelectionListener listener) {
        button.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                listener.onSelection(e);
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(final SelectionEvent e) {
                widgetSelected(e);
            }
        });
    }
}

@FunctionalInterface
interface ISelectionListener {
    void onSelection(SelectionEvent e);
}
