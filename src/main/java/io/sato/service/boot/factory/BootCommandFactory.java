package io.sato.service.boot.factory;

import io.sato.command.Command;

public abstract class BootCommandFactory {

    public abstract Command getRebootCommand();

    public abstract Command getShutdownCommand();

    public static BootCommandFactory getBootCommandFactory() {

        String operatingSystem = System.getProperty("os.name");

        BootCommandFactory bootCommandFactory = new UnknownBootCommandFactory();

        if (operatingSystem != null) {
            operatingSystem = operatingSystem.toLowerCase();
            if (operatingSystem.contains("win")) {
                bootCommandFactory = new WindowsCommandFactory();

            } else if (operatingSystem.contains("linux")) {
                bootCommandFactory = new LinuxCommandFactory();

            } else if (operatingSystem.contains("nix")
                    || operatingSystem.contains("sunos")
                    || operatingSystem.contains("aix")) {
                bootCommandFactory = new UnixCommandFactory();

            } else if (operatingSystem.contains("mac")) {
                bootCommandFactory = new MacCommandFactory();

            }
        }

        return bootCommandFactory;
    }

}
