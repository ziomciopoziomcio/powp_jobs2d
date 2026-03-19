package edu.kis.powp.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.Objects;

/**
 * Command that instructs the driver to draw to the given coordinates.
 * The receiver (Job2dDriver) is injected so the command is decoupled
 * from concrete driver implementations and can be reused with different drivers.
 */
public class OperateToCommand implements DriverCommand {

    private final int x;
    private final int y;

    private Job2dDriver driver;

    /**
     * Create a command with coordinates; driver must be injected before execute().
     */
    public OperateToCommand(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a command with coordinates and an initial driver.
     */
    public OperateToCommand(int x, int y, Job2dDriver driver) {
        this.x = x;
        this.y = y;
        this.driver = Objects.requireNonNull(driver, "driver must not be null");
    }

    /**
     * Inject or change the receiver (driver) for this command.
     */
    public void setDriver(Job2dDriver driver) {
        this.driver = Objects.requireNonNull(driver, "driver must not be null");
    }

    @Override
    public void execute() {
        if (driver == null) {
            throw new IllegalStateException("Job2dDriver has not been set for OperateToCommand");
        }
        driver.operateTo(x, y);
    }
}
