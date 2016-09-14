package io.sato.internal.service.boot.factory;

import io.sato.internal.command.Command;
import io.sato.internal.service.boot.command.UnknownRebootCommand;
import io.sato.internal.service.boot.command.UnknownShutdownCommand;

public class UnknownBootCommandFactory extends BootCommandFactory {

    @Override
    public Command getRebootCommand() {
        return new UnknownRebootCommand();
    }

    @Override
    public Command getShutdownCommand() {
        return new UnknownShutdownCommand();
    }

}
