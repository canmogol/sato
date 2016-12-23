package io.sato.service.boot.command;

import io.sato.command.Command;
import io.sato.service.boot.log.BootServiceLogger;

public class UnknownShutdownCommand implements Command {

    private BootServiceLogger logger = new BootServiceLogger(getClass());

    @Override
    public void execute() {
	logger.unknownOSCommandExecuted(getClass().getSimpleName());
    }
}
