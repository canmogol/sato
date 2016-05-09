package io.sato.internal.service.boot.command;

import io.sato.internal.command.Command;
import io.sato.internal.service.boot.log.BootServiceLogger;

public class UnknownRebootCommand implements Command {

    private BootServiceLogger logger = new BootServiceLogger();

    @Override
    public void execute() {
        logger.unknownOSCommandExecuted(getClass().getSimpleName());
    }

}

