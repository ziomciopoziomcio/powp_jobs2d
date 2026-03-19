package edu.kis.powp.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Macro command (composite) that executes a sequence of DriverCommand instances in order.
 */
public class ComplexCommand implements DriverCommand {

    private final List<DriverCommand> commands;

    /**
     * Creates an empty ComplexCommand.
     */
    public ComplexCommand() {
        this.commands = new ArrayList<>();
    }

    /**
     * Creates a ComplexCommand from an existing list of commands.
     * A defensive copy of the provided list is made to avoid external modifications.
     *
     * @param commands the initial list of commands (must not be null)
     */
    public ComplexCommand(List<DriverCommand> commands) {
        Objects.requireNonNull(commands, "commands must not be null");
        this.commands = new ArrayList<>(commands);
    }

    /**
     * Adds a command to the end of the sequence.
     *
     * @param command the command to add (must not be null)
     */
    public void addCommand(DriverCommand command) {
        Objects.requireNonNull(command, "command must not be null");
        this.commands.add(command);
    }

    /**
     * Executes all commands in the order they were added.
     */
    @Override
    public void execute() {
        for (DriverCommand command : new ArrayList<>(commands)) {
            command.execute();
        }
    }
}

