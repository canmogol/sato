package io.sato.internal.service.boot.log;

import com.fererlab.dispatch.log.BaseLogger;

public class BootServiceLogger extends BaseLogger {

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
