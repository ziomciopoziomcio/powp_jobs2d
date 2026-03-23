package edu.kis.powp.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.Objects;

/**
 * A recording Job2dDriver that records calls into a ComplexCommand.
 * The recorded commands are created with the provided target driver as receiver,
 * so the resulting ComplexCommand can be executed later on that target.
 */
public class CommandRecordingDriver implements Job2dDriver {

    private final Job2dDriver targetDriver;
    private final ComplexCommand recorded;

    /**
     * Create a recording driver that will build commands targeting the given driver.
     *
     * @param targetDriver receiver for recorded commands (must not be null)
     */
    public CommandRecordingDriver(Job2dDriver targetDriver) {
        this.targetDriver = Objects.requireNonNull(targetDriver, "targetDriver must not be null");
        this.recorded = new ComplexCommand();
    }

    @Override
    public void setPosition(int x, int y) {
        recorded.addCommand(new SetPositionCommand(x, y, targetDriver));
    }

    @Override
    public void operateTo(int x, int y) {
        recorded.addCommand(new OperateToCommand(x, y, targetDriver));
    }

    /**
     * Returns the ComplexCommand containing all recorded commands in order.
     *
     * @return the recorded ComplexCommand
     */
    public ComplexCommand getRecordedCommand() {
        return recorded;
    }
}

