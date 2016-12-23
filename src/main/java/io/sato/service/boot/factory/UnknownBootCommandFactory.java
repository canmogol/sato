package io.sato.service.boot.factory;

import io.sato.command.Command;
import io.sato.service.boot.command.UnknownRebootCommand;
import io.sato.service.boot.command.UnknownShutdownCommand;

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
