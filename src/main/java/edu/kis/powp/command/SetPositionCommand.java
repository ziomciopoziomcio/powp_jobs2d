package edu.kis.powp.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.Objects;

/**
 * Command that sets the driver's position without drawing.
 */
public class SetPositionCommand implements DriverCommand {

    private final int x;
    private final int y;
    private final Job2dDriver driver;

    public SetPositionCommand(int x, int y, Job2dDriver driver) {
        this.x = x;
        this.y = y;
        this.driver = Objects.requireNonNull(driver, "driver must not be null");
    }

    @Override
    public void execute() {
        driver.setPosition(x, y);
    }
}

