package io.sato.service.boot.factory;

import io.sato.command.Command;
import io.sato.command.GenericCommand;

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
