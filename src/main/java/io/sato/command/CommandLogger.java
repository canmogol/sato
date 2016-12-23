package io.sato.command;

import io.sato.logging.SatoLogger;

public class CommandLogger extends SatoLogger {

    public CommandLogger(Class<?> owner) {
	super(owner);
    }

    public void runtimeExecError(String commandName, String errorMessage) {
	error("got exception at " + commandName + " error: " + errorMessage);
    }

    public void willExecutingCommand(String command) {
	log("will execute command: " + command);
    }

    public void executedCommand(String command) {
	log("executed command: " + command);
    }
}
