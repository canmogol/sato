package io.sato.internal.command;

import java.io.IOException;

public class GenericCommand implements Command {

    private CommandLogger logger = new CommandLogger();
    private final String command;

    public GenericCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        try {
            Runtime.getRuntime().exec(this.command);
        } catch (IOException e) {
            logger.runtimeExecError(getClass().getSimpleName(), e.getMessage());
        }
    }
}
