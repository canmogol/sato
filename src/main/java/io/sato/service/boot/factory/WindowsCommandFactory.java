package io.sato.service.boot.factory;

import io.sato.command.Command;
import io.sato.command.GenericCommand;

public class WindowsCommandFactory extends BootCommandFactory {

    @Override
    public Command getRebootCommand() {
        return new GenericCommand("shutdown -r -f -t 0");
    }

    @Override
    public Command getShutdownCommand() {
        return new GenericCommand("shutdown -s -f -t 0");
    }

}
