package com.maya.fractal.sierpinski;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;

public class SierpinskiViewerApplication {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		
		final PaintListener paintListener = new PaintListener() {
			@Override
			public void paintControl(PaintEvent event) {
				final Rectangle clientArea = shell.getClientArea();
				event.gc.drawLine(0, 0, clientArea.width, clientArea.height);
			}
		};
		
		shell.addPaintListener(paintListener);
		shell.setSize(300, 300);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		shell.dispose();
	}
}
