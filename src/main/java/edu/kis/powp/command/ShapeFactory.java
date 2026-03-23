package edu.kis.powp.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.Objects;

/**
 * Factory for creating shape commands (rectangles, triangles) as ComplexCommand instances.
 */
public final class ShapeFactory {

    private ShapeFactory() {
        // utility class - prevent instantiation
    }

    public static ComplexCommand createRectangle(Job2dDriver driver, int x, int y, int width, int height) {
        Objects.requireNonNull(driver, "driver must not be null");

        ComplexCommand complex = new ComplexCommand();

        complex.addCommand(new SetPositionCommand(x, y, driver));
        complex.addCommand(new OperateToCommand(x + width, y, driver));
        complex.addCommand(new OperateToCommand(x + width, y + height, driver));
        complex.addCommand(new OperateToCommand(x, y + height, driver));
        complex.addCommand(new OperateToCommand(x, y, driver));

        return complex;
    }

    public static ComplexCommand createTriangle(Job2dDriver driver,
                                                int x1, int y1,
                                                int x2, int y2,
                                                int x3, int y3) {
        Objects.requireNonNull(driver, "driver must not be null");

        ComplexCommand complex = new ComplexCommand();

        complex.addCommand(new SetPositionCommand(x1, y1, driver));
        complex.addCommand(new OperateToCommand(x2, y2, driver));
        complex.addCommand(new OperateToCommand(x3, y3, driver));
        complex.addCommand(new OperateToCommand(x1, y1, driver));

        return complex;
    }
}

