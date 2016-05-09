package io.sato.internal.service.boot.factory;

import io.sato.internal.command.Command;
import io.sato.internal.command.GenericCommand;

public class UnixCommandFactory extends BootCommandFactory {

    @Override
    public Command getRebootCommand() {
        return new GenericCommand("shutdown -r now");
    }

    @Override
    public Command getShutdownCommand() {
        return new GenericCommand("shutdown -h now");
    }

}
