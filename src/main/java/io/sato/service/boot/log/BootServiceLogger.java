package io.sato.service.boot.log;


import io.sato.logging.SatoLogger;

public class BootServiceLogger extends SatoLogger {

    public BootServiceLogger(Class<?> owner) {
	super(owner);
    }

    public void willHandleRebootEvent(String event) {
	log("RebootEvent: " + event);
    }

    public void willHandleHaltEvent(String event) {
	log("HaltEvent: " + event);
    }

    public void unknownOSCommandExecuted(String commandName) {
	error("Command '" + commandName + "' on unknown Operating System executed");
    }

}
