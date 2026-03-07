package edu.kis.powp.jobs2d.drivers.adapter;

import java.util.function.Supplier;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DrawerFeature;

/**
 * Adapter that implements Job2dDriver by drawing lines using the Drawer library.
 * It uses a Supplier&lt;ILine&gt; to create the desired kind of line (basic, special, dashed, ...).
 */
public class LineDrawerAdapter implements Job2dDriver {

    private int startX = 0, startY = 0;
    private final DrawPanelController drawerController;
    private final Supplier<ILine> lineSupplier;

    /**
     * Default adapter using the basic line from LineFactory.
     */
    public LineDrawerAdapter() {
        this(LineFactory::getBasicLine);
    }

    /**
     * Create adapter with a custom line supplier (e.g. LineFactory::getSpecialLine).
     *
     * @param lineSupplier supplier producing new ILine instances for each drawn segment
     */
    public LineDrawerAdapter(Supplier<ILine> lineSupplier) {
        this.drawerController = DrawerFeature.getDrawerController();
        this.lineSupplier = lineSupplier;
    }

    @Override
    public void setPosition(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        ILine line = lineSupplier.get();
        line.setStartCoordinates(this.startX, this.startY);
        line.setEndCoordinates(x, y);

        drawerController.drawLine(line);
        this.startX = x;
        this.startY = y;
    }

    @Override
    public String toString() {
        return "LineDrawerAdapter";
    }
}
